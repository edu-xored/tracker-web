angular.module('trackerWebApp')
	.controller('postCommentController', function($scope, $http, $routeParams) {
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

		$scope.autoExpand = function(event) {
			var element = typeof event === 'object' ? event.target : document.getElementById(event);
			var scrollHeight = element.scrollHeight -60;
			element.style.height =  scrollHeight + "px";
		};
	});
