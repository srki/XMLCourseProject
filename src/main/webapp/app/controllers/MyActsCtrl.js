/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    angular.module('app.MyActsCtrl', [])
        .controller('MyActsCtrl', ['$scope', 'Acts', '$rootScope', '$uibModal', '$location', '$window',
            function ($scope, Acts, $rootScope, $uibModal, $location, $window) {
                $scope.data = [];

                $scope.init = function() {
                    $rootScope.loadCurrentStatus();
                    $scope.data = [];
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
                        controller: 'ConfirmDeleteCtrl'
                    });

                    modalInstance.result.then(function () {
                        console.log("Deleted " + uri);
                        Acts.remove(parseURI(uri)).then(function (response) {
                                $scope.init();
                            },
                            function (error) {
                                console.log(error);
                            });
                    }, function () {
                        console.log('Modal dismissed at: ' + new Date());
                    });
                };

                $scope.showHtml = function (uri) {
                    $location.path('/acts/' + parseURI(uri));
                };

                $scope.openPdf = function (uri) {
                    $window.open('/api/export/pdf/' + parseURI(uri));
                };

                $scope.formatStatus = function (status) {
                    return status.toUpperCase().split('_').join(' ');
                }

                $scope.init();
            }]);
}(angular));