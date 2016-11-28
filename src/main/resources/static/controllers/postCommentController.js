angular.module('trackerWebApp')
	.controller('postCommentController', function($scope, $http, $timeout, $routeParams) {
		$scope.comment = {};
		$scope.submitForm = function() {
			if (!$scope.comment.content) {
				return;
			}
			$http({
				method    :    'POST',
				url       :    '/api/issues/' + $routeParams.hash + '/comments',
				data      :    $scope.comment
			})
				.success(function() {
					console.log('Comment posted.');
				})
				.error(function() {
					console.log('Comment didn\'t post.');
				});
		};
	});
