var postApp = angular.module('postApp', []);
	postApp.controller('postController', function($scope, $http) {
		$scope.comment = {};
		$scope.submitForm = function() {
		$http.post('/comment', $scope.comment);
	};
});