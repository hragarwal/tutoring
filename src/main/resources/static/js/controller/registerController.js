angular.module('registerController', [])
    .controller('RegisterController', function($scope) {
      console.log("Inside registerController");
      $scope.headingTitle = "Register HTML";
      
      $scope.register={
    	        "name":"",
    	        "email":"",
    	        "country":"",
    	        "contactNumber":"",
    	        "skypeId":"",
    	        "password":"",
    	        "confirmPassword":"",
    	      };
      
      $scope.createProfile = function(loginForm){
          console.log("register details:"+$scope.resgitser);
          if(registerForm.$valid){
        	  ProfileService.createProfile($scope.register)
                .then(function successCallback(response) {
                  console.log("register successfull");
                }, function errorCallback(response) {
                  console.error("There is a error..");
                });
          }else{
            alert("Enter all fields");
          }
        }
      
    });