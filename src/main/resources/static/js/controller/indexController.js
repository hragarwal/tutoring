angular.module('indexController', ['factories','services'])
    .controller('indexController', function($scope, TutoringFactory, $rootScope, ProfileService, $location) {
      console.log("Inside IndexController");
      var profile  = TutoringFactory.getProfile();
      if(profile) {
        $rootScope.isLoggedIn = true;
      } else {
        $rootScope.isLoggedIn = false;
      }
      
      $scope.logout = function(){
          TutoringFactory.clearSessionStorage();
          ProfileService.logoutUser()
              .then(function successCallback(response) {
                console.log("User successfully logged out");
                $location.path('login');
                $rootScope.isLoggedIn=false;
              }, function errorCallback(response) {
                console.error("There is a error..");
              });
      }
      
    });