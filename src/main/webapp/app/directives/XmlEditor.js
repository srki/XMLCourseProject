/**
 * Created by Srđan on 19.5.2016..
 */

/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.XmlEditor', [])
        .directive('xmlEditor', function () {
            return {
                restrict: 'E',
                template: '<iframe class="editor" ng-src="{{ createUrl() }}" width="100%">',
                scope: {
                    options: '=',
                    height: '@'
                },
                link: function (scope) {
                    scope.createUrl = function () {
                        var url = '/xml-editor/editor.html', first = true;
                        for (var key in scope.options) {
                            if (scope.options.hasOwnProperty(key)) {
                                url += (first ? '?' : '&') + key + '=' + scope.options[key];
                                first = false;
                            }
                        }
                        return url;
                    };
                }
            }
        });
}(angular));