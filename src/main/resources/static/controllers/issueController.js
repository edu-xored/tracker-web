angular.module('trackerWebApp')
  .controller('issueController', function($scope, $http, $routeParams) {
    $http({
       method: 'GET',
       url: "/issues/" + $routeParams.hash
     })
     .then(function(response) {
        $scope.issue = response.data;
      }, function(response) {
          $scope.error = response.status.Text;
      });
});
