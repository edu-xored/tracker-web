var postApp = angular.module('postApp', []);
postApp.controller('postController', function($scope, $http)
{
    $scope.comment = {};
	$scope.submitForm = function() {
	    if ($scope.comment.author == null) {
                $scope.comment.author = "Anonymous";
        }
		$http({
			method	:	'POST',
			url	:	'/postcomment',
			data	:	$scope.comment,
			params  :   {issueHash: $scope.issueHash},
			headers :   {'Content-Type': 'application/json'}
		});
	};
});