/**
 * @author chirag.agrawal 
 */

angular.module('headerController', ['factories','services'])
    .controller('headerController', function($scope, TutoringFactory, $rootScope, ProfileService, $location) {
      console.log("Inside HeaderController");
      var profile  = TutoringFactory.getProfile();
      if(profile) {
        $rootScope.isLoggedIn = true;
        $scope.loggedInUsername = profile.name;
      } else {
        $rootScope.isLoggedIn = false;
        $scope.loggedInUsername = '';
      }

      $scope.redirectToUrl = function(url) {
    	  $location.path(url);
      }
      
      $scope.logout  = function(){
    	  TutoringFactory.clearSessionStorage();
    	  ProfileService.logoutUser().then(function(response) {
      		 console.log("User successfully logged out");
             $location.path('login');
             $rootScope.isLoggedIn=false;
    	  }).catch(function (error) {
    		 alert(error.data.message);
          }).finally(function () {
          });
      }
      
});