from urllib import response
from flask import Flask, request
import json
import os
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity

app = Flask(__name__)

@app.route('/recommend', methods=['POST'])
def recommend():
    userId = request.get_json().get('userId')
   
    user = pd.read_csv('./env/anofdata/user.csv') #./env/anofdata/알레르기 테이블.csv
    allergy = pd.read_csv('./env/anofdata/userallergystate.csv')
    ingredient = pd.read_csv('./env/anofdata/userIngredientstate.csv')
    user = pd.merge(user, allergy, left_on='allergyId', right_on='id')
    user = pd.merge(user, ingredient, left_on='IngredientId', right_on='id')
    user.drop(['allergyId', 'IngredientId', 'id_y', 'id'], axis=1, inplace=True)
    user.set_index('id_x', inplace=True)

    corrMatrix_wo_std = pd.DataFrame(cosine_similarity(user), index=user.index, columns=user.index)
    corrMatrix_wo_std 

    #userId를 입력하면 가장 유사한 평점을 준 user들을 return
    def get_similarUsers(userId):
        similar_score = corrMatrix_wo_std[userId]
        # 앞서 보정된 값을 가지고 평점의 내림차순으로 정렬
        similar_score = similar_score.sort_values(ascending=False)[:5]
        similar_users = pd.DataFrame(similar_score)
        similar_users.drop(similar_users[similar_users.index==userId], inplace=True)
        return similar_users

    similar_users = get_similarUsers(userId).sort_values('id_x')
    likeProduct = pd.read_csv('./env/anofdata/userlikeproduct.csv')
    likeProduct.drop(["id"], axis=1, inplace=True)
    
    def get_recommendProducts(likeProdcut):
        
        if len(likeProduct.index)==0:
            return "0"
        
        products = []
        
        for i in similar_users.index:
            if (likeProduct['userId']==i).any(): #userId==i가 존재하는 경우 
                data = likeProduct.loc[likeProduct.userId==i]
                data = data.pivot_table('isSelect', index='userId', columns='productId')
                products.append(data.loc[i])
        
        if len(products)==0:
            return "0"
            
        arr = []
                
        matrix = pd.DataFrame(data = products)
        matrix.fillna(0, inplace=True)

        for col in range(matrix.shape[1]):
            recommendation = 0
            for row in range(matrix.shape[0]):
                recommendation += matrix.iat[row, col]*similar_users.iat[row, 0]
            arr.append(recommendation)
                
        recommendProduct = pd.DataFrame(data=arr, index=matrix.columns, columns={'recommend'})

        recommendProduct = recommendProduct.sort_values(by='recommend', ascending=False)[:10]
                
        recommendProduct.drop(recommendProduct[recommendProduct['recommend']==0].index, inplace=True)
                
        return ','.join(str(n) for n in recommendProduct.index.values.tolist())
        
    response = get_recommendProducts(likeProduct)
    return response
    
if __name__ == '__main__':
        app.run(host="0.0.0.0", port="5005", debug=True)