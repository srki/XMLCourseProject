/**
 * Created by Aleksandar Lukić on 14.6.16..
 */


(function(angular) {
    'use strict';

    angular.module('app.SessionResultModalCtrl', [])

        .controller('SessionResultModalCtrl', ['$uibModalInstance', 'document',

            function($modalInstance, document) {
                var self = this;

                self.document = document;
                self.for;
                self.against;
                self.restrained;

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