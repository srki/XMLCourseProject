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

                            for (var i = 0; i < $scope.acts.length; i++){

                                $scope.acts[i] = {
                                    act: $scope.acts[i],
                                    amendments: [],
                                    dirty: false
                                }
                            }
                            console.log(response);
    
                        }, function (err) {
                            console.log(err);
                        });
                    
                    $scope.getAmendments = function (actModel) {

                        if(actModel.amendments.length > 0)
                            return;

                        Amendments.getByActURI(parseURI(actModel.act.uri)).then(

                            function (response) {

                                if(response.data.amendments.amendment){

                                    if(response.data.amendments.amendment.length) {
                                        actModel.amendments = response.data.amendments.amendment;
                                    }else {
                                        actModel.amendments = [];
                                        actModel.amendments.push(response.data.amendments.amendment);
                                    }
                                }else{
                                    actModel.amendments = [];
                                }

                                for (var i = 0; i < actModel.amendments.length; i++){

                                    actModel.amendments[i] = {
                                        amendment: actModel.amendments[i],
                                        dirty: false
                                    }
                                }
                            },
                            function (err) {
                                console.log(err);
                            }
                        );

                    };

                    $scope.dialog = function (document) {
                        SessionResultModal.open(document);
                    }

                }());

                function parseURI(uri) {
                    var index = uri.lastIndexOf('/');
                    return uri.substring(index + 1, uri.length);
                }
        }]);
}(angular));