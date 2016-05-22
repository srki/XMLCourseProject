/**
 * Created by Aleksandar Lukić on 21.5.16..
 */


(function (angular) {

    angular.module('app.ActFilterCtrl', [])

        .controller('ActFilterCtrl', ['$scope', 'Acts', '$location',

            function ($scope, Acts, $location) {

                $scope.data = [];
                $scope.state = 'idle';

                $scope.get = function () {

                    var filter = JSON.parse(JSON.stringify($scope.filter));

                    if(filter.start_date)
                        filter.start_date = $scope.filter.start_date.getTime();

                    if(filter.end_date)
                        filter.end_date = $scope.filter.end_date.getTime();

                    Acts.get(filter).then(

                        function (response) {
                            $scope.data = response.data.acts.act || [];
                            $scope.state = ($scope.data.length > 0)? 'showResults':'noResults';
                            console.log(response);
                        },

                        function (error) {
                            console.log(error);
                        }
                    );
                };

                $scope.clear = function () {

                    $scope.filter = {};
                };

                $scope.update = function (index) {
                    $location.path('/acts/' + $scope.data[index].uri);
                };

                $scope.clear();
            }
        ]);

}(angular));



