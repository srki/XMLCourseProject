/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    'use strict';

    angular.module('app.SystemStatus', [])

        .factory('SystemStatus', ['$http', function ($http) {

            return {

                getStatus: function () {
                    return  $http({
                        method: 'GET',
                        url: 'api/systemStatus'
                    });
                },


                updateStatus: function (status) {
                    return $http({
                        method: 'PUT',
                        url: 'api/systemStatus',
                        data: status
                    });
                }
            };
        }]);

}(angular));