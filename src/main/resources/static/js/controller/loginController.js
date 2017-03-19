angular.module('loginController', ['factories','services'])
    .controller('LoginController', function($scope,LoginService,AppConstants,$location,TutoringFactory,$rootScope) {
      console.log("Inside loginController");
      $scope.headingTitle = "Login";

      $scope.login={
        "email":"",
        "password":""
      };

      $scope.validateUser = function(loginForm){
        console.log("login details:"+$scope.login);
        if(loginForm.$valid){
          LoginService.validateUser($scope.login)
              .then(function successCallback(response) {
            	  if(response.data.status == AppConstants.API_SUCCESS) {
            		  TutoringFactory.setProfile(response.data.data);
                  TutoringFactory.setUnAuthorizedFlag(false);
                  $rootScope.isLoggedIn = true;
            		  $location.path('home');
            	  } else {
            		  //alert(response.data.message);
                      $('.alertMessage').html(response.data.message).removeClass('hide');
            	  }
              }, function errorCallback(response) {
                console.error("There is a error..");
                
                
              });
        }else{
          alert("Enter all fields");
          $('.alertMessage').html("Enter all fields").removeClass('hide');
        }
      }
    });