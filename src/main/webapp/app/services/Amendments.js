/**
 * Created by Aleksandar Lukić on 23.5.16..
 */


(function (angular) {
    'use strict';

    angular.module('app.Amendments', [])

        .factory('Amendments', ['$http', function ($http) {

            return {
                
                getByActURI: function (actURI) {
                    return $http({
                       method: 'GET',
                        url: 'api/amendments/' + actURI
                    });  
                },

                getAllFor: function (username) {
                    return  $http({
                        method: 'GET',
                        url: 'api/amendments/?username=' + username
                    });
                },

                get: function (status) {
                    return  $http({
                        method: 'GET',
                        url: 'api/amendments/',
                        params: status
                    });
                },

                getHtml: function (id) {
                    return $http({
                        method: 'GET',
                        url: 'api/export/amendment/html/' + id
                    });
                },

                remove: function (amendmentId) {
                    return  $http({
                        method: 'DELETE',
                        url: 'api/amendments/' + amendmentId
                    });
                },

                update: function (amendmentId) {
                    return  $http({
                        method: 'PUT',
                        url: 'api/amendments/' + amendmentId
                    });
                },

                create: function () {
                    return  $http({
                        method: 'POST',
                        url: 'api/amendments/'
                    });
                }
            };
        }]);

}(angular));