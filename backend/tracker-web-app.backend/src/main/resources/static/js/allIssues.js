angular.module('issuesApp', [])
  .controller('issuesController', function($scope, $http) {
      $http.get('/issues').
      then(function(response) {
          $scope.issues = response.data;
      }, function(response) {
          $scope.error = response.status.Text;
      });
});
