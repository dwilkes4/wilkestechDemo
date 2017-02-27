'use strict';

describe('Controller Tests', function() {

    describe('Property Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProperty, MockRenter, MockAddress, MockDatesRented, MockDayPrice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProperty = jasmine.createSpy('MockProperty');
            MockRenter = jasmine.createSpy('MockRenter');
            MockAddress = jasmine.createSpy('MockAddress');
            MockDatesRented = jasmine.createSpy('MockDatesRented');
            MockDayPrice = jasmine.createSpy('MockDayPrice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Property': MockProperty,
                'Renter': MockRenter,
                'Address': MockAddress,
                'DatesRented': MockDatesRented,
                'DayPrice': MockDayPrice
            };
            createController = function() {
                $injector.get('$controller')("PropertyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'wilkestechDemoApp:propertyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
