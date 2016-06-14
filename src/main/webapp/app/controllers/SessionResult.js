/**
 * Created by Srđan on 28.5.2016..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.SessionResultCtrl', [])
        .controller('SessionResultCtrl', ['$scope', 'Acts', 'Amendments', 'SessionResultModal',
            function ($scope, Acts, Amendments, SessionResultModal) {

                $scope.acts = [];
                $scope.amendments = [];

                (function () {

                    Acts.get({}).then(
    
                        function (response) {
    
                            if(response.data.acts.act){
    
                                if(response.data.acts.act.length) {
                                    $scope.acts = response.data.acts.act;
                                }else {
                                    $scope.acts = [];
                                    $scope.acts.push(response.data.acts.act);
                                }
                            }else{
                                $scope.acts = [];
                            }

                            for (var i = 0; i < acts.length; i++){

                                acts[i] = {
                                    act: acts[i],
                                    amendments: [],
                                    dirty: false
                                }
                            }
                            console.log(response);
    
                        }, function (err) {
                            console.log(err);
                        });
                    
                    $scope.getAmendments = function (act) {

                        if(act.amendments.length > 0)
                            return;

                        Amendments.getByActURI(parseURI(act.uri)).then(

                            function (response) {
                                console.log(response);
                            },
                            function (err) {
                                console.log(err);
                            }
                        );

                    };

                    $scope.dialog = function (act) {
                        SessionResultModal.open(act);
                    }

                }());

                function parseURI(uri) {
                    var index = uri.lastIndexOf('/');
                    return uri.substring(index + 1, uri.length);
                }
        }]);
}(angular));