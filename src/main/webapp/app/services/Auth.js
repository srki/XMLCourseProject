/**
 * Created by Srđan Milaković on 17.5.16..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.Auth', [])
        .factory('Auth', function ($http) {
            return {
                isLogged: function () {
                    return $http({
                        method: 'GET',
                        url: '/api/login'
                    });
                },
                login: function (user) {
                    return $http({
                        method: 'POST',
                        url: '/api/login',
                        data: {user: user}
                    });
                },
                register: function (user) {
                    return $http({
                        method: 'POST',
                        url: '/api/register',
                        data: {user: user}
                    });
                }
            };
        });
}(angular));