from init import mongo_db

events = mongo_db.events


class Event():

    """
    Attributes:
      swagger_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value an explanation of the attribute role.
    """
    swagger_types = {
        '_id': 'ID',
        'name': 'str',
        'location': 'str',
        'new_orders': 'list',
        'pending_orders': 'list',
        'processed_orders': 'list',
        'machines': 'list'
    }

    attribute_map = {
        '_id': 'id of the event',
        'name': 'name of the event',
        'location': 'location of the address',
        'new_orders': 'list of active orders in the event',
        'pending_orders': 'list of orders that are being processed by machine',
        'processed_orders': 'list of orders that were processed',
        'machines': 'list of machines registered to this event'
    }

    def __init__(self, name, location):
        self.name = name
        self.location = location
        self.new_orders = []
        self.pending_orders = []
        self.processed_orders = []
        self.machines = []

    def create(self):
        new_event = {
            'name': self.name,
            'location': self.location,
            'new_orders': self.new_orders,
            'pending_orders': self.pending_orders,
            'processed_orders': self.processed_orders,
            'machines': self.machines
        }
        self._id = events.insert_one(new_event).inserted_id

    def save(self):
        query = {"_id": self._id}
        new_values = {"$set": {
                'name': self.name,
                'location': self.location,
                'new_orders': self.new_orders,
                'pending_orders': self.pending_orders,
                'processed_orders': self.processed_orders,
                'machines': self.machines
            }
        }
        events.update_one(query, new_values)

    @classmethod
    def find(cls, _id):
        found_event_data = events.find_one({"_id": _id})
        found_event = cls(found_event_data.get('name'),
                          found_event_data.get('location'))
        found_event.new_orders = found_event_data.get('new_orders')
        found_event.pending_orders = found_event_data.get('pending_orders')
        found_event.processed_orders = found_event_data.get('processed_orders')
        found_event.machines = found_event_data.get('machines')
        found_event._id = found_event_data.get('_id')
        return found_event

    def to_dict(self):
        return {
            '_id': str(self._id),
            'name': self.name,
            'location': self.location,
            'new_orders': self.new_orders,
            'pending_orders': self.pending_orders,
            'processed_orders': self.processed_orders,
            'machines': self.machines
        }
