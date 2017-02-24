angular.module('loginController', ['factories','services'])
    .controller('LoginController', function($scope,LoginService,AppConstants) {
      console.log("Inside loginController");

      $scope.login={
        "email":"",
        "password":""
      };

      $scope.validateUser = function(loginForm){
        console.log("login details:"+$scope.login);
        if(loginForm.$valid){
          LoginService.validateUser($scope.login)
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