/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.RegisterCtrl', [])
        .controller('RegisterCtrl', function ($scope, $location, Auth) {
            $scope.register = function () {
                if (!$scope.user.name) {
                    $scope.alertMessage = "Name cannot be empty.";
                } else if (!$scope.user.lastname) {
                    $scope.alertMessage = "Last name cannot be empty."
                } else if (!$scope.user.username) {
                    $scope.alertMessage = 'Username cannot be empty.';
                } else if (!$scope.user.password) {
                    $scope.alertMessage = 'Password cannot be empty.';
                } else if ($scope.user.password !== $scope.renteredPassword) {
                    $scope.alertMessage = 'Passwords do not match.'
                } else {
                    $scope.alertMessage = null;
                }

                if ($scope.alertMessage) {
                    return;
                }

                Auth.register($scope.user).then(
                    function () {
                        $location.path('/login');
                    },
                    function (response) {
                        $scope.alertMessage = response.data.error.message;
                    }
                );
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