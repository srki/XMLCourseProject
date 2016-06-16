/**
 * Created by Aleksandar Lukić on 14.6.16..
 */

(function() {
    'use strict';

    angular
        .module('app.sessionResultModal', [])
        .factory('SessionResultModal', ['$uibModal',  function($uibModal) {

            return {
                open:  function(act, index) {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'partials/sessionResultModal.html',
                        controller: 'SessionResultModalCtrl',
                        controllerAs: 'srmc',
                        size: 'md',
                        resolve: {
                            act: function () {
                                return act;
                            },
                            index: function () {
                                return index;
                            }
                        }
                    });

                    return modalInstance;
                }
            };
        }]);
})();