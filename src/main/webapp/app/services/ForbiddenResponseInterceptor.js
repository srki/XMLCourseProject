/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.ForbiddenResponseInterceptor', [])
        .factory('ForbiddenResponseInterceptor', function responseObserver($q, $location) {
            return {
                'responseError': function (errorResponse) {
                    if (errorResponse.status === 403) {
                        $location.path('/login');
                    }

                    return $q.reject(errorResponse);
                }
            };
        });
}(angular));