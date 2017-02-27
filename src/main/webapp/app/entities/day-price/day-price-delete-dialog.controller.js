(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DayPriceDeleteController',DayPriceDeleteController);

    DayPriceDeleteController.$inject = ['$uibModalInstance', 'entity', 'DayPrice'];

    function DayPriceDeleteController($uibModalInstance, entity, DayPrice) {
        var vm = this;

        vm.dayPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DayPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
