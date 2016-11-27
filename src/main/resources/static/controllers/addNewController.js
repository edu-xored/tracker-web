angular.module('trackerWebApp')
    .controller('addNewController', function($scope, $http, $routeParams) {
        $scope.issue = {};
        $scope.issue.status = 'OPEN';
        $scope.submitForm = function() {
            if (!$scope.issue.summary) {
                $scope.status = 'Summary isn\'t filled!';
                return;
            }
            $http({
                method    :    'POST',
                url       :    '/api/issues/',
                data      :    $scope.issue
            })
                .success(function(response) {
                    $scope.status = 'Issue added with hash: #' + response.hash;
                })
                .error(function(response) {
                    $scope.status = 'Issue didn\'t add.';
                    alert(response.message);
                });
        };
    });
