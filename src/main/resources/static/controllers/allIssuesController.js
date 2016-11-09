angular.module('trackerWebApp')
	.controller('issuesController', function($scope, $http) {
		$scope.currentPage = 0;
		$scope.itemsPerPage = 10;
		$scope.itemsPerPageSelects = [2, 5, 10, 20, 50];
		$scope.issues = [];
		$scope.firstPage = function() {
            return $scope.currentPage == 0;
        }
        $scope.lastPage = function() {
            var lastPageNum = Math.ceil($scope.filtered.length / $scope.itemsPerPage - 1);
            return $scope.currentPage == lastPageNum;
        }
        $scope.numberOfPages = function(){
            return Math.ceil($scope.filtered.length / $scope.itemsPerPage);
        }
        $scope.startingItem = function() {
            return $scope.currentPage * $scope.itemsPerPage;
        }
        $scope.pageBack = function() {
            $scope.currentPage = $scope.currentPage - 1;
        }
        $scope.pageForward = function() {
            $scope.currentPage = $scope.currentPage + 1;
        }
        $scope.changeItemsPerPage = function() {
            $scope.currentPage = Math.floor($scope.currentPage * $scope.itemsPerPage / $scope.itemsPerPageSelect);
            $scope.itemsPerPage = $scope.itemsPerPageSelect;
        }
        $scope.initItemsPerPage = function() {
            $scope.itemsPerPageSelect = $scope.itemsPerPage;
        }
        $scope.filteredPagination = function() {
            $scope.currentPage = 0;
        }
		$http.get('/issues').
		then(function(response) {
			$scope.issues = response.data;
			$scope.itemsSize = $scope.issues.length;
		}, function(response) {
			$scope.error = response.status.Text;
		});
	})
	.filter('startFrom', function() {
		return function(input, start) {
			start = +start; //parse to int
			return input.slice(start);
		}
	});