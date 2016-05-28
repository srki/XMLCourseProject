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
                .when('/acts/:actId/:articleId', {
                    templateUrl: 'partials/editAct.html',
                    controller: 'EditArticleCtrl'
                })
                .otherwise('/');

            $locationProvider.html5Mode(true);

            $httpProvider.interceptors.push('ForbiddenResponseInterceptor');
            $httpProvider.interceptors.push('ContentTypeInterceptor');
        })
        .run(function (Auth, $location) {
            Auth.isLogged(function (isLogged) {
                if (!isLogged) {
                    $location.path('/login');
                }
            });
        });
}(angular));