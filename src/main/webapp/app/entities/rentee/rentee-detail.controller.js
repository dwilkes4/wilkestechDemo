(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenteeDetailController', RenteeDetailController);

    RenteeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rentee', 'DatesRented'];

    function RenteeDetailController($scope, $rootScope, $stateParams, previousState, entity, Rentee, DatesRented) {
        var vm = this;

        vm.rentee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('wilkestechDemoApp:renteeUpdate', function(event, result) {
            vm.rentee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
