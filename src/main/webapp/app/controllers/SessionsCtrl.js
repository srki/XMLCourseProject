/**
 * Created by Aleksandar Lukić on 19.5.16..
 */

/*global angular*/
(function (angular) {
    var hour = 60 * 60 * 1000,
        day = hour * 24;

    angular.module('app.SessionsCtrl', [])

        .controller('SessionsCtrl', ['$scope', 'Sessions', '$location',
            function ($scope, Sessions) {

                $scope.sessions = [];
                $scope.inputData = {
                    beginDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day),
                    endDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day)
                };
                $scope.state = 'idle';

                $scope.getUpcoming = function () {
                    Sessions.getUpcoming().then(

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
                    Sessions.getFinished().then(

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
                    Sessions.create($scope.inputData).then(

                        function (response) {
                            $scope.inputData = {
                                beginDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day),
                                endDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day)
                            };
                            $scope.getUpcoming();
                            console.log(response);
                        },

                        function (error) {
                            $scope.inputData = {
                                beginDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day),
                                endDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day)
                            };
                            console.log(error);
                        }
                    );
                };

                $scope.showForm = function () {
                    $scope.state = 'createNew';
                };

                $scope.cancel = function () {
                    $scope.state = 'idle';
                    $scope.inputData = {
                        beginDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day),
                        endDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day)
                    };
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
