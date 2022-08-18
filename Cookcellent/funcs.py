from pymongo import MongoClient

from configuration import config

conn_str = config.conn_string
client = MongoClient(conn_str, serverSelectionTimeoutMS=5000)
database = client['Cookcellent']
IngridientsCollection = database['ingredient']
RecipeDetailsColleciton = database['recipes_detail']
SearchCollection = database['searchhash']

def findAndRetrieve(IngredientsList):
    IngredientsList.sort()
    searchHash = ""
    for comp in IngredientsList:
        searchHash += comp
    recipeList = []

    test = SearchCollection.find({"search": searchHash})
    count= SearchCollection.count_documents({"search":searchHash})
    print(searchHash)
    if(count>0):
        for recipe in test:
            recipeList.append(recipe['Recipe'][0]['index'])

    return recipeList

def reCombineAndFind(Ingredient,limit =0):
    if(len(Ingredient)==0 or limit==3):
        return []
    Ingredients = list(Ingredient)
    indexes=[]
    indexes.extend(findAndRetrieve(Ingredients))
    for i in range(len(Ingredients)):
        tempIngredients = Ingredients.pop(i)
        indexes.extend(reCombineAndFind(Ingredients,limit=limit+1))
        Ingredients.insert(i, tempIngredients)

    return indexes

def getIngridients():
    Ingredients = IngridientsCollection.find()

    result = []
    for single in Ingredients:
        result.append(single["Ingredient_name"])
    return result

def getRecipe(index):

    recipeCur = RecipeDetailsColleciton.find({"id":index},{"_id":0})

    return recipeCur