(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenterDeleteController',RenterDeleteController);

    RenterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Renter'];

    function RenterDeleteController($uibModalInstance, entity, Renter) {
        var vm = this;

        vm.renter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Renter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
