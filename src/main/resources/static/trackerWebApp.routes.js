angular
	.module('trackerWebApp')
	.config(function($routeProvider, $locationProvider) {
	$routeProvider
	.when('/issues/addNew', {
		templateUrl: '/views/addNewView.html',
		controller: 'addNewController'
	})
	.when('/issues/:hash', {
		templateUrl: '/views/issueView.html',
		controller: 'issueController'
	})
	.otherwise({
		templateUrl: '/views/allIssuesView.html',
		controller: 'issuesController'
	});
	$locationProvider.html5Mode(true);
});
