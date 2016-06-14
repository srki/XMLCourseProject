/**
 * Created by ragnar on 14.6.16..
 */
(function (angular) {
    angular.module('app.SemanticSearchCtrl', [])
        .controller('SemanticSearchCtrl', ['$scope', // 'SemanticSearch'
            function ($scope) {
                $scope.items = [{
                    id: 0,
                    label: '',
                    subItem: { name: 'bSubItem' }
                },{
                    id: 1,
                    label: 'aLabel',
                    subItem: { name: 'aSubItem' }
                }, {
                    id: 2,
                    label: 'bLabel',
                    subItem: { name: 'bSubItem' }
                }, {
                    id: 2,
                    label: 'cLabel',
                    subItem: { name: 'cSubItem' }
                }];

                $scope.submit = function() {
                    console.log($scope.selected);
                    console.log($scope.searchText);
                }
            }]);
}(angular));