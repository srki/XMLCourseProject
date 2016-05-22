/**
 * Created by Aleksandar Lukić on 19.5.16.
 */


(function (angular) {
    'use strict';

    angular.module('app.AssemblyMeeting', [])

        .factory('AssemblyMeeting', ['$http', function ($http) {

            return {

                getFinished: function () {
                    return  $http({
                        method: 'GET',
                        url: 'api/assemblyMeetings',
                        params: {
                            status: 'finished'
                        }
                    });
                },
                
                getUpcoming: function () {
                    return  $http({
                        method: 'GET',
                        url: 'api/assemblyMeetings',
                        params: {
                            status: 'upcoming'
                        }
                    });
                },
                
                create: function (data) {
                    return $http({
                        method: 'POST',
                        url: 'api/assemblyMeetings',
                        data: {assemblyMeeting: data}
                    });
                }
            };
        }]);

}(angular));
