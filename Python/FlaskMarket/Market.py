from flask import Flask, render_template

app = Flask(__name__)

@app.route('/')
@app.route('/Home')
@app.route('/home')
@app.route('/Index')
@app.route('/index')
def home_page():
    return render_template('home.html')

@app.route('/About/<username>')
def about(username):
    return f'<h1>About Page of {username}</h1>'