angular.module('registerController', ['factories','services'])
    .controller('RegisterController', function($scope, ProfileService, AppConstants) {
      console.log("Inside registerController");
      $scope.headingTitle = "Register HTML";
      
      $scope.profile={
    	        "name":"",
    	        "email":"",
    	        "country":"",
    	        "contactNumber":"",
    	        "skypeId":"",
    	        "password":"",
    	        "confirmPassword":"",
      };
      
      $scope.createProfile = function(registerForm){
          console.log("register details:"+JSON.stringify($scope.profile.name));
          if(registerForm.$valid){
        	  ProfileService.createProfile($scope.profile)
                .then(function successCallback(response) {
                  if(response.data.status = AppConstants.API_SUCCESS) {
               		  alert(response.data.message);
               	  } else {
               		  alert(response.data.message);
               	  }
                }, function errorCallback(response) {
                  console.error("There is a error..");
                });
          }else{
            alert("Enter all fields");
          }
        }
      
    });