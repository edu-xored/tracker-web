angular.module('trackerWebApp')
    .controller('addNewController', function($scope, $http) {
        $scope.issue = {};
        $scope.issue.status = 'OPEN';
        $scope.issue.description = '';
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
                    $scope.status = 'Issue created with hash: #' + response.hash;
                    $scope.issue.summary = '';
                    $scope.issue.description = '';
                })
                .error(function(response) {
                    $scope.status = 'Issue didn\'t add.';
                    alert(response.message);
                });
        };
    });
