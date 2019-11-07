import bcrypt
import datetime
import base64

from init import mongo_db, app

users = mongo_db.users


class User():

    """
    Attributes:
      swagger_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value an explanation of the attribute role.
    """
    swagger_types = {
        '_id': 'str',
        'name': 'str',
        'email': 'str',
        'password': 'str',
        'token': 'str',
        'orders': 'str',
    }

    attribute_map = {
        '_id': 'ID pf the user',
        'email': 'Email of the user',
        'password': 'hashed password of the user',
        'token': 'token used to validate the user is signed in',
        'orders': 'ids of the orders associated with the user'
    }

    def __init__(self, name, email, password):
        salt = bcrypt.gensalt()
        self.name = name
        self.email = email
        self.password = bcrypt.hashpw(password.encode('utf8'), salt)
        self.token = None
        self.orders = []
        if bcrypt.checkpw(password.encode('utf8'),self.password):
            print("true")
        else:
            print("false")

    def create(self):
        new_user = {
            'name': self.name,
            'email': self.email,
            'password': self.password,
            'token': self.token,
            'orders': self.orders
        }
        self._id = users.insert_one(new_user).inserted_id

    def save(self):
        query = {"_id": self._id}
        new_values = {"$set": {
                'name': self.name,
                'email': self.email,
                'token': self.token,
                'orders': self.orders
            }
        }
        users.update_one(query, new_values)

    def get_token(self, password):
        if bcrypt.checkpw(password.encode('utf8'), self.password):
            # base 64 encoded datetime:email
            str_token = str(datetime.datetime.now())+":"+self.email
            self.token = base64.urlsafe_b64encode(str_token.encode('utf8')).decode('utf8')
        else:
            self.token = None

    @classmethod
    def find(cls, email):
        found_user = users.find_one({'email': email})
        user = cls(found_user.get("name"),
                   found_user.get("email"),
                   "")
        user._id = found_user.get("_id")
        user.password = found_user.get("password")
        user.token = found_user.get("token")
        user.orders = found_user.get("orders")
        return user

    @classmethod
    def find_by_token(cls, token):
        app.logger.info(token)
        found_user = users.find_one({'token': token})
        app.logger.info(found_user)
        if found_user is None:
            return None
        user = cls(found_user.get("name"),
                   found_user.get("email"),
                   "")
        user._id = found_user.get("_id")
        user.password = found_user.get("password")
        user.token = found_user.get("token")
        user.orders = found_user.get("orders")
        return user

    @staticmethod
    def exist(email):
        if users.find({'email': email}).count() > 0:
            return True
        return False

    def to_dict(self):
        return {
            '_id': str(self._id),
            'name': self.name,
            'email': self.email,
            'token': self.token,
            'orders': self.orders,
        }
