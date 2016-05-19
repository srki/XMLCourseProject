/**
 * Created by Srđan Milaković on 17.5.16..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.Auth', [])
        .factory('Auth', function ($http, $window, $rootScope) {
            var storeData = function (role, username) {
                $rootScope.display = role;
                $rootScope.username = username;
                $window.localStorage.setItem('display', role);
                $window.localStorage.setItem('username', username);
            };

            return {
                isLogged: function (callback) {
                    return $http({
                        method: 'GET',
                        url: '/api/login'
                    }).then(
                        function (response) {
                            storeData(response.data.user.type, response.data.user.username);
                            callback(true);
                        },
                        function () {
                            storeData('login', null);
                            callback(false);
                        }
                    );
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
                },
                logout: function () {
                    return $http({
                        method: 'POST',
                        url: 'api/logout'
                    })
                },
                storeData: function (role, username) {
                    storeData(role, username);
                }

            };
        });
}(angular));