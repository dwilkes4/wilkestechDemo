(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('renter', {
            parent: 'entity',
            url: '/renter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Renters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/renter/renters.html',
                    controller: 'RenterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('renter-detail', {
            parent: 'entity',
            url: '/renter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Renter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/renter/renter-detail.html',
                    controller: 'RenterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Renter', function($stateParams, Renter) {
                    return Renter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'renter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('renter-detail.edit', {
            parent: 'renter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/renter/renter-dialog.html',
                    controller: 'RenterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Renter', function(Renter) {
                            return Renter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('renter.new', {
            parent: 'renter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/renter/renter-dialog.html',
                    controller: 'RenterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                email: null,
                                phoneNumber: null,
                                password: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('renter', null, { reload: 'renter' });
                }, function() {
                    $state.go('renter');
                });
            }]
        })
        .state('renter.edit', {
            parent: 'renter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/renter/renter-dialog.html',
                    controller: 'RenterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Renter', function(Renter) {
                            return Renter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('renter', null, { reload: 'renter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('renter.delete', {
            parent: 'renter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/renter/renter-delete-dialog.html',
                    controller: 'RenterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Renter', function(Renter) {
                            return Renter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('renter', null, { reload: 'renter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
