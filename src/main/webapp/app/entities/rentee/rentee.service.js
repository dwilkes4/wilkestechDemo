(function() {
    'use strict';
    angular
        .module('wilkestechDemoApp')
        .factory('Rentee', Rentee);

    Rentee.$inject = ['$resource'];

    function Rentee ($resource) {
        var resourceUrl =  'api/rentees/:id';

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
