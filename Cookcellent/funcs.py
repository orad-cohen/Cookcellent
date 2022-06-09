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
    test = SearchCollection.find({"search":searchHash})
    for recipe in print(test.__getitem__(0)['Recipe']):
        recipeList.append(recipe)
    return recipeList

def reCombineAndFind(Ingredient,limit):
    Ingredients = list(Ingredient)
    for i in range(len(Ingredients)):
        tempIngredients = Ingredients.pop(i)
        Ingredients.insert(i, tempIngredients)
    for i in range(len(Ingredients)):
        for j in range(len(Ingredients)):
            if i >= j:
                continue
            else:
                tempIngredientsJ = Ingredients.pop(j)
                tempIngredientsI = Ingredients.pop(i)

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

                    Ingredients.insert(k, tempIngredientsK)
                    Ingredients.insert(j, tempIngredientsJ)
                    Ingredients.insert(i, tempIngredientsI)

def getIngridients():
    Ingredients = IngridientsCollection.find()
    print(Ingredients.__sizeof__())
    result = []
    for single in Ingredients:
        result.append(single["Ingredient_name"])
    return result
