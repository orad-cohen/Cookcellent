from flask import Flask, request, json
from Cookcellent import funcs
from json import *
# Flask Constructor
app = Flask(__name__)


# decorator to associate
# a function with the url
@app.route("/")
def showHomePage():
    # response from the server
    print("accessed")
    return "Cookcellent is unavailable at the moment"

@app.route("/hello")
def sayHello():
    name = request.args.get('name')
    return "Hello "+name

@app.route("/hey", methods=['GET', 'POST'])
def add_message():
    data = json.loads(request.get_data())
    mada = request.get_json()
    print(data)
    print(mada)
    rd = "{\"Recipe\":\"Cheese cake\", \"Ingredients\": [\"cream cheese\", \"Milk\",\"sugar\"]}"
    return rd

@app.route("/rawIngredients",methods=['GET','POST'])
def get_Ingredients():
    ings = funcs.getIngridients()
    answer = {"Ingredients":ings}

    return json.dumps(answer)


if __name__ == "__main__":
    app.run(host="0.0.0.0")