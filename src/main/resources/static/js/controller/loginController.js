angular.module('loginController', ['factories','services'])
    .controller('LoginController', function($scope,LoginService,AppConstants,$location,TutoringFactory,$rootScope) {
      $scope.headingTitle = "Login";

      $scope.login={
        'email' : '',
        'password' : ''
      };

      $scope.validateUser = function(loginForm){
        if(loginForm.$valid){
        	LoginService.validateUser($scope.login).then(function(response) {
	    		 TutoringFactory.setProfile(response.data.data);
	             TutoringFactory.setAuthorized(true);
	             $rootScope.isLoggedIn = true;
	           	 $location.path('home');
           }).catch(function (error) {
        	   alert(error.data.message);
           }).finally(function () {
           });
        }else{
        	alert("Enter all fields");
        }
      };
      
});