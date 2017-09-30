angular.module('loginController', ['factories','services'])
    .controller('LoginController', function($scope,LoginService,AppConstants,$location,TutoringFactory,$rootScope, AppFactory) {
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
        	   AppFactory.toastError(error.data.message);
           }).finally(function () {
           });
        }else{
        	 AppFactory.toastError('Enter all fields');
        }
      };
      
});