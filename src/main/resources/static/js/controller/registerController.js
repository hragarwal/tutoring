angular.module('registerController', ['factories','services'])
    .controller('RegisterController', function($scope, ProfileService, AppConstants) {
      $scope.headingTitle = "Sign up";
      
      $scope.profile={
    	        'name': '',
    	        'email': '',
    	        'country': '',
    	        'contactNumber': '',
    	        'skypeId': '',
    	        'password': '',
    	        'confirmPassword': ''
      };
      
      $scope.createProfile = function(registerForm) {
          if(registerForm.$valid){
        	  ProfileService.createProfile($scope.profile).then(function(response) {
               		alert(response.data.message);
               }).catch(function (error) {
            	   alert(error.data.message);
               }).finally(function () {
               });
          } else{
        	  alert('Enter all fields');
          }
      }
      
});