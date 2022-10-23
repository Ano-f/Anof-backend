from urllib import response
from flask import Flask, request
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from sqlalchemy import create_engine
import time

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

    corrMatrix_wo_std = pd.DataFrame(cosine_similarity(user), index=user.index, columns=user.index)

    def get_similarUsers(userId):
        similar_score = corrMatrix_wo_std[userId]
        #앞서 보정된 값을 가지고 내림차순으로 정렬
        similar_score = similar_score.sort_values(ascending=False)[:6]
        similar_users = pd.DataFrame(similar_score)
        
        if (userId in similar_users.index): 
            similar_users.drop(similar_users[similar_users.index==userId], inplace=True)
            
        for i in range(len(similar_users)):
            similarity = similar_users.iat[i,0]
            if(similarity==0):
                similar_users.drop(similar_users.index[i], inplace=True)
            
        print(similar_users)
        return similar_users
    
    def get_likeproductBySimilarUsers():
        products = []
        products_append = products.append
        
        for i in similar_users.index:   #유사한 사용자들의 선호 식품을 기반으로 추천 식품 choice
            if (likeProduct['userId']==i).any(): #userId==i가 존재하는 경우 
                data = likeProduct.loc[likeProduct.userId==i]
                data = data.pivot_table('isSelect', index='userId', columns='productId')
                print(data)
                products_append(data.loc[i])
            else: similar_users.drop(index=i, inplace=True)
        
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
    
    if len(products)==0: #유사 사용자의 선호 식품이 없는 경우
        response = "0"
        return response
            
    arr = []
    arr_append = arr.append
                
    matrix = pd.DataFrame(data = products)
    matrix.fillna(0, inplace=True)

    for col in range(matrix.shape[1]):
        recommendation = 0
        for row in range(matrix.shape[0]):
            recommendation += matrix.iat[row, col]*similar_users.iat[row, 0]
        arr_append(recommendation)
              
    recommendProduct = pd.DataFrame(data=arr, index=matrix.columns, columns={'recommend'})

    recommendProduct = recommendProduct.sort_values(by='recommend', ascending=False)[:10]
    print(recommendProduct)
                
    recommendProduct.drop(recommendProduct[recommendProduct['recommend']==0].index, inplace=True)
        
    response = ','.join(str(n) for n in recommendProduct.index.values.tolist())
    return response
    
if __name__ == '__main__':
        app.run(host="0.0.0.0", port="5005", debug=True)