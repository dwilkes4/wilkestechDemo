(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DatesRentedController', DatesRentedController);

    DatesRentedController.$inject = ['$scope', '$state', 'DatesRented'];

    function DatesRentedController ($scope, $state, DatesRented) {
        var vm = this;
        
        vm.datesRenteds = [];

        loadAll();

        function loadAll() {
            DatesRented.query(function(result) {
                vm.datesRenteds = result;
            });
        }
    }
})();
