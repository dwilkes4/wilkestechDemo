(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('DayPriceDialogController', DayPriceDialogController);

    DayPriceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DayPrice', 'Property'];

    function DayPriceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DayPrice, Property) {
        var vm = this;

        vm.dayPrice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.dayPrice.id !== null) {
                DayPrice.update(vm.dayPrice, onSaveSuccess, onSaveError);
            } else {
                DayPrice.save(vm.dayPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('wilkestechDemoApp:dayPriceUpdate', result);
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
