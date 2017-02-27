(function() {
    'use strict';

    angular
        .module('wilkestechDemoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('day-price', {
            parent: 'entity',
            url: '/day-price',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DayPrices'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/day-price/day-prices.html',
                    controller: 'DayPriceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('day-price-detail', {
            parent: 'entity',
            url: '/day-price/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DayPrice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/day-price/day-price-detail.html',
                    controller: 'DayPriceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DayPrice', function($stateParams, DayPrice) {
                    return DayPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'day-price',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('day-price-detail.edit', {
            parent: 'day-price-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-price/day-price-dialog.html',
                    controller: 'DayPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DayPrice', function(DayPrice) {
                            return DayPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('day-price.new', {
            parent: 'day-price',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-price/day-price-dialog.html',
                    controller: 'DayPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                priceOfDay: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('day-price', null, { reload: 'day-price' });
                }, function() {
                    $state.go('day-price');
                });
            }]
        })
        .state('day-price.edit', {
            parent: 'day-price',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-price/day-price-dialog.html',
                    controller: 'DayPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DayPrice', function(DayPrice) {
                            return DayPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('day-price', null, { reload: 'day-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('day-price.delete', {
            parent: 'day-price',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-price/day-price-delete-dialog.html',
                    controller: 'DayPriceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DayPrice', function(DayPrice) {
                            return DayPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('day-price', null, { reload: 'day-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
