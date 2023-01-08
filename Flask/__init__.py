from flask import Flask, request

app = Flask(__name__)


@app.route('/')
def home():
    return "home"


if __name__ == '__name__':
    app.run(debug=True)