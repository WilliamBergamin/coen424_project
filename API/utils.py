from flask import make_response

from init import JSON_MIME_TYPE


def json_response(data='', status=200, headers=None):
    headers = headers or {}
    if 'Content-Type' not in headers:
        headers['Content-Type'] = JSON_MIME_TYPE
    res = make_response(data, status, headers)
    return res
