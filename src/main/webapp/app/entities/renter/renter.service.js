(function() {
    'use strict';
    angular
        .module('wilkestechDemoApp')
        .factory('Renter', Renter);

    Renter.$inject = ['$resource'];

    function Renter ($resource) {
        var resourceUrl =  'api/renters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
