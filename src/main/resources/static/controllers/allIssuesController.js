angular.module('trackerWebApp')
    .controller('issuesController', function(modalDialog, $scope, $http, $element) {
        $scope.dateOrder = function (val) {
                   return ('0' +val.createdDateTime[0]).slice(-2) + ('0' +val.createdDateTime[1]).slice(-2)
                    					+ ('0' +val.createdDateTime[2]).slice(-2) + ('00' +val.createdDateTime[3]).slice(-2)
                    					+ ('00' +val.createdDateTime[4]).slice(-2)
                    					+ ((val.createdDateTime[5] == null) ? '00' : '00' + val.createdDateTime[5]).slice(-2);
                }
        $scope.currentPage = 0;
        $scope.itemsPerPage = 10;
        $scope.itemsPerPageSelects = [2, 5, 10, 20, 50];
        $scope.issues = [];
        $scope.displayIssueExpanded = [];
        $scope.changeDisplayIssueExpanded = function (hash) {
            $scope.displayIssueExpanded[hash] = !$scope.displayIssueExpanded[hash];
        }
        $scope.statusSelectBackground = 'white';
        $scope.statusSelectTextColor = 'black';
        $scope.firstPage = function() {
            return $scope.currentPage == 0;
        }
        $scope.lastPage = function() {
            var lastPageNum = Math.ceil($scope.filtered.length / $scope.itemsPerPage - 1);
            if (lastPageNum < 0)
                lastPageNum = 0;
            return $scope.currentPage == lastPageNum;
        }
        $scope.numberOfPages = function(){
            var pages = Math.ceil($scope.filtered.length / $scope.itemsPerPage);
            if (pages == 0)
                pages = 1;
            return pages;
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
        $scope.changeStatusSelectBackground = function () {
            switch ($scope.selectedStatus) {
                case "OPEN":
                    $scope.statusSelectBackground = 'rgb(100, 177, 15)';
                    $scope.statusSelectTextColor = 'white';
                    break
                case "RESOLVED":
                    $scope.statusSelectBackground = '#878787';
                    $scope.statusSelectTextColor = 'white';
                    break
                default:
                    $scope.statusSelectBackground = 'white';
                    $scope.statusSelectTextColor = 'black';
                    break
            }
        }
        $http.get('/api/issues').
            then(function(response) {
                $scope.issues = response.data;
            }, function(response) {
                $scope.error = response.status.Text;
            });
        $scope.selectedStatus = "ALL";
        $scope.statusVariants = ["ALL", "OPEN", "RESOLVED"];
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
