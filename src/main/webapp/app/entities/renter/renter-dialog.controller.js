(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('RenterDialogController', RenterDialogController);

    RenterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Renter', 'Property'];

    function RenterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Renter, Property) {
        var vm = this;

        vm.renter = entity;
        vm.clear = clear;
        vm.save = save;
        vm.properties = Property.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.renter.id !== null) {
                Renter.update(vm.renter, onSaveSuccess, onSaveError);
            } else {
                Renter.save(vm.renter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('wilkestechDemoApp:renterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
