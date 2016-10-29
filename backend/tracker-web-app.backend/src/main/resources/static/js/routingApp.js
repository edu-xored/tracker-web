angular.module('routingApp', ['ngRoute'])
    .controller('SampleControllerOne', function ($scope) {
        $scope.number = 'ONE'
    })
    .controller('SampleControllerTwo', function ($scope) {
        $scope.number = 'Two'
    })
    .config(function($routeProvider, $locationProvider) {
        $routeProvider
            .when('/firstTemplate', {
                templateUrl: 'html/sampleTemplateOne.html',
                controller: 'SampleControllerOne'
            })
            .when('/secondTemplate', {
                templateUrl: 'html/sampleTemplateTwo.html',
                controller: 'SampleControllerTwo'
            });
        $locationProvider.html5Mode(true);
    })
;