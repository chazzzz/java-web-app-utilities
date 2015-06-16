Java WebApp Utilities
======================

A collection of utility classes frequently used in building a web application

HttpUtil
-----------------
Used in sending requests (e.g. get, post, put, delete) to a url resource.
Every methods returns Apache's `HttpResponse` object. With this object, 
we can get the status code, headers, and content of the response.

Methods:
``HttpUtil.post(String url, Map<String, String> params);``

``HttpUtil.get(String url, Map<String, String> params);``

``HttpUtil.get(String url, Map<String, String> params, Map<String, String> headers);``

``HttpUtil.postJson(String url, String jsonData, Map<String, String> headers);``

``HttpUtil.delete(String url, Map<String, String> headers);``

To get the String content of the HttpResponse, use the method:

``HttpUtil.getContent(HttpResponse response); //returns the string content of the response``

To get the status code of the response, use the statement

``response.getStatusLine().getStatusCode(); //returns int code``
