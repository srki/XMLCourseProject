/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    angular.module('app.MyAmendmentsCtrl', [])
        .controller('MyAmendmentsCtrl', ['$scope', 'Amendments', '$rootScope', '$uibModal', '$location', '$window',
            function ($scope, Amendments, $rootScope, $uibModal, $location, $window) {
                $scope.data = [];

                $scope.init = function() {
                    $rootScope.loadCurrentStatus();
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
                    $location.path('/amendments/' + parseURI(uri));
                };

                $scope.openPdf = function (uri) {
                    $window.open('api/export/amendment/pdf/' + parseURI(uri));
                };

                $scope.showActHtml = function (uri) {
                    $location.path('/acts/' + parseURI(uri));
                };

                $scope.openActPdf = function (uri) {
                    $window.open('/api/export/pdf/' + parseURI(uri));
                };

                $scope.init();
            }]);
}(angular));