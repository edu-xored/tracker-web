angular.module('trackerWebApp')
	.controller('issuesController', function($scope, $http) {
		$scope.currentPage = 0;
    	$scope.pageSize = 10;
    	$scope.pageSizeSelects = [2, 5, 10, 20, 50];
    	$scope.issues = [];
    	$http.get('/issues').
    	then(function(response) {
        	$scope.issues = response.data;
    	}, function(response) {
        	$scope.error = response.status.Text;
    	});
    	$scope.numberOfPages = function() {
        	return Math.ceil($scope.issues.length / $scope.pageSize);
    	};
	})
	.filter('startFrom', function() {
    	return function(input, start) {
        	start = +start; //parse to int
        	return input.slice(start);
    	}
	});