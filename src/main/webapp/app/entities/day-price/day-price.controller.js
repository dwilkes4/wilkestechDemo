(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DayPriceController', DayPriceController);

    DayPriceController.$inject = ['$scope', '$state', 'DayPrice'];

    function DayPriceController ($scope, $state, DayPrice) {
        var vm = this;
        
        vm.dayPrices = [];

        loadAll();

        function loadAll() {
            DayPrice.query(function(result) {
                vm.dayPrices = result;
            });
        }
    }
})();
