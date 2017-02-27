(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('PropertyDetailController', PropertyDetailController);

    PropertyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Property', 'Renter', 'Address', 'DatesRented', 'DayPrice'];

    function PropertyDetailController($scope, $rootScope, $stateParams, previousState, entity, Property, Renter, Address, DatesRented, DayPrice) {
        var vm = this;

        vm.property = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('wilkestechDemoApp:propertyUpdate', function(event, result) {
            vm.property = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
