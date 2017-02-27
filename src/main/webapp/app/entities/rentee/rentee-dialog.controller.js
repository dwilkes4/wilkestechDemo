(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenteeDialogController', RenteeDialogController);

    RenteeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rentee', 'DatesRented'];

    function RenteeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rentee, DatesRented) {
        var vm = this;

        vm.rentee = entity;
        vm.clear = clear;
        vm.save = save;
        vm.datesrenteds = DatesRented.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rentee.id !== null) {
                Rentee.update(vm.rentee, onSaveSuccess, onSaveError);
            } else {
                Rentee.save(vm.rentee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('wilkestechDemoApp:renteeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
