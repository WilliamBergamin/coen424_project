import json
import os
from flask import g
from flask import request
from flask_cors import CORS
from flask import make_response
from flask_httpauth import HTTPTokenAuth
import base64

from models.Event import Event
from models.User import User
from models.Order import Order
from models.Drink import Drink

from init import app, JSON_MIME_TYPE, mongo_db

auth = HTTPTokenAuth(scheme='Token')


@auth.verify_token
def verify_token(token):
    if token is None:
        return False
    app.logger.info(token)
    current_user = User.find_by_token(token)
    app.logger.info(current_user)
    if current_user:
        g.current_user = current_user
        return True
    return False


@app.route('/', methods=['GET'])
def test():
    """
    Gets informaation about the application.
    :return: Information about this application.
    :rtype: str
    """
    return 'very AI, many Knowledge, much Cloud'


@app.route('/api/v1/event', methods=['POST'])
def post_event():
    """
    {
      "name":"your moms a hoe",
      "location":"ur moms house"
    }
    """
    if request.content_type != JSON_MIME_TYPE:
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    app.logger.info("Request received")
    data = request.json
    new_event = Event(name=data['name'], location=data['location'])
    new_event.create()
    return json_response(json.dumps(new_event.to_dict()), status=201)


@app.route('/api/v1/user', methods=['POST'])
def post_user():
    """
    {
      "name":"your moms a hoe",
      "email":"cool@gmail.com",
      "password":"ur moms house"
    }
    """
    if request.content_type != JSON_MIME_TYPE:
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    app.logger.info("Request received")
    data = request.json
    if User.exist(data['email']):
        return json_response(status=409)
    new_user = User(name=data['name'],
                    email=data['email'],
                    password=data['password'])
    new_user.create()
    return json_response(json.dumps(new_user.to_dict()), status=201)


@app.route('/api/v1/user/token', methods=['GET'])
def get_token():
    """
    {
      "email":"cool@gmail.com",
      "password":"ur moms house"
    }
    """
    if request.content_type != JSON_MIME_TYPE:
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    app.logger.info("Request received")
    data = request.json
    if not User.exist(data['email']):
        return json_response(status=404)
    user = User.find(data['email'])
    user.get_token(data["password"])
    user.save()
    if user.token is None:
        return json_response(json.dumps(user.to_dict()), status=401)
    return json_response(json.dumps(user.to_dict()), status=200)


@app.route('/api/v1/user', methods=['GET'])
@auth.login_required
def get_user():
    """
    header WWW-Authenticate: Token realm="Authentication Required"
    """
    app.logger.info("Request received")
    return json_response(json.dumps(g.current_user.to_dict()), status=200)


@app.route('/api/v1/order', methods=['POST'])
@auth.login_required
def post_order():
    """
    header Authorization : Token the_user_token
    {
      "event_key": "y37jsnks",
      "drinks": [{
          "mixer_type": "coke",
          "alcohol_type": "rhum",
          "double": True
      }],
    }
    """
    if request.content_type != JSON_MIME_TYPE:
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    app.logger.info("Request received")
    data = request.json
    event = Event.find(data.get('event_key'))
    if event is None:
        error = json.dumps({'error': 'Event not found'})
        return json_response(error, 404)
    drinks = [Drink(drink.get('mixer_type'),
                    drink.get('alcohol_type'),
                    drink.get('double')) for drink in data.get('drinks')]
    # total_price = Order.get_price_from_drinks(drinks)
    # TODO process transation, for now assume the trasaction always passes
    new_order = Order(g.current_user._id, drinks, payed=True)
    new_order.create()
    event.add_new_order(new_order)
    g.current_user.add_order(new_order)
    return json_response(json.dumps(new_order.to_dict()), status=200)


@app.route('/api/v1/user/event/<string:event_key>', methods=['POST'])
@auth.login_required
def post_user_to_event(event_key):
    """
    header Authorization : Token the_user_token
    /api/v1/user/event/<string:event_key>
    """
    app.logger.info("Request received")
    event = Event.find(event_key)
    if event is None:
        error = json.dumps({'error': 'Event not found'})
        return json_response(error, 404)
    event.add_user(g.current_user)
    return json_response(json.dumps(event.to_dict()), status=200)


def json_response(data='', status=200, headers=None):
    headers = headers or {}
    if 'Content-Type' not in headers:
        headers['Content-Type'] = JSON_MIME_TYPE
    res = make_response(data, status, headers)
    return res


CORS(app)
