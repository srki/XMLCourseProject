/**
 * Created by Aleksandar Lukić on 21.5.16..
 */


(function (angular) {

    angular.module('app.ActFilterCtrl', [])

        .controller('ActFilterCtrl', ['$scope', 'Acts',

            function ($scope, Acts) {

                $scope.data = [];

                $scope.get = function () {

                    Acts.get($scope.filter).then(

                        function (response) {
                            $scope.data = response.data.acts || [];
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

                $scope.clear();

            }
        ]);

}(angular));



