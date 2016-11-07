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
		$scope.sortField = undefined;
		$scope.reverse = false;
		$scope.sort = function(fieldName) {
			if ($scope.sortField == fieldName) {
				$scope.reverse = !$scope.reverse;
			} else {
				$scope.sortField = fieldName;
				$scope.reverse = false;
			}
		};
	});
