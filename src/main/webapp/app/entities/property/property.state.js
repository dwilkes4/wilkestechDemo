(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('property', {
            parent: 'entity',
            url: '/property',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Properties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/property/properties.html',
                    controller: 'PropertyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('property-detail', {
            parent: 'entity',
            url: '/property/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Property'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/property/property-detail.html',
                    controller: 'PropertyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Property', function($stateParams, Property) {
                    return Property.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'property',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('property-detail.edit', {
            parent: 'property-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/property/property-dialog.html',
                    controller: 'PropertyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Property', function(Property) {
                            return Property.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('property.new', {
            parent: 'property',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/property/property-dialog.html',
                    controller: 'PropertyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                propertyName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('property', null, { reload: 'property' });
                }, function() {
                    $state.go('property');
                });
            }]
        })
        .state('property.edit', {
            parent: 'property',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/property/property-dialog.html',
                    controller: 'PropertyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Property', function(Property) {
                            return Property.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('property', null, { reload: 'property' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('property.delete', {
            parent: 'property',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/property/property-delete-dialog.html',
                    controller: 'PropertyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Property', function(Property) {
                            return Property.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('property', null, { reload: 'property' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
