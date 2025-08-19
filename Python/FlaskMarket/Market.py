from flask import Flask, render_template

app = Flask(__name__)

@app.route('/')
@app.route('/Home')
@app.route('/home')
@app.route('/Index')
@app.route('/index')
def home_page():
    return render_template('Home.html')

@app.route('/About/<username>')
def about(username):
    return f'<h1>About Page of {username}</h1>'

@app.route('/Market')
@app.route('/market')
def market_page():
    return render_template('Market.html')