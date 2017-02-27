(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenterDetailController', RenterDetailController);

    RenterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Renter', 'Property'];

    function RenterDetailController($scope, $rootScope, $stateParams, previousState, entity, Renter, Property) {
        var vm = this;

        vm.renter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('wilkestechDemoApp:renterUpdate', function(event, result) {
            vm.renter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
