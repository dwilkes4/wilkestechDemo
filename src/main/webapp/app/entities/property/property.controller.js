(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .controller('PropertyController', PropertyController);

    PropertyController.$inject = ['$scope', '$state', 'Property'];

    function PropertyController ($scope, $state, Property) {
        var vm = this;
        
        vm.properties = [];

        loadAll();

        function loadAll() {
            Property.query(function(result) {
                vm.properties = result;
            });
        }
    }
})();
