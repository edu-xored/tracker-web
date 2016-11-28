angular.module('trackerWebApp')
	.controller('postCommentController', function($scope, $http, $timeout, $routeParams) {
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
					$scope.status = 'Comment posted!';
				})
				.error(function() {
					$scope.status = 'Comment didn\'t post.';
				});
		};
	});
