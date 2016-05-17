/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.ContentTypeInterceptor', [])
        .factory('ContentTypeInterceptor', function responseObserver($q) {
            var xmlContentType = 'application/xml',
                jsonContentType = 'application/json',
                charsetUTF8 = 'charset=utf-8',
                x2js = new X2JS(),
                transformRequest = function (request) {
                    if ((request.headers['Content-Type'] || "").indexOf(jsonContentType) !== -1) {
                        request.headers['Content-Type'] = xmlContentType + ';' + charsetUTF8;
                        request.data = x2js.json2xml_str(request.data)
                    }
                    return request;
                },
                transformResponse = function (response) {
                    if (typeof (response.headers) == 'function'
                        && (response.headers()['content-type'] || "").indexOf(xmlContentType) !== -1) {
                        response.headers()['content-type'] = jsonContentType + ';' + charsetUTF8;
                        response.data = x2js.xml_str2json(response.data)
                    }
                    return response;
                };

            return {
                'request': function (request) {
                    return transformRequest(request);
                },
                'requestError': function (rejection) {
                    return $q.reject(transformRequest(rejection));
                },
                'response': function (response) {
                    return transformResponse(response);
                },
                'responseError': function (rejection) {
                    return $q.reject(transformResponse(rejection));
                }
            };
        });
}(angular));
