(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DayPriceDetailController', DayPriceDetailController);

    DayPriceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DayPrice', 'Property'];

    function DayPriceDetailController($scope, $rootScope, $stateParams, previousState, entity, DayPrice, Property) {
        var vm = this;

        vm.dayPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('wilkestechDemoApp:dayPriceUpdate', function(event, result) {
            vm.dayPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
