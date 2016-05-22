/**
 * Created by Srđan on 22.5.2016..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.EditActCtrl', [])
        .controller('EditActCtrl', function ($scope, $routeParams, $location, Auth) {
            Auth.isLogged(function (isLogged) {
                if (!isLogged) {
                    $location.path('/login');
                }
            });

            (function () {
                $scope.options = {
                    schemaUri: '/api/schemas/',
                    schemaName: 'act.xsd',
                    rootElement: 'act',
                    submitPath: '/api/acts',
                    retrievalPath: '/api/acts/' + $routeParams.id
                };
            }());
        });
}(angular));