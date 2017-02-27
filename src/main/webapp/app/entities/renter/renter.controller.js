(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenterController', RenterController);

    RenterController.$inject = ['$scope', '$state', 'Renter'];

    function RenterController ($scope, $state, Renter) {
        var vm = this;
        
        vm.renters = [];

        loadAll();

        function loadAll() {
            Renter.query(function(result) {
                vm.renters = result;
            });
        }
    }
})();
