package com.shinsunsu.anofspring.service;

import com.google.gson.Gson;
import com.shinsunsu.anofspring.domain.Article;
import com.shinsunsu.anofspring.dto.request.ArticleRequest;
import com.shinsunsu.anofspring.dto.response.ArticleResponse;
import com.shinsunsu.anofspring.exception.article.ArticleNotFoundException;
import com.shinsunsu.anofspring.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ArticleService {

    @Value("${keyword-key}")
    private String key;
    @Value("${client-ID}")
    private String clientID;
    @Value("${client-Secret}")
    private String clientSecret;


    private final ArticleRepository articleRepository;

    //키워드 찾기(3개)
    public List<String> searchKeywords(String args){

        List<String> tags = new ArrayList<>();
        // 언어 분석 기술(문어)
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
        String accessKey = key;   // 발급받은 API Key
        String analysisCode = "ner";        // 언어 분석 코드
        String text = args;           // 분석할 텍스트 데이터
        Gson gson = new Gson();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("analysis_code", analysisCode);
        argument.put("text", text);

        request.put("access_key", accessKey);
        request.put("argument", argument);

        URL url;
        Integer responseCode = null;
        String responBodyJson = null;
        Map<String, Object> responeBody = null;

        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            responBodyJson = sb.toString();

            // http 요청 오류 시 처리
            if ( responseCode != 200 ) {
                // 오류 내용 출력
                System.out.println("[error] " + responBodyJson);
            }

            responeBody = gson.fromJson(responBodyJson, Map.class);
            int result = ((Double) responeBody.get("result")).intValue();
            Map<String, Object> returnObject;
            List<Map> sentences;

            // 분석 요청 오류 시 처리
            if ( result != 0 ) {
                // 오류 내용 출력
                System.out.println("[error] " + responeBody.get("result"));
            }

            // 분석 결과 활용
            returnObject = (Map<String, Object>) responeBody.get("return_object");
            sentences = (List<Map>) returnObject.get("sentence");

            Map<String, ArticleKeywordService.Morpheme> morphemesMap = new HashMap<String, ArticleKeywordService.Morpheme>();
            Map<String, ArticleKeywordService.NameEntity> nameEntitiesMap = new HashMap<String, ArticleKeywordService.NameEntity>();
            List<ArticleKeywordService.Morpheme> morphemes = null;
            List<ArticleKeywordService.NameEntity> nameEntities = null;

            for( Map sentence : sentences ) {
                // 형태소 분석기 결과 수집 및 정렬
                List<Map<String, Object>> morphologicalAnalysisResult = (List<Map<String, Object>>) sentence.get("morp");
                for( Map<String, Object> morphemeInfo : morphologicalAnalysisResult ) {
                    String lemma = (String) morphemeInfo.get("lemma");
                    ArticleKeywordService.Morpheme morpheme = morphemesMap.get(lemma);
                    if ( morpheme == null ) {
                        morpheme = new ArticleKeywordService.Morpheme(lemma, (String) morphemeInfo.get("type"), 1);
                        morphemesMap.put(lemma, morpheme);
                    } else {
                        morpheme.count = morpheme.count + 1;
                    }
                }

                // 개체명 분석 결과 수집 및 정렬
                List<Map<String, Object>> nameEntityRecognitionResult = (List<Map<String, Object>>) sentence.get("NE");
                for( Map<String, Object> nameEntityInfo : nameEntityRecognitionResult ) {
                    String name = (String) nameEntityInfo.get("text");
                    ArticleKeywordService.NameEntity nameEntity = nameEntitiesMap.get(name);
                    if ( nameEntity == null ) {
                        nameEntity = new ArticleKeywordService.NameEntity(name, (String) nameEntityInfo.get("type"), 1);
                        nameEntitiesMap.put(name, nameEntity);
                    } else {
                        nameEntity.count = nameEntity.count + 1;
                    }
                }
            }

            if ( 0 < morphemesMap.size() ) {
                morphemes = new ArrayList<ArticleKeywordService.Morpheme>(morphemesMap.values());
                morphemes.sort( (morpheme1, morpheme2) -> {
                    return morpheme2.count - morpheme1.count;
                });
            }

            if ( 0 < nameEntitiesMap.size() ) {
                nameEntities = new ArrayList<ArticleKeywordService.NameEntity>(nameEntitiesMap.values());
                nameEntities.sort( (nameEntity1, nameEntity2) -> {
                    return nameEntity2.count - nameEntity1.count;
                });
            }

            BufferedReader reader2=new BufferedReader(
                    new FileReader("src/main/resources/stopwordlist.txt")
            );

            String stopwordstr;
            List<String> stopwordlist = new ArrayList<String>();
            while ((stopwordstr=reader2.readLine())!=null){
                String[] word=stopwordstr.split(" ");
                Collections.addAll(stopwordlist, word);
            }

            ArrayList<ArticleKeywordService.NameEntity> totallist=new ArrayList<ArticleKeywordService.NameEntity>();

            // 형태소들 중 명사들에 대해서 많이 노출된 순으로 출력 ( 최대 5개 )
            assert morphemes != null;
            morphemes
                    .stream()
                    .filter(morpheme -> {
                        return morpheme.type.equals("NNG") ||
                                morpheme.type.equals("NNP") ||
                                morpheme.type.equals("NNB");
                    })
                    .limit(10)
                    .forEach(morpheme -> {
                        totallist.add(new ArticleKeywordService.NameEntity(morpheme.text,morpheme.type,morpheme.count));
                    });
            // 인식된 개채명들 많이 노출된 순으로 출력 ( 최대 5개 )
            nameEntities
                    .stream()
                    .limit(10)
                    .forEach(nameEntity -> {
                        int dupli=0;
                        for (ArticleKeywordService.NameEntity entity : totallist) {
                            if (entity.text.equals(nameEntity.text)) {
                                dupli += 1;
                                entity.count += nameEntity.count;
                                break;
                            }
                        }
                        if (dupli==0){
                            totallist.add(new ArticleKeywordService.NameEntity(nameEntity.text,nameEntity.type,nameEntity.count));
                        }
                    });

            int totallistindex=0;

            while(totallistindex<totallist.size()){
                if(totallist.get(totallistindex).text.length() == 1){
                    totallist.remove(totallistindex);
                } else {
                    totallistindex++;
                }
            }

            int stopwordlistidx=-1;
            while(stopwordlistidx < (stopwordlist.size()-1)){
                stopwordlistidx++;      // -1 -> 0
                for(int i = 0; i < totallist.size(); i++){
                    if (totallist.get(i).text.equals(stopwordlist.get(stopwordlistidx))){
                        totallist.remove(i);
                        break;
                    }
                }
            }

            Collections.sort(totallist);
            Collections.reverse(totallist);

            for (int i = 0; i < 3; i++) {
                if (totallist.get(i).count >= 4) tags.add(totallist.get(i).text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tags;
    }

    //기사 요약
    public String getSummary(ArticleRequest articleRequest) {

        String responseBody = null;
        JSONObject jsonObjectAll = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        jsonObject1.put("title", articleRequest.getTitle());
        jsonObject1.put("content", articleRequest.getContent());
        jsonObjectAll.put("document", jsonObject1);

        jsonObject2.put("language","ko");
        jsonObject2.put("model","news");
        jsonObject2.put("tone","2");
        jsonObject2.put("summaryCount","3");
        jsonObjectAll.put("option", jsonObject2);

        try {
            String link = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";
            HttpURLConnection conn = null;
            URL url = new URL(link);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientID);

            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write(jsonObjectAll.toString());
            bw.flush();
            bw.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            responseBody = in.readLine();

            JSONParser jsonParser = new JSONParser();
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
            responseBody = (String) jsonObject.get("summary");

            int resposeCode = conn.getResponseCode();
            if(resposeCode == 400) {
                System.out.println("[error : 400] : 명령 실행 오류 ");
            } else if(resposeCode == 500) {
                System.out.println("[error : 500] : 서버 오류 ");
            } else {
                System.out.println(resposeCode + ": 응답코드");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    //기사 등록
    public boolean addArticle(Article article) {
        articleRepository.save(article);
        return true;
    }

    //기사 조회
    public ArticleResponse getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("존재하지 않는 기사입니다"));
        return  new ArticleResponse(article);
    }
}