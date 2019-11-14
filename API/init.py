import os
from pymongo import MongoClient
from logging.config import dictConfig
from flask import Flask

mongodb_host = os.environ.get('MONGODB_HOST', 'localhost')
mongodb_port = os.environ.get('MONGODB_PORT', '27017')

dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {'wsgi': {
        'class': 'logging.StreamHandler',
        'stream': 'ext://flask.logging.wsgi_errors_stream',
        'formatter': 'default'
    }},
    'root': {
        'level': 'INFO',
        'handlers': ['wsgi']
    }
})

app = Flask(__name__)

client = MongoClient('mongodb://'+mongodb_host+':'+mongodb_port+'/', connect=False)
mongo_db = client['taste_of_heaven']

JSON_MIME_TYPE = 'application/json'
