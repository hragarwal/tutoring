angular.module('forgotPasswordController', ['factories','services'])
    .controller('ForgotPasswordController', function($scope,ProfileService) {
      console.log("Inside ForgotPasswordController");

      $scope.profile={
        "email":""
      };

      $scope.generateTemporaryPassword = function(forgotPasswordForm){
        if(forgotPasswordForm.$valid){
          ProfileService.forgotPassword($scope.profile.email)
              .then(function successCallback(response) {
                alert(response.data.message);
              }, function errorCallback(response) {
                console.error("There is a error..");
              });
        }else{
          alert("Please enter email id correctly");
        }
      }
    });