
class Order:
    
    """
    Attributes:
      swagger_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value an explanation of the attribute role.
    """
    swagger_types = {
        '_id': 'str',
        'user_id': 'str',
        'machine_id': 'str',
        'state': 'str',
        'drink': 'str',
    }

    attribute_map = {
        '_id': 'ID pf the event',
        'user_id': 'ID of the user that has created the order',
        'machine_id': 'ID of the machine that will make the drink',
        'state': 'state of the order can be \'available\', \'processing\' or \'done\''
        'drink': 'ID of the drink to be made',
    }

    states = ['available', 'processing', 'done']