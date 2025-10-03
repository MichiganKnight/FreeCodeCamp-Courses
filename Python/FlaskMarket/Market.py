import os.path

from flask import Flask, render_template
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
BASE_DIR = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(BASE_DIR, 'market.db')

db = SQLAlchemy(app)

class Item(db.Model):
    id = db.Column(db.Integer(), primary_key = True)
    name = db.Column(db.String(length = 30), nullable = False, unique = True)
    barcode = db.Column(db.String(length = 12), nullable = False, unique = True)
    description = db.Column(db.String(length = 1024), nullable = False, unique = True)
    price = db.Column(db.Integer(), nullable = False)

    def __repr__(self):
        return f'<Item {self.name}>'

with app.app_context():
    db.create_all()

@app.route('/')
@app.route('/home')
@app.route('/Home')
def home_page():
    return render_template('Home.html')

@app.route('/market')
@app.route('/Market')
def market_page():
    items = Item.query.all()

    return render_template('Market.html', items = items)