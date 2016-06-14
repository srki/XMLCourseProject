/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    angular.module('app.MyActsCtrl', [])
        .controller('MyActsCtrl', ['$scope', 'Acts', '$rootScope', '$uibModal',
            function ($scope, Acts, $rootScope, $uibModal) {
                $scope.data = [];

                $scope.init = function() {
                    Acts.get({username: $rootScope.username}).then(
                        function (response) {
                            if (response.data.acts.act){
                                if(response.data.acts.act.length) {
                                    $scope.data = response.data.acts.act;
                                }else {
                                    $scope.data = [];
                                    $scope.data.push(response.data.acts.act);
                                }
                            } else {
                                $scope.data = [];
                            }
                            console.log(response);
                        },
                        function (error) {
                            console.log(error);
                        }
                    );
                };

                $scope.delete = function (uri) {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'partials/confirmDelete.html',
                        controller: 'ConfirmDeleteCtrl',
                    });

                    modalInstance.result.then(function () {
                        console.log("Deleted " + uri);
                    }, function () {
                        console.log('Modal dismissed at: ' + new Date());
                    });
                };

                $scope.init();
            }]);
}(angular));