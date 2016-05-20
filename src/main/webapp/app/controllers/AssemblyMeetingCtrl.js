/**
 * Created by Aleksandar Lukić on 19.5.16..
 */


(function (angular) {

    angular.module('app.AssemblyMeetingCtrl', [])

        .controller('AssemblyMeetingCtrl', ['$scope', 'AssemblyMeeting',
            function ($scope, AssemblyMeeting) {

                $scope.meetings = [{id: 1, startDate: Date.now(), endDate: Date.now()}];
                $scope.inputData = {};

                $scope.getUpcoming = function () {
                    AssemblyMeeting.getUpcoming().then(
                        function (response) {
                          console.log(response);
                        },

                        function (error) {
                            console.log(error);
                        }
                    );
                };

                $scope.getFinished = function () {
                    AssemblyMeeting.getFinished().then(
                        function (response) {
                            console.log(response);
                        },

                        function (error) {
                            console.log(error);
                        }
                    );
                };

                $scope.create = function () {
                    
                }
            }
        ]);

}(angular));
