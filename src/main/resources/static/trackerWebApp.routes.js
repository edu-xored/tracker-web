angular
  .module('trackerWebApp')
  .config(function($routeProvider, $locationProvider) {
    $routeProvider
      .when('/issues/addNew', {
        templateUrl: '/views/addNewView.html',
        controller: 'issueController'
      })
      .when('/issues/:hash', {
        templateUrl: '/views/issueView.html',
        controller: 'addNewController'
      })
      .when('/issues/:hash/postcomment', {
        templateUrl: '/views/postCommentView.html',
        controller: 'postCommentController'
      })
      .otherwise({
        templateUrl: '/views/allIssuesView.html',
        controller: 'issuesController'
      });
      $locationProvider.html5Mode(true);
});
