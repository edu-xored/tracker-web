angular.module('trackerWebApp')
    .controller('addNewController', function($scope, $http) {
        $scope.issue = {};
        // TODO: CLOSED to RESOLVED
        $scope.statusVariants = ['OPEN', 'CLOSED'];
        $scope.submitForm = function() {
            if (!$scope.issue.summary || !$scope.issue.description) {
                $scope.status = 'Some fields are not filled!';
                return;
            }
            $http({
                method    :    'POST',
                url       :    '/api/issues/',
                data      :    $scope.issue
            })
                .success(function(response) {
                    $scope.status = 'Issue added with hash: #' + response.hash.substring(0,6) + '.';
                })
                .error(function(response) {
                    $scope.status = 'Issue didn\'t add.';
                    alert(response.message);
                });
        };
    });
