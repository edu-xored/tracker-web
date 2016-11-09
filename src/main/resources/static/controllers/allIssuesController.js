angular.module('trackerWebApp')
	.controller('issuesController', function($scope, $http) {
		$http.get('/api/issues').
		then(function(response) {
			$scope.issues = response.data;
		}, function(response) {
			$scope.error = response.status.Text;
		});
		$scope.selectedStatus = "";
		$scope.statusVariants = ["", "OPEN", "CLOSED"];
	});
