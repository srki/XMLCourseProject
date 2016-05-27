/**
 * Created by Aleksandar Lukić on 21.5.16..
 */


(function (angular) {

    angular.module('app.ActFilterCtrl', [])

        .controller('ActFilterCtrl', ['$scope', 'Acts', '$location', '$routeParams',

            function ($scope, Acts, $location, $routeParams) {

                $scope.data = [];
                $scope.state = 'idle';

                $scope.get = function () {

                    $location.search('query', 'true');

                    var filter = JSON.parse(JSON.stringify($scope.filter));

                    if (filter.start_date)
                        filter.start_date = $scope.filter.start_date.getTime();

                    if (filter.end_date)
                        filter.end_date = $scope.filter.end_date.getTime();

                    Acts.get(filter).then(

                        function (response) {

                            if(response.data.acts.act){

                                if(response.data.acts.act.length) {
                                    $scope.data = response.data.acts.act;
                                }else {
                                    $scope.data = [];
                                    $scope.data.push(response.data.acts.act);
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

                    $scope.filter = {};
                    $routeParams.filter = {};
                };

                $scope.update = function (index) {
                    $location.path('/acts/' + parseURI($scope.data[index].uri));
                    $location.search('edit', 'true');
                };

                $scope.showHtml = function (index) {
                    $location.path('/acts/' + parseURI($scope.data[index].uri));
                };

                function parseURI(uri) {
                    var index = uri.lastIndexOf('/');
                    return uri.substring(index + 1, uri.length);
                }

                $scope.clear();

                if($routeParams.query) {
                    $scope.get();
                }
            }
        ]);


}(angular));


