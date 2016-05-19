/**
 * Created by Srđan on 18.5.2016..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.Users', [])
        .factory('Users', function ($http) {
            return {
                get: function (id) {

                },
                query: function () {
                    return $http({
                        method: 'GET',
                        url: 'api/users'
                    })
                }
            };
        });
}(angular));