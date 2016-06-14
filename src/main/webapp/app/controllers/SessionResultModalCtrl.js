/**
 * Created by Aleksandar Lukić on 14.6.16..
 */


(function(angular) {
    'use strict';

    angular.module('app.SessionResultModalCtrl', [])

        .controller('SessionResultModalCtrl', ['$uibModalInstance', 'act',

            function($modalInstance, act) {
                var self = this;

                self.act = act;
                self.for = 0;
                self.against = 0;

                self.confirm = function () {
                    return;
                    $modalInstance.close();
                };

                self.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

                self.keyFn = function (event) {
                    if (event.keyCode == 13)
                        self.confirm();
                };
            }
        ]);

}(angular));