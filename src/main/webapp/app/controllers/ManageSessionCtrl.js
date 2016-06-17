/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    angular.module('app.ManageSessionCtrl', [])
        .controller('ManageSessionCtrl', ['$scope', 'SystemStatus', '$rootScope',
            function ($scope, SystemStatus, $rootScope) {
                $scope.status = '';
                $scope.max = 3;
                $scope.dynamic = 0;
                $scope.buttonText = ".";

                var states = ['act', 'amendment', 'voting', 'idle'];

                SystemStatus.getStatus().then(function(response) {
                    $scope.status = response.data.systemStatus.status;
                    $rootScope.currentStatus = $scope.status;
                    console.log("Current: " + $scope.status);
                    switch ($scope.status) {
                        case states[0]:
                            $scope.dynamic = 1;
                            $scope.buttonText = "Start proposing amendments";
                            break;
                        case states[1]:
                            $scope.dynamic = 2;
                            $scope.buttonText = "Start voting";
                            break;
                        case states[2]:
                            $scope.dynamic = 3;
                            $scope.buttonText = "Close session";
                            break;
                        case states[3]:
                            $scope.dynamic = 0;
                            $scope.buttonText = "Start proposing acts";
                            break;
                    }
                });

                $scope.next = function() {
                    switch ($scope.status) {
                        case states[0]:
                            $scope.status = states[1];
                            break;
                        case states[1]:
                            $scope.status = states[2];
                            break;
                        case states[2]:
                            $scope.status = states[3];
                            break;
                        case states[3]:
                            $scope.status = states[0];
                            break;
                        case '':
                            $scope.status = states[3];
                            break;
                    }

                    SystemStatus.updateStatus($scope.status).then(function() {
                        $rootScope.loadCurrentStatus();

                        if ($scope.dynamic < 3)
                            $scope.dynamic++;
                        else
                            $scope.dynamic = 0;

                        switch ($scope.dynamic) {
                            case 0:
                                $scope.buttonText = "Start proposing acts";
                                $rootScope.currentStatus = 'act';
                                break;
                            case 1:
                                $scope.buttonText = "Start proposing amendments";
                                break;
                                $rootScope.currentStatus = 'amendment';
                            case 2:
                                $scope.buttonText = "Start voting";
                                $rootScope.currentStatus = 'voting';
                                break;
                            case 3:
                                $scope.buttonText = "Close session";
                                $rootScope.currentStatus = 'idle';
                                break;
                        }

                    });
                };
            }]);

}(angular));