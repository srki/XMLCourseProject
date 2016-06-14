/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    angular.module('app.MyAmendmentsCtrl', [])
        .controller('MyAmendmentsCtrl', ['$scope', 'Amendments', '$rootScope', '$uibModal',
            function ($scope, Amendments, $rootScope, $uibModal) {
                $scope.data = [];

                $scope.init = function() {
                    Amendments.getAllFor($rootScope.username).then(
                        function (response) {
                            if (response.data.amendments){
                                if(response.data.amendments.amendment.length) {
                                    $scope.data = response.data.amendments.amendment;
                                } else {
                                    $scope.data = [];
                                    $scope.data.push(response.data.amendments.amendment);
                                }
                            } else {
                                $scope.data = [];
                            }
                            console.log(response);
                            console.log($scope.data);
                        },
                        function (error) {
                            console.log(error);
                        }
                    );
                };

                function parseURI(uri) {
                    var index = uri.lastIndexOf('/');
                    return uri.substring(index + 1, uri.length);
                }

                $scope.delete = function (uri) {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'partials/confirmDelete.html',
                        controller: 'ConfirmDeleteCtrl',
                    });

                    modalInstance.result.then(function () {
                        console.log("Deleted " + uri);
                        Amendments.remove(parseURI(uri)).then(function (response) {
                                console.log(response);
                                $scope.init();
                            },
                            function (error) {
                                console.log(error);
                            });
                    }, function () {
                        console.log('Modal dismissed at: ' + new Date());
                    });
                };

                $scope.init();
            }]);
}(angular));