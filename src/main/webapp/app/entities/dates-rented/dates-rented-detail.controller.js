(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DatesRentedDetailController', DatesRentedDetailController);

    DatesRentedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DatesRented', 'Rentee', 'Property'];

    function DatesRentedDetailController($scope, $rootScope, $stateParams, previousState, entity, DatesRented, Rentee, Property) {
        var vm = this;

        vm.datesRented = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('wilkestechDemoApp:datesRentedUpdate', function(event, result) {
            vm.datesRented = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
