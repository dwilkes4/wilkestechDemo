(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rentee', {
            parent: 'entity',
            url: '/rentee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rentees'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rentee/rentees.html',
                    controller: 'RenteeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('rentee-detail', {
            parent: 'entity',
            url: '/rentee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rentee'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rentee/rentee-detail.html',
                    controller: 'RenteeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Rentee', function($stateParams, Rentee) {
                    return Rentee.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rentee',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rentee-detail.edit', {
            parent: 'rentee-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rentee/rentee-dialog.html',
                    controller: 'RenteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rentee', function(Rentee) {
                            return Rentee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rentee.new', {
            parent: 'rentee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rentee/rentee-dialog.html',
                    controller: 'RenteeDialogController',
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
                    $state.go('rentee', null, { reload: 'rentee' });
                }, function() {
                    $state.go('rentee');
                });
            }]
        })
        .state('rentee.edit', {
            parent: 'rentee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rentee/rentee-dialog.html',
                    controller: 'RenteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rentee', function(Rentee) {
                            return Rentee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rentee', null, { reload: 'rentee' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rentee.delete', {
            parent: 'rentee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rentee/rentee-delete-dialog.html',
                    controller: 'RenteeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rentee', function(Rentee) {
                            return Rentee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rentee', null, { reload: 'rentee' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
