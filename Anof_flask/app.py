from urllib import response
from flask import Flask, request
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from sqlalchemy import create_engine
import numpy as np

app = Flask(__name__)

@app.route('/recommend', methods=['POST'])
def recommend():
    userId = request.get_json().get('userId')
    
    engine = create_engine("mysql+pymysql://anof:"+"anof1234"+"@anof-db.cev71n12sity.ap-northeast-2.rds.amazonaws.com:3306/anof?charset=utf8", encoding='utf-8')
    conn = engine.connect()
    
    user = pd.read_sql_table('User', con=conn)
    allergy = pd.read_sql_table('Allergy', con=conn)
    ingredient = pd.read_sql_table('Ingredient', con=conn)
    likeProduct = pd.read_sql_table('LikeProduct', con=conn)
    
    conn.close()
    
    user = user[['id', 'allergyId', 'IngredientId']]
    
    user = pd.merge(user, allergy, left_on='allergyId', right_on='id')
    user = pd.merge(user, ingredient, left_on='IngredientId', right_on='id')
    user.drop(['allergyId', 'IngredientId', 'id_y', 'id'], axis=1, inplace=True)
    user.set_index('id_x', inplace=True)

    corrMatrix_wo_std = pd.DataFrame(cosine_similarity(user, user), index=user.index, columns=user.index)

    def get_similarUsers(userId):
        similar_score = corrMatrix_wo_std[userId]
        similar_score = similar_score.sort_values(ascending=False)[:6]
        similar_users = pd.DataFrame(similar_score)
        
        if (userId in similar_users.index): 
            similar_users.drop(similar_users[similar_users.index==userId], inplace=True)
            
        similar_users = similar_users.xs(userId, axis=1)  
            
        for i in similar_users.index:
            similarity = similar_users.at[i]
            if(similarity==0):
                similar_users.drop(index=i, inplace=True)
        return similar_users
    
    def get_likeproductBySimilarUsers():
        
        products = pd.DataFrame()
        
        for i in similar_users.index:   #유사한 사용자들의 선호 식품을 기반으로 추천 식품 choice
            if (likeProduct['userId']==i).any(): 
                data = likeProduct.loc[likeProduct.userId==i]
                data = data.pivot_table('isSelect', index='userId', columns='productId')
                dataframe = pd.DataFrame(data)
                dataframe = dataframe*similar_users.at[i]
                products = pd.concat([products, dataframe])
        
        return products
    
    similar_users = get_similarUsers(userId)
    
    if(similar_users.empty): 
        response = "0"
        return response
    
    likeProduct.drop(["id"], axis=1, inplace=True)
        
    if len(likeProduct.index)==0: #선호 식품 db가 null인 경우
        response = "0"
        return response
    
    products = get_likeproductBySimilarUsers()
    
    if products.empty: #유사 사용자의 선호 식품이 없는 경우
        response = "0"
        return response
         
    products.fillna(0, inplace=True)
    
    products = pd.DataFrame(products.sum())
    products.drop(products[products[0]==0].index, inplace=True)
    products.sort_values(by=0, ascending=False)[:10]
    
    response = ','.join(str(n) for n in products.index.values.tolist())
    
    return response
    
if __name__ == '__main__':
        app.run(host="0.0.0.0", port="5005", debug=True)