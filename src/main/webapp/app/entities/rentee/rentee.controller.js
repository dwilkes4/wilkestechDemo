(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenteeController', RenteeController);

    RenteeController.$inject = ['$scope', '$state', 'Rentee'];

    function RenteeController ($scope, $state, Rentee) {
        var vm = this;
        
        vm.rentees = [];

        loadAll();

        function loadAll() {
            Rentee.query(function(result) {
                vm.rentees = result;
            });
        }
    }
})();
