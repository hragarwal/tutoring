angular.module('registerController', ['factories','services'])
    .controller('RegisterController', function($scope, ProfileService, AppConstants) {
      console.log("Inside registerController");
      $scope.headingTitle = "Create an Account";
      
      $scope.profile={
    	        "name":"",
    	        "email":"",
    	        "country":"",
    	        "contactNumber":"",
    	        "skypeId":"",
    	        "password":"",
    	        "confirmPassword":""
      };
      
      $scope.createProfile = function(registerForm){
          console.log("register details:"+JSON.stringify($scope.profile.name));
          if(registerForm.$valid){
        	  ProfileService.createProfile($scope.profile)
                .then(function successCallback(response) {
                  if(response.data.status == AppConstants.API_SUCCESS) {
               		  alert(response.data.message);
               	  } else {
               		  //alert(response.data.message);
               		  $('.alertMessage').html(response.data.message).removeClass('hide');
               	  }
                }, function errorCallback(response) {
                  console.error("There is a error..");
                  $('.alertMessage').html("There is a error..").removeClass('hide');
                });
          }else{
           // alert("Enter all fields");
            $('.alertMessage').html("Enter all fields").removeClass('hide');
          }
        }
      
    });