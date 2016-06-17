/**
 * Created by ragnar on 17.6.16..
 */
(function (angular) {
    "use strict";

    angular.module('app.ShowAmendmentCtrl', [])
        .controller('ShowAmendmentCtrl', function ($scope, $sce, $routeParams, $location, Auth, Amendments) {
            Auth.isLogged(function (isLogged) {
                if (!isLogged) {
                    $location.path('/login');
                }
            });

            (function () {

                Amendments.getHtml($routeParams.id).then(
                    function (response) {
                        $scope.amendmentHtml = $sce.trustAsHtml(response.data);
                    });

            }());
        });
}(angular));