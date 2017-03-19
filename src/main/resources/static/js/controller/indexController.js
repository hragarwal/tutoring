angular.module('indexController', ['factories','services'])
    .controller('indexController', function($scope,AppConstants,$location,TutoringFactory,$sessionStorage) {
      console.log("Inside IndexController");
      
      var profile  = TutoringFactory.getProfile();
      if(profile=="" || angular.isUndefined(profile)) {
    	  $sessionStorage.isLoggedIn = false;
      } else {
    	  $sessionStorage.isLoggedIn = true;
      }
      $scope.isLoggedIn = $sessionStorage.isLoggedIn;
    }); 