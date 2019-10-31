

class Event:

    """
    Attributes:
      swagger_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value an explanation of the attribute role.
    """
    swagger_types = {
        'id': 'ID'
        'name': 'str',
        'location': 'str',
        'new_orders': 'list',
        'pending_orders': 'list'
        'processed_orders': 'list',
        'machines': 'list'
    }

    attribute_map = {
        'id': 'id of the event'
        'name': 'name of the event',
        'location': 'location of the address',
        'new_orders': 'list of active orders in the event',
        'pending_orders': 'list of orders that are being processed by machine'
        'processed_orders': 'list of orders that were processed',
        'machines': 'list of machines registered to this event'
    }

    @classmethod
    def create(cls, name, locations):

    @classmethod
    def get(cls, id):


    @classmethod
    def delete(cls, id):


    @classmethod
    def update(cls, id, name, location):

    
    def get_new_order(machine_id, order_id):
    

    def post_order_processed(machine_id, order_id):

    def post_machine(machine_id):
        
    

