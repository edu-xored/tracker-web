var postApp = angular.module('postApp', []);
postApp.controller('postController', function($scope, $http)
{
	$scope.comment = {};
	$scope.submitForm = function() {
		$http({
			method	:	'POST',
			url	:	'postcomment',
			data	:	$scope.comment
		}).success(function() {});
	};
});