package com.shinsunsu.anofspring.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ArticleService {

    static public class Morpheme {
        final String text;
        final String type;
        Integer count;
        public Morpheme (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }
    static public class NameEntity implements Comparable<NameEntity>{
        final String text;
        final String type;
        Integer count;
        public NameEntity (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }

        @Override
        public int compareTo(NameEntity o) {
            return this.count.compareTo(o.count);
        }
    }

    public void searchKeywords(String args) throws IOException{
        // 언어 분석 기술 문어/구어 중 한가지만 선택해 사용
        // 언어 분석 기술(문어)
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
        String accessKey = "key";   // 발급받은 API Key
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
                return ;
            }

            responeBody = gson.fromJson(responBodyJson, Map.class);
            int result = ((Double) responeBody.get("result")).intValue();
            Map<String, Object> returnObject;
            List<Map> sentences;

            // 분석 요청 오류 시 처리
            if ( result != 0 ) {

                // 오류 내용 출력
                System.out.println("[error] " + responeBody.get("result"));
                return ;
            }

            // 분석 결과 활용
            returnObject = (Map<String, Object>) responeBody.get("return_object");
            sentences = (List<Map>) returnObject.get("sentence");

            Map<String, Morpheme> morphemesMap = new HashMap<String, Morpheme>();
            Map<String, NameEntity> nameEntitiesMap = new HashMap<String, NameEntity>();
            List<Morpheme> morphemes = null;
            List<NameEntity> nameEntities = null;

            for( Map sentence : sentences ) {
                // 형태소 분석기 결과 수집 및 정렬
                List<Map<String, Object>> morphologicalAnalysisResult = (List<Map<String, Object>>) sentence.get("morp");
                for( Map<String, Object> morphemeInfo : morphologicalAnalysisResult ) {
                    String lemma = (String) morphemeInfo.get("lemma");
                    Morpheme morpheme = morphemesMap.get(lemma);
                    if ( morpheme == null ) {
                        morpheme = new Morpheme(lemma, (String) morphemeInfo.get("type"), 1);
                        morphemesMap.put(lemma, morpheme);
                    } else {
                        morpheme.count = morpheme.count + 1;
                    }
                }

                // 개체명 분석 결과 수집 및 정렬
                List<Map<String, Object>> nameEntityRecognitionResult = (List<Map<String, Object>>) sentence.get("NE");
                for( Map<String, Object> nameEntityInfo : nameEntityRecognitionResult ) {
                    String name = (String) nameEntityInfo.get("text");
                    NameEntity nameEntity = nameEntitiesMap.get(name);
                    if ( nameEntity == null ) {
                        nameEntity = new NameEntity(name, (String) nameEntityInfo.get("type"), 1);
                        nameEntitiesMap.put(name, nameEntity);
                    } else {
                        nameEntity.count = nameEntity.count + 1;
                    }
                }
            }

            if ( 0 < morphemesMap.size() ) {
                morphemes = new ArrayList<Morpheme>(morphemesMap.values());
                morphemes.sort( (morpheme1, morpheme2) -> {
                    return morpheme2.count - morpheme1.count;
                });
            }

            if ( 0 < nameEntitiesMap.size() ) {
                nameEntities = new ArrayList<NameEntity>(nameEntitiesMap.values());
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

            ArrayList<NameEntity> totallist=new ArrayList<NameEntity>();

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
                        totallist.add(new NameEntity(morpheme.text,morpheme.type,morpheme.count));
                        System.out.println("[명사] " + morpheme.text + " ("+morpheme.count+")" );
                    });
            // 인식된 개채명들 많이 노출된 순으로 출력 ( 최대 5개 )
            System.out.println("");
            nameEntities
                    .stream()
                    .limit(10)
                    .forEach(nameEntity -> {
                        int dupli=0;
                        for (NameEntity entity : totallist) {
                            if (entity.text.equals(nameEntity.text)) {
                                dupli += 1;
                                entity.count += nameEntity.count;
                                break;
                            }
                        }
                        if (dupli==0){
                            totallist.add(new NameEntity(nameEntity.text,nameEntity.type,nameEntity.count));
                        }
                        System.out.println("[개체명] " + nameEntity.text + " ("+nameEntity.count+")" );
                    });
            System.out.println("");

            int totallistindex=0;

            while(totallistindex<totallist.size()){
                if(totallist.get(totallistindex).text.length()==1){
                    totallist.remove(totallistindex);
                } else {
                    totallistindex++;
                }
            }

            int stopwordlistidx=-1;
            while(stopwordlistidx<(stopwordlist.size()-1)){
                stopwordlistidx++;      // -1 -> 0
                for(int i=0;i<totallist.size();i++){
                    if (totallist.get(i).text.equals(stopwordlist.get(stopwordlistidx))){
                        totallist.remove(i);
                        break;
                    }
                }
            }

            Collections.sort(totallist);
            Collections.reverse(totallist);

            for (NameEntity nameEntity : totallist) {
                if (nameEntity.count >= 4) {
                    System.out.println(nameEntity.text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}