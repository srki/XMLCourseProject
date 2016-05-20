/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.LogoutCtrl', [])
        .controller('LogoutCtrl', function ($location, Auth) {
            (function () {
                Auth.logout().then(
                    function () {
                        $location.path('/login');
                    },
                    function () {
                        $location.path('/login');
                    }
                )
            })();
        });
}(angular));