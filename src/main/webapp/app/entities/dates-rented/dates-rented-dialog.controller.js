(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DatesRentedDialogController', DatesRentedDialogController);

    DatesRentedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DatesRented', 'Rentee', 'Property'];

    function DatesRentedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DatesRented, Rentee, Property) {
        var vm = this;

        vm.datesRented = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.rentees = Rentee.query();
        vm.properties = Property.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.datesRented.id !== null) {
                DatesRented.update(vm.datesRented, onSaveSuccess, onSaveError);
            } else {
                DatesRented.save(vm.datesRented, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('wilkestechDemoApp:datesRentedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
