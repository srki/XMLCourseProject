/**
 * Created by Aleksandar Lukić on 19.5.16..
 */


(function (angular) {

    angular.module('app.AssemblyMeetingCtrl', [])

        .controller('AssemblyMeetingCtrl', ['$scope', 'AssemblyMeeting', '$location',
            function ($scope, AssemblyMeeting, $location) {

                $scope.meetings = [];
                $scope.inputData = {};
                $scope.state = 'idle';

                $scope.getUpcoming = function () {
                    AssemblyMeeting.getUpcoming().then(

                        function (response) {
                            processResponse(response);
                            $scope.title = 'Upcoming';
                        },

                        function (error) {
                            $scope.data = [];
                            console.log(error);
                        }
                    );
                };

                $scope.getFinished = function () {
                    AssemblyMeeting.getFinished().then(

                        function (response) {
                            processResponse(response);
                            $scope.title = 'Finished';
                        },

                        function (error) {
                            $scope.data = [];
                            console.log(error);
                        }
                    );
                };

                $scope.create = function () {

                    $scope.state = 'idle';
                    AssemblyMeeting.create($scope.inputData).then(

                        function (response) {
                            $scope.inputData = {};
                            $scope.getUpcoming();
                            console.log(response);
                        },

                        function (error) {
                            $scope.inputData = {};
                            console.log(error);
                        }
                    );
                };

                $scope.showForm = function () {
                    $scope.state = 'createNew';
                };

                $scope.cancel = function () {
                    $scope.state = 'idle';
                    $scope.inputData = {};
                };

                $scope.addResults = function () {
                    //$location.path('/sessionResults/');
                };

                function processResponse(response) {

                    if(response.data.sessions.session){

                        if(response.data.sessions.session.length) {
                            $scope.data = response.data.sessions.session;
                        }else {
                            $scope.data = [];
                            $scope.data.push(response.data.sessions.session);
                        }
                    }else{
                        $scope.data = [];
                    }

                    console.log(response);
                }

                $scope.getUpcoming();
            }
        ]);

}(angular));
