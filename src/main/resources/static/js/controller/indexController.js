angular.module('indexController', ['factories','services'])
    .controller('indexController', function($scope,TutoringFactory,$rootScope) {
      console.log("Inside IndexController");
      var profile  = TutoringFactory.getProfile();
      if(profile) {
        $rootScope.isLoggedIn = true;
      } else {
        $rootScope.isLoggedIn = false;
      }
    });