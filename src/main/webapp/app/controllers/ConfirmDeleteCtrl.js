/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    angular.module('app.ConfirmDeleteCtrl', [])
        .controller('ConfirmDeleteCtrl', ['$scope', '$uibModalInstance',
            function ($scope, $uibModalInstance) {
                $scope.ok = function () {
                    $uibModalInstance.close();
                };

                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }]);
}(angular));