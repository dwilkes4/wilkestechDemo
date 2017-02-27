(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dates-rented', {
            parent: 'entity',
            url: '/dates-rented',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DatesRenteds'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dates-rented/dates-renteds.html',
                    controller: 'DatesRentedController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('dates-rented-detail', {
            parent: 'entity',
            url: '/dates-rented/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DatesRented'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dates-rented/dates-rented-detail.html',
                    controller: 'DatesRentedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DatesRented', function($stateParams, DatesRented) {
                    return DatesRented.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dates-rented',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dates-rented-detail.edit', {
            parent: 'dates-rented-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dates-rented/dates-rented-dialog.html',
                    controller: 'DatesRentedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DatesRented', function(DatesRented) {
                            return DatesRented.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dates-rented.new', {
            parent: 'dates-rented',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dates-rented/dates-rented-dialog.html',
                    controller: 'DatesRentedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                price: null,
                                startDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dates-rented', null, { reload: 'dates-rented' });
                }, function() {
                    $state.go('dates-rented');
                });
            }]
        })
        .state('dates-rented.edit', {
            parent: 'dates-rented',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dates-rented/dates-rented-dialog.html',
                    controller: 'DatesRentedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DatesRented', function(DatesRented) {
                            return DatesRented.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dates-rented', null, { reload: 'dates-rented' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dates-rented.delete', {
            parent: 'dates-rented',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dates-rented/dates-rented-delete-dialog.html',
                    controller: 'DatesRentedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DatesRented', function(DatesRented) {
                            return DatesRented.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dates-rented', null, { reload: 'dates-rented' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
