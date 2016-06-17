/**
 * Created by Aleksandar Lukić on 22.5.16..
 */


(function (angular) {
    "use strict";

    angular.module('app.EditArticleCtrl', [])

        .controller('EditArticleCtrl', [
            '$scope', '$routeParams', '$location', 'Auth', '$rootScope',

            function ($scope, $routeParams, $location, Auth, $rootScope) {
                Auth.isLogged(function (isLogged) {
                    if (!isLogged) {
                        $location.path('/login');
                    }
                });

                (function () {
                    $rootScope.loadCurrentStatus();

                    if($routeParams.new){

                        $scope.options = {
                            schemaUri:  '/xml/',
                            schemaName: 'amendment.xsd',
                            rootElement: 'amendment',
                            submitPath: '/api/amendments'
                        }
                    }else{
                        $scope.options = {
                            schemaUri: '/xml/',
                            schemaName: 'amendment.xsd',
                            rootElement: 'amendment',
                            submitPath: '/api/amendments',
                            retrievalPath: '/api/acts/' + $routeParams.actId + '/articles/'
                            + $routeParams.articleId + '/amendment'
                        };
                    }

                    if ($rootScope.currentStatus != 'amendment') {
                        $location.path('/home');
                    }
                }());
            }]);
}(angular));
