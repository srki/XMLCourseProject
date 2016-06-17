/**
 * Created by Srđan on 22.5.2016..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.EditActCtrl', [])
        .controller('EditActCtrl', function ($scope, $sce, $routeParams, $location, $anchorScroll, Auth, Acts) {
            Auth.isLogged(function (isLogged) {
                if (!isLogged) {
                    $location.path('/login');
                }
            });

            $scope.scroll = function (id) {
                if (id || id == 0) {
                    $location.hash(id);
                }
                $anchorScroll();
            };

            (function () {
                if ($routeParams.edit) {
                    $scope.url = 'api/export/html-edit/' + $routeParams.id;
                } else {
                    $scope.url = 'api/export/html/' + $routeParams.id;
                }
            }());
        });
}(angular));