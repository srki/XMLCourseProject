/**
 * Created by Aleksandar Lukić on 14.6.16..
 */

(function() {
    'use strict';

    angular
        .module('app.sessionResultModal', [])
        .factory('SessionResultModal', ['$uibModal',  function($uibModal) {

            return {
                open:  function(act) {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'partials/sessionResultModal.html',
                        controller: 'SessionResultModalCtrl',
                        controllerAs: 'srmc',
                        size: 'sm',
                        resolve: {
                            act: function () {
                                return act;
                            }
                        }
                    });

                    return modalInstance;
                }
            };
        }]);
})();