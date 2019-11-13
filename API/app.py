import json
from flask import g
from flask import request
from flask_cors import CORS
from flask_httpauth import HTTPTokenAuth

from models.Event import Event
from models.Event import Order_Process_Exception
from models.User import User
from models.User import User_Creation_Exception
from models.Machine import Machine
from models.Machine import Machine_Creation_Exception
from models.Order import Order
from models.Drink import Drink


from init import app, JSON_MIME_TYPE
from utils import json_response

auth = HTTPTokenAuth(scheme='Token')


@auth.verify_token
def verify_token(token):
    if token is None:
        return False
    if token[:7] == "machine":
        current_machine = Machine.find_by_token(token)
        if current_machine:
            g.current_machine = current_machine
            return True
        return False
    current_user = User.find_by_token(token)
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

# ---------------------- EVENT END-POINTS ---------------------- #
@app.route('/api/v1/event', methods=['POST'])
def post_event():
    """
    {
      "name":"your moms a hoe",
      "location":"ur moms house"
    }
    """
    if request.content_type != JSON_MIME_TYPE:
        app.logger.info('Invalid Content Type')
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    data = request.json
    new_event = Event(name=data['name'], location=data['location'])
    new_event.create()
    return json_response(json.dumps(new_event.to_dict()), status=201)

# ---------------------- USER END-POINTS ---------------------- #
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
        app.logger.info('Invalid Content Type')
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    data = request.json
    new_user = User(name=data['name'],
                    email=data['email'],
                    password=data['password'])
    try:
        new_user.create()
    except User_Creation_Exception:
        app.logger.info('Email already used for existing user')
        error = json.dumps({'error': "Email already used for existing user"})
        return json_response(error, 409)
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
        app.logger.info('Invalid Content Type')
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    data = request.json
    user = User.find(data['email'])
    if user is None:
        return json_response(status=404)
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
    if g.current_user is None:
        app.logger.info('No user found might have been a machine token')
        return json_response(status=401)
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
        app.logger.info('Invalid Content Type')
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    if g.current_user is None:
        app.logger.info('No user found might have been a machine token')
        return json_response(status=401)
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
    if g.current_user is None:
        app.logger.info('No user found might have been a machine token')
        return json_response(status=401)
    event = Event.find(event_key)
    if event is None:
        app.logger.info('Event not found')
        error = json.dumps({'error': 'Event not found'})
        return json_response(error, 404)
    event.add_user(g.current_user)
    return json_response(json.dumps(event.to_dict()), status=200)

# ---------------------- MACHINE END-POINTS ---------------------- #
@app.route('/api/v1/machine', methods=['POST'])
@auth.login_required
def post_machine():
    """
    header Authorization : Token the_user_token
    """
    if g.current_user is None:
        app.logger.info('No user found might have been a machine token')
        return json_response(status=401)
    new_machine = Machine()
    try:
        new_machine.create(g.current_user)
    except Machine_Creation_Exception:
        app.logger.info('User: '+str(g.current_user._id)+' unautherised for this creation action')
        return json_response(status=401)
    return json_response(json.dumps(new_machine.to_dict()), status=200)


@app.route('/api/v1/machine/event/<string:event_key>', methods=['POST'])
@auth.login_required
def post_machine_to_event(event_key):
    """
    header Authorization : Token the_machine_token
    /api/v1/user/event/<string:event_key>
    """
    if g.current_machine is None:
        app.logger.info('No machine found might have been a user token')
        return json_response(status=401)
    event = Event.find(event_key)
    if event is None:
        app.logger.info('Event not found')
        error = json.dumps({'error': 'Event not found'})
        return json_response(error, 404)
    event.add_machine(g.current_machine)
    return json_response(json.dumps(event.to_dict()), status=200)


@app.route('/api/v1/machine/order/<string:event_key>/<string:order_key>', methods=['POST'])
@auth.login_required
def machine_get_order(event_key, order_key):
    """
    header Authorization : Token the_machine_token
    """
    if g.current_machine is None:
        app.logger.info('No machine found might have been a user token')
        return json_response(status=401)
    if g.current_machine.selected_order is not None:
        app.logger.info('Machine has not finished its selected order')
        error = json.dumps({'error': 'Machine not finished selected order'})
        return json_response(error, 403)
    order = Order.find(order_key)
    if order is None:
        app.logger.info('Order not found')
        error = json.dumps({'error': 'Order not found'})
        return json_response(error, 404)
    if order.machine_id is not None:
        app.logger.info('Order already has an associated machine')
        error = json.dumps({'error': 'Order already has an associated machine'})
        return json_response(error, 403)
    event = Event.find(event_key)
    if event is None:
        app.logger.info('Event not found')
        error = json.dumps({'error': 'Event not found'})
        return json_response(error, 404)
    try:
        event.machine_get_order(order)
    except Order_Process_Exception:
        app.logger.info('Order: '+str(order._id)+' was not found in the new_orders of event: '+str(event._id))
        error = json.dumps({'error': 'Order was not found in event new order'})
        return json_response(error, 404)
    g.current_machine.set_selected_order(order)
    order.assigne_machine(g.current_machine._id)
    return json_response(json.dumps(order.to_dict()), status=200)


@app.route('/api/v1/machine/order/done/<string:event_key>', methods=['POST'])
@auth.login_required
def machine_post_order_completed(event_key):
    """
    header Authorization : Token the_machine_token
    """
    if g.current_machine is None:
        app.logger.info('No machine found might have been a user token')
        return json_response(status=401)
    if g.current_machine.selected_order is None:
        app.logger.info('Machine has no order to finsih')
        error = json.dumps({'error': 'Machine has no order to finish'})
        return json_response(error, 403)
    order = Order.find_by_id(g.current_machine.selected_order)
    if order is None:
        app.logger.info('Order not found')
        error = json.dumps({'error': 'Order not found'})
        return json_response(error, 404)
    if order.machine_id is None:
        app.logger.info('Order does not have machine id, it should have one')
        error = json.dumps({'error': 'Order does not have machine_id'})
        return json_response(error, 403)
    event = Event.find(event_key)
    if event is None:
        app.logger.info('Event not found')
        error = json.dumps({'error': 'Event not found'})
        return json_response(error, 404)
    try:
        event.machine_finished_order(order)
    except Order_Process_Exception:
        app.logger.info('Order: '+str(order._id)+' was not found in the new_orders of event: '+str(event._id))
        error = json.dumps({'error': 'Order was not found in event new order'})
        return json_response(error, 404)
    g.current_machine.set_selected_order_done()
    order.set_done()
    return json_response(json.dumps(order.to_dict()), status=200)


CORS(app)
