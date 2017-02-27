'use strict';

describe('Controller Tests', function() {

    describe('DatesRented Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDatesRented, MockRentee, MockProperty;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDatesRented = jasmine.createSpy('MockDatesRented');
            MockRentee = jasmine.createSpy('MockRentee');
            MockProperty = jasmine.createSpy('MockProperty');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DatesRented': MockDatesRented,
                'Rentee': MockRentee,
                'Property': MockProperty
            };
            createController = function() {
                $injector.get('$controller')("DatesRentedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'wilkestechDemoApp:datesRentedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
