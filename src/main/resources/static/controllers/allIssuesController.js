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
        $http.get('/api/issues').
            then(function(response) {
                $scope.issues = response.data;
                $scope.issues.forEach(function (issue) {
                    issue.hash = issue.hash.substring(0,6);
                });
            }, function(response) {
                $scope.error = response.status.Text;
            });
        $scope.selectedStatus = "ALL";
        $scope.statusVariants = ["ALL", "OPEN", "CLOSED"];
        $scope.statusFilter = function (issue) {
            if((issue.status === $scope.selectedStatus) || ('ALL' === $scope.selectedStatus)) {
                return issue;
            }
        };
    })
    .filter('startFrom', function() {
        return function(input, start) {
            start = +start; //parse to int
            return input.slice(start);
        }
    });
