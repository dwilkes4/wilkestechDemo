(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenteeDeleteController',RenteeDeleteController);

    RenteeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rentee'];

    function RenteeDeleteController($uibModalInstance, entity, Rentee) {
        var vm = this;

        vm.rentee = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Rentee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
