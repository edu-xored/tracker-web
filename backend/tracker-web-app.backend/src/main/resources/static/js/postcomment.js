var postApp = angular.module('postApp', []);
postApp.controller('postController', function($scope, $http)
{
	$scope.comment = {};
	$scope.submitForm = function() {
	    if (!$scope.comment.author) {
                $scope.comment.author = "Anonymous";
        }
		$http({
			method	:	'POST',
			url	:	'/issues/'+$scope.issueHash+'/comments',
			data	:	$scope.comment
		});
	};
});