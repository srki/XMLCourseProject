/**
 * Created by ragnar on 17.6.16..
 */
(function (angular) {

    angular.module('app.AmendmentFilterCtrl', [])

        .controller('AmendmentFilterCtrl', ['$scope', 'Amendments', '$location', '$routeParams', '$window', '$rootScope',

            function ($scope, amendments, $location, $routeParams, $window, $rootScope) {

                $scope.data = [];
                $scope.state = 'idle';
                $scope.documentOptions = ['', 'proposed', 'canceled', 'denied', 'approved'];


                $scope.get = function () {

                    $location.search('query', 'true');

                    amendments.get($scope.status ? {status: $scope.status} : null).then(

                        function (response) {

                            if(response.data.amendments.amendment){

                                if(response.data.amendments.amendment.length) {
                                    $scope.data = response.data.amendments.amendment;
                                }else {
                                    $scope.data = [];
                                    $scope.data.push(response.data.amendments.amendment);
                                }
                            }else{
                                $scope.data = [];
                            }

                            $scope.state = ($scope.data.length > 0) ? 'showResults' : 'noResults';
                            console.log(response);
                        },

                        function (error) {
                            console.log(error);
                        }
                    );
                };

                $scope.clear = function () {

                    $scope.status = '';
                    $routeParams.filter = {};
                };

                $scope.update = function (uri) {
                    $location.path('/amendments/' + parseURI(uri));
                    $location.search('edit', 'true');
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

                $scope.formatStatus = function (status) {
                    return status.toUpperCase().split('_').join(' ');
                }

                function parseURI(uri) {
                    var index = uri.lastIndexOf('/');
                    return uri.substring(index + 1, uri.length);
                }

                $scope.clear();
                $rootScope.loadCurrentStatus();

                if($routeParams.query) {
                    $scope.get();
                }
            }
        ]);


}(angular));


