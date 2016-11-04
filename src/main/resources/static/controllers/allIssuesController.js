angular.module('trackerWebApp')
	.controller('issuesController', function($scope, $http) {
		$scope.selectedStatus = "";
		$scope.statusVariants = ["", "OPEN", "CLOSED"];
		$http.get('/issues').
		then(function(response) {
			$scope.issues = response.data;
		}, function(response) {
			$scope.error = response.status.Text;
		});
});
