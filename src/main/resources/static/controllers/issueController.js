angular.module('trackerWebApp')
  .controller('issueController', function($scope, $http, $routeParams) {
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
      $scope.comment = {};
      		$scope.submitForm = function() {
      			if (!$scope.comment.content) {
      				$scope.status = 'Enter a comment!';
      				return;
      			}
      			$http({
      				method    :    'POST',
      				url       :    '/api/issues/' + $routeParams.hash + '/comments',
      				data      :    $scope.comment
      			})
      				.success(function() {
      					$scope.status = 'Comment posted.';
      				})
      				.error(function() {
      					$scope.status = 'Comment didn\'t post.';
      				});
      		};
});