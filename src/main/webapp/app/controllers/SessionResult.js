/**
 * Created by Srđan on 28.5.2016..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.SessionResultCtrl', [])
        .controller('SessionResultCtrl', ['$scope', 'Acts', 'Amendments', 'SessionResultModal',
            'Users', 'Sessions', '$rootScope', '$routeParams',
            function ($scope, Acts, Amendments, SessionResultModal,
                      Users, Sessions, $rootScope, $routeParams) {

                $scope.acts = [];
                $scope.dirty = [];
                $scope.attended = [];
                $scope.aldermen = [];


                console.log($scope.currentStatus);

                $scope.get = function () {

                    var filter = JSON.parse(JSON.stringify($scope.filter));

                    if (filter.start_date)
                        filter.start_date = $scope.filter.start_date.getTime();

                    if (filter.end_date)
                        filter.end_date = $scope.filter.end_date.getTime();

                    Acts.get(filter).then(

                        function (response) {

                            parseResponse(response, 'acts', 'act', $scope, 'acts');

                            for (var i = 0; i < $scope.acts.length; i++){

                                $scope.acts[i].amendments = [];
                                $scope.acts[i].dirty = false;
                                $scope.acts[i].votedFor = 0;
                                $scope.acts[i].votedAgainst = 0;
                                $scope.acts[i].notVoted = 0;
                            }
                        },

                        function (error) {
                            console.log(error);
                        }
                    );
                };

                $scope.clear = function () {
                    $scope.filter = {};
                };

                $scope.getAmendments = function (act) {

                    if(act.amendments.length > 0)
                        return;

                    Amendments.getByActURI(parseURI(act.uri)).then(

                        function (response) {

                            parseResponse(response, 'amendments', 'amendment', act, 'amendments');

                            for (var i = 0; i < act.amendments.length; i++){

                                act.amendments[i].dirty = false;
                                act.amendments[i].votedFor = 0;
                                act.amendments[i].votedAgainst = 0;
                                act.amendments[i].notVoted = 0;
                            }
                        },
                        function (err) {
                            console.log(err);
                        }
                    );

                };

                $scope.getAldermen = function () {

                    if($scope.aldermen.length === 0) {
                        Users.query({type: 'representative'}).then(

                            function (response) {
                                parseResponse(response, 'users', 'user', $scope, 'aldermen');

                                for (var i = 0; i < $scope.aldermen.length; i++)
                                    $scope.aldermen[i].attended = false;

                                $scope.state = 'aldermanState';
                            },

                            function (err) {
                                console.log(err);
                            }
                        );
                    }else {
                        $scope.state = 'aldermanState';
                    }

                };

                $scope.dialog = function (act, index) {
                    SessionResultModal.open(act, index).result.then(
                        
                        function (act) {

                            for(var i = 0; i < $scope.dirty.length; i++){

                                if(act.uri === $scope.dirty[i].uri){

                                    if(act.dirty){
                                        $scope.dirty[i].status = act.status;
                                        $scope.dirty[i].votedFor = act.votedFor;
                                        $scope.dirty[i].votedAgainst = act.votedAgainst;
                                        $scope.dirty[i].notVoted = act.notVoted;
                                    }

                                    if($scope.dirty[i].amendments.length === 0){

                                        $scope.dirty[i].amendments = act.amendments;

                                    }else{

                                        for(var j = 0; j < $scope.dirty[i].amendments.length; j++){

                                            if($scope.dirty[i].amendments.uri === act.amendments[j].uri){

                                                if(act.amendments[j].dirty) {
                                                    $scope.dirty[i].amendments[j] = act.amendments[j];
                                                }
                                            }
                                        }
                                    }

                                    return;
                                }
                            }

                            $scope.dirty.push(act);
                        },
                        
                        function (err) {
                            console.log(err);
                        }
                        
                    );
                    
                };

                $scope.finish = function () {

                    var preparedData = {
                        acts: [],
                        aldermen: [],
                        beginDate: $scope.session._beginDate,
                        endDate: $scope.session._endDate,
                        place: $scope.session.place
                    };

                    for(var i = 0; i < $scope.aldermen.length; i++)
                        preparedData.aldermen.push({_username: $scope.aldermen[i].username});


                    for(var i = 0; i < $scope.dirty.length; i++) {

                        var act = {
                            _status: $scope.dirty[i].status,
                            _ref: parseURI($scope.dirty[i].uri),
                            amendment: []
                        };

                        for(var j = 0; j < $scope.dirty[i].amendments.length; j++){

                            if($scope.dirty[i].amendments[j].dirty){

                                act.amendment.push({
                                    _status: $scope.dirty[i].amendments[j].status,
                                    _ref: parseURI( $scope.dirty[i].amendments[j].uri)
                                });
                            }
                        }

                        preparedData.acts.push(act);
                    }

                    console.log(preparedData);

                    Sessions.update(parseURI($scope.session.uri), preparedData).then(

                        function (response) {
                            console.log(response);
                        },

                        function (err) {
                            console.log(err);
                        }
                    );
                };


                function parseURI(uri) {
                    var index = uri.lastIndexOf('/');
                    return uri.substring(index + 1, uri.length);
                }

                function parseResponse(response, collection, item, obj, prop) {

                    if(response.data[collection][item]){

                        if(response.data[collection][item].length) {
                            obj[prop] = response.data[collection][item];
                        }else {
                            obj[prop] = [];
                            obj[prop].push(response.data[collection][item]);
                        }
                    }else{
                        obj[prop] = [];
                    }

                    console.log(response);
                }


                (function () {

                    Sessions.getById($routeParams.id).then(

                        function (response) {
                            $scope.session = response.data.session;
                            $scope.session.uri = $routeParams.id;
                        },

                        function (err) {
                            console.log(err);
                        }
                    );

                    $rootScope.loadCurrentStatus();
                    $scope.clear();
                }());


        }]);
}(angular));