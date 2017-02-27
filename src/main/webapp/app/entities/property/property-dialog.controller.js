(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('PropertyDialogController', PropertyDialogController);

    PropertyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Property', 'Renter', 'Address', 'DatesRented', 'DayPrice'];

    function PropertyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Property, Renter, Address, DatesRented, DayPrice) {
        var vm = this;

        vm.property = entity;
        vm.clear = clear;
        vm.save = save;
        vm.renters = Renter.query();
        vm.addresses = Address.query({filter: 'property-is-null'});
        $q.all([vm.property.$promise, vm.addresses.$promise]).then(function() {
            if (!vm.property.addressId) {
                return $q.reject();
            }
            return Address.get({id : vm.property.addressId}).$promise;
        }).then(function(address) {
            vm.addresses.push(address);
        });
        vm.datesrenteds = DatesRented.query();
        vm.dayprices = DayPrice.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.property.id !== null) {
                Property.update(vm.property, onSaveSuccess, onSaveError);
            } else {
                Property.save(vm.property, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('wilkestechDemoApp:propertyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
