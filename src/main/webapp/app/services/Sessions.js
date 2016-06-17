/**
 * Created by Aleksandar Lukić on 19.5.16.
 */


(function (angular) {
    'use strict';

    angular.module('app.Sessions', [])

        .factory('Sessions', ['$http', function ($http) {

            return {


                getById: function (sessionId) {
                    return  $http({
                        method: 'GET',
                        url: 'api/sessions/' + sessionId
                    });
                },

                getFinished: function () {
                    return  $http({
                        method: 'GET',
                        url: 'api/sessions',
                        params: {
                            status: 'finished'
                        }
                    });
                },

                getUpcoming: function () {
                    return  $http({
                        method: 'GET',
                        url: 'api/sessions',
                        params: {
                            status: 'upcoming'
                        }
                    });
                },

                create: function (data) {

                    var startDate =  data.beginDate.toISOString();
                    var endDate =  data.endDate.toISOString();

                    return $http({
                        method: 'POST',
                        url: 'api/sessions',
                        data: {
                            session: {
                                _xmlns : "http://ftn.uns.ac.rs/xml",
                                '_xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance",
                                _beginDate : startDate,
                                _endDate : endDate,
                                place: data.place
                            }
                        }
                    });
                },

                update: function (sessionId, data) {

                    var session = {
                        _xmlns : "http://ftn.uns.ac.rs/xml",
                        '_xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance",
                        _beginDate : data.beginDate,
                        _endDate : data.endDate,
                        place: data.place
                    };

                    if(data.acts.length > 0) {
                        session.act = data.acts;
                    }

                    if(data.aldermen.length > 0){
                        session.alderman = data.aldermen;
                    }

                    return $http({
                        method: 'PUT',
                        url: 'api/sessions/' + sessionId,
                        data: {session : session}
                    });
                }
            };
        }]);

}(angular));
