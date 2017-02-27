(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DatesRentedDeleteController',DatesRentedDeleteController);

    DatesRentedDeleteController.$inject = ['$uibModalInstance', 'entity', 'DatesRented'];

    function DatesRentedDeleteController($uibModalInstance, entity, DatesRented) {
        var vm = this;

        vm.datesRented = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DatesRented.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
