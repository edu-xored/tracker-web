angular
  .module('trackerWebApp')
  .config(function($routeProvider, $locationProvider) {
    $routeProvider
      .otherwise({
        templateUrl: '/views/allIssuesView.html',
        controller: 'issuesController'
      });
      $locationProvider.html5Mode(true);
});
