var postApp = angular.module('postApp', []);
postApp.controller('postController', function($scope, $http)
{
	$scope.comment = {};
	$scope.submitForm = function() {
		if ($scope.issueHash == undefined) {
			alert( 'Enter a number of issue!' );
			return;
		}
		if (!$scope.comment.content) {
			alert( 'Enter a comment!' );
			return;
		}
	    if (!$scope.comment.author) {
                $scope.comment.author = "Anonymous";
        }
		$http({
			method	:	'POST',
			url	:	'/issues/' + $scope.issueHash + '/comments',
			data	:	$scope.comment
		});
	};
});
