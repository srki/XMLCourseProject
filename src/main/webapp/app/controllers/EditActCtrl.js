/**
 * Created by Srđan on 22.5.2016..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.EditActCtrl', [])
        .controller('EditActCtrl', function ($scope, $routeParams, $location, Auth, Acts) {
            Auth.isLogged(function (isLogged) {
                if (!isLogged) {
                    $location.path('/login');
                }
            });

            (function () {
                if ($routeParams.edit) {
                    Acts.getHtmlEdit($routeParams.id).then(
                        function (response) {
                            $scope.actHtml = response.data;
                        });
                } else {
                    Acts.getHtml($routeParams.id).then(
                        function (response) {
                            $scope.actHtml = response.data;
                        });
                }

            }());
        });
}(angular));