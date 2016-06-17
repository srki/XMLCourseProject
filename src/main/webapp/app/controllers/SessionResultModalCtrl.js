/**
 * Created by Aleksandar Lukić on 14.6.16..
 */


(function(angular) {
    'use strict';

    angular.module('app.SessionResultModalCtrl', [])

        .controller('SessionResultModalCtrl', ['$uibModalInstance', 'act', 'index',

            function($modalInstance, act, index) {
                var self = this;

                self.document = JSON.parse(JSON.stringify((index < 0) ? act : act.amendments[index]));
                self.documentOptions = [];
                self.errorMessage = '';

                var errMsgCommon = 'ERROR! ';

                function isInt(number) {
                    return parseInt(number) == number;
                }

                self.validate = function () {

                    if(!isInt(self.document.votedFor) || self.document.votedFor < 0){
                        self.errorMessage = errMsgCommon + 'voted for field accepts only positive integer!';
                        return false;
                    }


                    if(!isInt(self.document.votedAgainst) || self.document.votedAgainst < 0){
                        self.errorMessage = errMsgCommon + 'voted against field accepts only positive integer!';
                        return false;
                    }

                    if(!isInt(self.document.notVoted) || self.document.notVoted < 0) {
                        self.errorMessage = errMsgCommon + 'not voted field accepts only positive integer!';
                        return false;
                    }

                    self.errorMessage = '';
                    return true;
                };

                self.confirm = function () {

                    if(!self.validate())
                        return;

                    if(index < 0){
                        act = self.document;
                        act.dirty = true;
                    } else {
                        act.amendments[index] = self.document;
                        act.amendments[index].dirty = true;
                    }

                    console.log(act);
                    $modalInstance.close(act);
                };

                self.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

                self.keyFn = function (event) {
                    if (event.keyCode == 13)
                        self.confirm();
                };

                (function () {

                    if(index < 0){

                        switch (self.document.status){

                            case 'proposed':
                                self.documentOptions = ['canceled', 'denied',
                                    'approved_as_whole', 'approved_in_principle'];

                                break;

                            case 'denied':
                                self.documentOptions = ['proposed'];
                                break;

                            case 'canceled':
                                self.documentOptions = ['proposed'];
                                break;

                            case 'approved_in_principle':
                                self.documentOptions = ['approved_as_whole', 'denied'];
                                break;

                            case 'approved_as_whole':
                                self.documentOptions = ['retired'];
                                break;

                            default:
                                self.documentOptions = [];
                        }

                    }else{

                        switch (self.document.status){

                            case 'proposed':
                                self.documentOptions = ['canceled', 'denied', 'approved'];
                                break;

                            case 'denied':
                                self.documentOptions = ['proposed'];
                                break;

                            case 'canceled':
                                self.documentOptions = ['proposed'];
                                break;

                            default:
                                self.documentOptions = [];
                        }
                    }

                    self.document.status = (self.documentOptions.length > 0) ? self.documentOptions[0] : undefined;

                }());

            }
        ]);

}(angular));