/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
(function (angular) {
    "use strict";
    angular.module('app', ['app.controllers', 'app.services', 'app.directives', 'app.constants',
        'ngRoute', 'ngAnimate', 'ngSanitize', 'ui.bootstrap'])
        .config(function ($routeProvider, $locationProvider, $httpProvider) {
            $routeProvider
                .when('/', {
                    redirectTo: '/login'
                })
                .when('/login', {
                    templateUrl: 'partials/login.html',
                    controller: 'LoginCtrl'
                })
                .when('/register', {
                    templateUrl: 'partials/register.html',
                    controller: 'RegisterCtrl'
                })
                .when('/logout', {
                    template: '',
                    controller: 'LogoutCtrl'
                })
                .when('/home', {
                    templateUrl: 'partials/home.html',
                    controller: 'HomeCtrl'
                })
                .when('/users', {
                    templateUrl: 'partials/users.html',
                    controller: 'UsersCtrl'
                })
                .when('/sessions', {
                    templateUrl: 'partials/sessions.html',
                    controller: 'SessionsCtrl'
                })
                .when('/session-result/:id',{
                    templateUrl: 'partials/sessionResult.html',
                    controller: 'SessionResultCtrl'
                })

                .when('/session-result-detail/:id',{
                    templateUrl: 'partials/sessionResultDetails.html',
                    controller: 'SessionResultDetailCtrl'
                })
                .when('/newAct', {
                    templateUrl: 'partials/newAct.html',
                    controller: 'NewActCtrl'
                })
                .when('/acts',{
                    templateUrl: 'partials/actFilter.html',
                    controller: 'ActFilterCtrl'
                })
                .when('/acts/:id', {
                    templateUrl: 'partials/showAct.html',
                    controller: 'EditActCtrl'
                })
                .when('/amendments',{
                    templateUrl: 'partials/amendmentFilter.html',
                    controller: 'AmendmentFilterCtrl'
                })
                .when('/amendments/:id', {
                    templateUrl: 'partials/showAmendment.html',
                    controller: 'ShowAmendmentCtrl'
                })
                .when('/acts/:actId/:articleId', {
                    templateUrl: 'partials/editAct.html',
                    controller: 'EditArticleCtrl'
                })
                .when('/manageSession',{
                    templateUrl: 'partials/manageSession.html',
                    controller: 'ManageSessionCtrl'
                })
                .when('/semanticSearch',{
                    templateUrl: 'partials/semanticSearch.html',
                    controller: 'SemanticSearchCtrl'
                })
                .when('/myActs',{
                    templateUrl: 'partials/myActs.html',
                    controller: 'MyActsCtrl'
                })
                .when('/myAmendments',{
                    templateUrl: 'partials/myAmendments.html',
                    controller: 'MyAmendmentsCtrl'
                })
                .otherwise('/');

            $locationProvider.html5Mode(true);

            $httpProvider.interceptors.push('ForbiddenResponseInterceptor');
            $httpProvider.interceptors.push('ContentTypeInterceptor');
        })
        .run(function (Auth, $location, $rootScope, SystemStatus) {
            Auth.isLogged(function (isLogged) {
                if (!isLogged) {
                    $location.path('/login');
                }
            });

            $rootScope.currentStatus = '*';
            $rootScope.loadCurrentStatus = function () {
                SystemStatus.getStatus().then(function(response) {
                    $rootScope.currentStatus = response.data.systemStatus.status;
                });
            };

            window.addEventListener("message", function (event) {
                if (event.data == 'act') {
                    $location.path('/myActs');
                } else if (event.data == 'amendment') {
                    $location.path('/myAmendments');
                } else {
                    $location.path('/');
                }
                $rootScope.$apply();
            }, false);
        });
}(angular));