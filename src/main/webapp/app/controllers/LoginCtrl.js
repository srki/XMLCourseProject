/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.LoginCtrl', [])
        .controller('LoginCtrl', function ($scope, $location, Auth, $rootScope) {
            var success = function (response) {
                    $rootScope.username = $scope.user.username;
                    console.log(response);
                    $location.path('/home');
                },
                error = function (response) {
                    console.log(response);
                    $scope.alertMessage = "Username and/or password are incorrect.";
                };

            $scope.login = function () {
                if (!$scope.user.username) {
                    $scope.alertMessage = 'Username cannot be empty.';
                } else if (!$scope.user.password) {
                    $scope.alertMessage = 'Password cannot be empty.';
                } else {
                    $scope.alertMessage = null;
                }

                if ($scope.alertMessage) {
                    return;
                }

                Auth.login($scope.user).then(success, error);
            };

            $scope.register = function () {
                $location.path('/register');
            };

            (function () {
                $scope.user = {};
                Auth.isLogged(function (isLogged) {
                    if (isLogged) {
                        $location.path('/home');
                    }
                });
            })();
        });
}(angular));