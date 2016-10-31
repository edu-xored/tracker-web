angular.module('issueApp', [])
    .controller('issueController', function($location,$http){
        var issue = this;
        var url=$location.absUrl();
        var paramString = "?hash=";
        var hash="";
        lastIndex=url.lastIndexOf(paramString);
        if(lastIndex!=-1) {
            hash=url.slice(lastIndex + paramString.length);
        }
        if(hash.length<20 && hash.match(/^[0-9]+$/)) {
           $http({
           method: 'GET',
           url: "/issues/" + hash
           }).then(function successCallback(response) {
                issue.summary=response.data.summary;
                issue.description=response.data.description;
                issue.hash=response.data.hash;
                }, function errorCallback(response) {
                issue.error = response.data.message;
                });
         }
         else {
         issue.error = "Wrong param";
         }
    });
