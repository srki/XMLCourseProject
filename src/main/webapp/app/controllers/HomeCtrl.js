/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.HomeCtrl', [])
        .controller('HomeCtrl', function ($scope) {
            (function () {
                $scope.options = {
                    schemaUri: '/api/schemas/',
                    schemaName: 'act.xsd',
                    rootElement: 'act',
                    submitPath: '/api/propose'
                }
            })();
        });
}(angular));