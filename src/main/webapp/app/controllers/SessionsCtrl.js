/**
 * Created by Aleksandar Lukić on 19.5.16..
 */

/*global angular*/
(function (angular) {
    var hour = 60 * 60 * 1000,
        day = hour * 24;

    var parseUri = function (uri) {
        var index = uri.lastIndexOf('/');
        return uri.substring(index + 1, uri.length);
    };

    angular.module('app.SessionsCtrl', [])

        .controller('SessionsCtrl', ['$scope', 'Sessions', '$location','$rootScope',
            function ($scope, Sessions, $location, $rootScope) {

                $scope.sessions = [];
                $scope.activeTab = 0;
                $scope.errorMessage = '';
                $scope.inputData = {
                    beginDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day),
                    endDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day)
                };

                $scope.getUpcoming = function () {
                    Sessions.getUpcoming().then(
                        function (response) {
                            processResponse(response);
                            $scope.title = 'Upcoming';
                            $scope.activeTab = 0;
                        },

                        function (error) {
                            $scope.data = [];
                            $scope.activeTab = 0;
                            console.log(error);
                        }
                    );
                };

                $scope.getFinished = function () {
                    Sessions.getFinished().then(
                        function (response) {
                            processResponse(response);
                            $scope.title = 'Finished';
                            $scope.activeTab = 1;
                        },

                        function (error) {
                            $scope.data = [];
                            $scope.activeTab = 1;
                            console.log(error);
                        }
                    );
                };

                $scope.validate = function () {

                    if(!$scope.inputData.place){
                        $scope.errorMessage = 'ERROR! Place cannot be empty!';
                        return false;
                    }

                    var checkDate =  new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day).getTime();

                    if($scope.inputData.beginDate.getTime() < checkDate){
                        $scope.errorMessage = 'ERROR! Begin date must be at least a week after today!';
                        return false;
                    }

                    if($scope.inputData.beginDate.getTime() > $scope.inputData.endDate.getTime()){
                        $scope.errorMessage = 'ERROR! End date must be after begin date!';
                        return false;
                    }

                    $scope.errorMessage = '';
                    return true;
                };

                $scope.create = function () {

                    if(!$scope.validate())
                        return;

                    Sessions.create($scope.inputData).then(
                        function (response) {
                            $scope.inputData = {
                                beginDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day),
                                endDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day)
                            };
                            $scope.getUpcoming();
                            $scope.activeTab = 0;
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

                $scope.showDetails = function (uri) {
                    $location.path('/session-result-detail/' + parseUri(uri));
                };

                $scope.showForm = function () {
                    $scope.activeTab = 2;
                };

                $scope.cancel = function () {

                    $scope.inputData = {
                        beginDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day),
                        endDate: new Date(Math.floor(new Date().getTime() / hour) * hour + 7 * day)
                    };
                };

                $scope.addResults = function (uri) {
                    $location.path('/session-result/' + parseUri(uri));
                };

                function processResponse(response) {
                    if (response.data.sessions.session) {
                        if (response.data.sessions.session.length) {
                            $scope.data = response.data.sessions.session;
                        } else {
                            $scope.data = [];
                            $scope.data.push(response.data.sessions.session);
                        }
                    } else {
                        $scope.data = [];
                    }

                    console.log(response);
                }

                $scope.getUpcoming();
                $rootScope.loadCurrentStatus();
            }
        ]);

}(angular));
