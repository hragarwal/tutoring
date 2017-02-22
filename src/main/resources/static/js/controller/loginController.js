angular.module('loginController', ['factories','services'])
    .controller('LoginController', function($scope,LoginService) {
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
                console.log("Login successfull");
              }, function errorCallback(response) {
                console.error("There is a error..");
              });
        }else{
          alert("Enter all fields");
        }
      }
    });