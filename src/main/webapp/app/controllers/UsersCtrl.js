/**
 * Created by Srđan on 18.5.2016..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.UsersCtrl', [])
        .controller('UsersCtrl', function ($scope, Users) {

            (function () {
                $scope.users = [];
                Users.query().then(
                    function (response) {
                        $scope.users = response.data.users.user;
                    },
                    function (response) {
                        console.log(response);
                    }
                );
            }());

        });
}(angular));