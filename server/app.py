from flask import Flask, request
import time

app = Flask(__name__)

@app.route("/")
def hello():
    print request.method
    return "hello"

@app.route("/timeout")
def input():
    method = request.method
    time.sleep( 600 )
    return method + ' done'

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80)
