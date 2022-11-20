from flask import Flask, render_template
from flask import request
from truck import *

app = Flask(__name__)
message="msg(in,request,react,echo,in(M,Q),1)"

@app.route('/')
def index():
    connect(8000)
    return render_template('index.html')

@app.route('/sendRequest', methods=['POST'])
def sendRequest():
    log=""
    temp_r = message.replace('M', request.form['material'])
    req = temp_r.replace('Q', request.form['weight'])

    temp_log = request.form['textarea'] + '\n' + req
    reply = send_request(message=req)
    log = temp_log + '\n' + reply
    print(log)
    return render_template('index.html', log=log)
