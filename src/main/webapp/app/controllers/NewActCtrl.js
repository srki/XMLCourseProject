/**
 * Created by Srđan on 20.5.2016..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.NewActCtrl', [])
        .controller('NewActCtrl', function ($scope, $location, Auth, $rootScope) {

            Auth.isLogged(function (isLogged) {
                if (!isLogged) {
                    $location.path('/login');
                }
            });

            (function () {
                $rootScope.loadCurrentStatus();

                $scope.options = {
                    schemaUri: '/api/schemas/',
                    schemaName: 'act.xsd',
                    rootElement: 'act',
                    submitPath: '/api/acts'
                }

                if ($rootScope.currentStatus != 'act') {
                    $location.path('/home');
                }
            }());
        });
}(angular));