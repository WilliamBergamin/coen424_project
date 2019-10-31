import json
import os
from flask import request
from flask_cors import CORS
from flask import make_response
from flask_httpauth import HTTPTokenAuth

from init import app, JSON_MIME_TYPE, mongo_db

auth = HTTPTokenAuth('Token')

api_token = os.environ.get('API_TOKEN')


@auth.verify_token
def verify_token(token):
    # if not int(os.environ.get('ENABLE_FLASK_AUTH')):
    if not int(0):
        return True
    if token == api_token:
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


@app.route('/job', methods=['POST'])
def post_event():
    """
    Post a new job for the worker to execute.
    :param name: name of the model that will execute the job
    :param images: list of images that must be processed
    :return: The id of the new job.
    :rtype: JSON response
    """
    if request.content_type != JSON_MIME_TYPE:
        error = json.dumps({'error': 'Invalid Content Type'})
        return json_response(error, 400)
    app.logger.info("Request received for hydra")
    data = request.json
    name = data['name']
    dwarf = Dwarf(name=name, model_host_path=None)
    res = dwarf.get()
    if res['state'] != 'up':
        return auth.login_required(json_response(json.dumps(res), status=404))
    info = name.split(":")
    with Connection(REDIS_CONN):
        job = Queue(name=info[1]).enqueue_call(func="model_wrappers.model_wrapper_manager.run_model_wrapper",
                                               args=(info[0], data,),
                                               result_ttl=864000, timeout=600)
        app.logger.info("Request placed on queue succesfully")
    return auth.login_required(json_response(json.dumps({
                                                        "job_id": job.get_id()
                                                        }), status=201))
