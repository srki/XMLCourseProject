/**
 * Created by Aleksandar Lukić on 22.5.16..
 */


(function (angular) {
    'use strict';

    angular.module('app.Articles', [])

        .factory('Articles', ['$http', function ($http) {

            return {

                get: function (actId, articleId) {
                    return  $http({
                        method: 'GET',
                        url: 'api/acts/' + actId +'/' + articleId,
                        params: {format: 'amendment'}
                    });
                }
            };
        }]);

}(angular));