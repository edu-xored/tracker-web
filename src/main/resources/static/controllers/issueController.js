angular.module('trackerWebApp')
  .controller('issueController', function(modalDialog, $scope, $http, $routeParams) {
	$http({
	   method: 'GET',
	   url: "/api/issues/" + $routeParams.hash
	 })
	 .then(function(response) {
		$scope.issue = response.data;
		$scope.error = null;
	  }, function(response) {
		  $scope.error = response.data.message;
	  });

	  $scope.resolveForm = function() {
		  $http({
			  method    :    'PATCH',
			  url       :    '/api/issues/' + $routeParams.hash,
			  data      :    $scope.issue
		  })
			  .success(function() {
				  $scope.issue.status = 'RESOLVED';
				  $scope.status = 'Resolved';
			   })
			   .error(function() {
				  $scope.status = 'Failed to resolve';
			   });
	  };
});
