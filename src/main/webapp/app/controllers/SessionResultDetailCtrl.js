/**
 * Created by Aleksandar Lukić on 17.6.16..
 */

(function (angular) {
    "use strict";

    angular.module('app.SessionResultDetailCtrl', [])
        .controller('SessionResultDetailCtrl', ['$scope', 'Sessions', '$routeParams', 'Users',
            function ($scope, Sessions, $routeParams, Users) {

                $scope.acts = [];
                $scope.aldermen = [];

                function parseURI(uri) {
                    var index = uri.lastIndexOf('/');
                    return uri.substring(index + 1, uri.length);
                }

                function parseResponse(response, collection, item, obj, prop, append) {

                    if(response.data[collection][item]){

                        if(response.data[collection][item].length) {

                            if(!append)
                                obj[prop] = response.data[collection][item];
                            else
                                obj[prop].concat(response.data[collection][item]);
                        }else {

                            if(!append)
                                obj[prop] = [];

                            obj[prop].push(response.data[collection][item]);
                        }
                    }else{

                        if(!append)
                            obj[prop] = [];
                    }

                    console.log(response);
                }

                function parseSessionData(session, collection, obj, prop){

                    if(session[collection]){

                        if(session[collection].length) {

                            obj[prop] = session[collection];

                        }else {

                            obj[prop] = [];
                            obj[prop].push(session[collection]);
                        }
                    }else{
                        obj[prop] = [];
                    }
                }

                (function () {

                    Sessions.getById($routeParams.id).then(

                        function (response) {

                            console.log(response);
                            $scope.session = response.data.session;
                            $scope.session.uri = $routeParams.id;

                            parseSessionData($scope.session, 'act', $scope, 'acts');
                            parseSessionData($scope.session, 'alderman', $scope.session, 'alderman');

                            for(var i = 0; i < $scope.acts.length; i++){
                                parseSessionData($scope.acts[i], 'amendment', $scope.acts[i], 'amendments');
                            }
                            
                            delete $scope.acts.amendment;

                            Users.query({type: 'representative'}).then(

                                function (response) {

                                    parseResponse(response, 'users', 'user', $scope, 'aldermen');

                                    Users.query({type: 'president'}).then(

                                        function (response) {
                                            parseResponse(response, 'users', 'user', $scope, 'aldermen', true);

                                            var found = false;
                                            for (var i = $scope.aldermen.length - 1; i >= 0; i--){

                                                for(var j = 0; j < $scope.session.alderman.length; j++){

                                                    if($scope.aldermen[i].username === $scope.session.alderman[j]._username){
                                                        found = true;
                                                        break;
                                                    }
                                                }

                                                if(!found)
                                                    $scope.aldermen.splice(i, 1);
                                            }

                                            delete $scope.session.alderman;
                                        },

                                        function (err) {
                                            console.log(err);
                                        }
                                    );
                                },

                                function (err) {
                                    console.log(err);
                                }
                            );
                        },

                        function (err) {
                            console.log(err);
                        }
                    );
                }());
            }]);
}(angular));