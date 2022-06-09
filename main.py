
import bson
from bs4 import BeautifulSoup
import pymongo
from pymongo import MongoClient
import pprint
import requests
import json
from configuration import config

conn_str = config.connection_string
client = MongoClient(conn_str, serverSelectionTimeoutMS=5000)
database = client['Cookcellent']
IngridientsCollection = database['ingredient']
RecipeDetailsColleciton = database['recipes_detail']
SearchCollection = database['searchhash']


def RetrieveIngredients(Ingreintslist):
    recipeIngredient = []
    for Ingredient in Ingreintslist:
        url = 'https://trackapi.nutritionix.com/v2/natural/nutrients'
        header = {'Content-Type': 'application/json', 'x-app-id': '759de310',
                      'x-app-key': '78f2efe0b3b925b64b1b6544a7be09c1'}
        query = {'query': str(Ingredient)}
        jsonquery = json.loads(json.dumps(query))
        ans = requests.post(url, headers=header, json=jsonquery)
        try:
            print(ans.json())
            ingredient = ans.json()["foods"][0]["food_name"]
            recipeIngredient.append({"Ingredient_name": ingredient})
        except Exception:
            print("an error occured ")
    return recipeIngredient;
def RetrieveDirections(DirectionList):
    Steps=[]
    for step in DirectionList:
        Steps.append(step['text'])
    return Steps
def PrepareRecipeDetails(htmlJson,index):
    name = htmlJson[1]['name']
    link = htmlJson[1]['mainEntityOfPage']
    image= htmlJson[1]['image']
    rating=htmlJson[1]['aggregateRating']
    Ingredients=htmlJson[1]['recipeIngredient']
    directions = []
    for block in htmlJson[1]['recipeInstructions']:
        directions.append(block['text'])
    final={'id':index,'name' : name,'link':link,'image':image,'rating':rating,'ingredients':Ingredients,'directions':directions}
    return final

def FoodName(array):
    return array.get('Ingredient_name')

def reCombine(Ingredient, recipeIndex):
    Ingredients = list(Ingredient)
    for i in range(len(Ingredients)):
        tempIngredients = Ingredients.pop(i)
        sortAndUpload(Ingredients,recipeIndex,1)
        Ingredients.insert(i, tempIngredients)
    for i in range(len(Ingredients)):
        for j in range(len(Ingredients)):
            if i >= j:
                continue
            else:
                tempIngredientsJ = Ingredients.pop(j)
                tempIngredientsI = Ingredients.pop(i)

                sortAndUpload(Ingredients,recipeIndex, 2)
                Ingredients.insert(j, tempIngredientsJ)
                Ingredients.insert(i, tempIngredientsI)
    for i in range(len(Ingredients)):
        for j in range(len(Ingredients)):
            for k in range(len(Ingredients)):
                if i >= j or j >= k:
                    continue
                else:
                    tempIngredientsK = Ingredients.pop(k)
                    tempIngredientsJ = Ingredients.pop(j)
                    tempIngredientsI = Ingredients.pop(i)
                    sortAndUpload(Ingredients,recipeIndex, 3)
                    Ingredients.insert(k, tempIngredientsK)
                    Ingredients.insert(j, tempIngredientsJ)
                    Ingredients.insert(i, tempIngredientsI)

def sortAndUpload(Ingredients,recipeIndex,level):
    Ingredients.sort()
    searchHash=""
    for comp in Ingredients:
        searchHash+=comp
    to_add = {'Recipe': {'index': str(recipeIndex), 'level': str(level)}}
    SearchCollection.update_one({'search': searchHash}, update={"$push": to_add}, upsert=True)

def RetreieveData(index):

    url = "https://www.allrecipes.com/recipe/"
    surl = url + index
    r = requests.get(surl, allow_redirects=True)
    sfq = BeautifulSoup(r.text, features="html.parser")
    if(sfq.head.title.text=="Allrecipes - File Not Found"):
        return
    JsonData = sfq.find("script", {'type': "application/ld+json"})
    if(JsonData==None):
        return
    data = json.loads(JsonData.text)
    print("Retrieving Ingredients")
    IngredientList= RetrieveIngredients(data[1]['recipeIngredient'])
    IngredientListRaw = []
    for Ingre in IngredientList:
        IngredientListRaw.append(Ingre["Ingredient_name"])
    print(IngredientListRaw)
    RecipeDetails = PrepareRecipeDetails(data,index)


    print("Uploading Ingredients to database")
    for i in IngredientList:
        IngridientsCollection.update_one({"Ingredient_name":i['Ingredient_name']},update={"$set":{"Ingredient_name":i['Ingredient_name']}},upsert=True)
    print("Uploading Recipe Details")
    RecipeDetailsColleciton.insert_one(RecipeDetails)

    print("Uploading to search string")
    sortAndUpload(IngredientListRaw,index,0)






def main():
    index = int(input("enter index: "))
    range = int(input("enter range: "))+index
    while(index<range):
        RetreieveData(str(index))
        index += 1

if __name__=="__main__":
    main()




