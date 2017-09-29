/**
 * @author chirag.agrawal 
 */

angular.module('headerController', ['factories','services'])
    .controller('headerController', function($scope, TutoringFactory, $rootScope, ProfileService, $location) {
      console.log("Inside HeaderController");
      var profile  = TutoringFactory.getProfile();
      if(profile) {
        $rootScope.isLoggedIn = true;
      } else {
        $rootScope.isLoggedIn = false;
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