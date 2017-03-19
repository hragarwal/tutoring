angular.module('forgotPasswordController', ['factories','services'])
    .controller('ForgotPasswordController', function($scope,ProfileService) {
      console.log("Inside ForgotPasswordController");
      $scope.headingTitle = "Forgot Password";

      $scope.profile={
        "email":""
      };

      $scope.generateTemporaryPassword = function(forgotPasswordForm){
        if(forgotPasswordForm.$valid){
          ProfileService.forgotPassword($scope.profile.email)
              .then(function successCallback(response) {
 //               alert(response.data.message);
                $('.alertMessage').html(response.data.message).removeClass('hide');
              }, function errorCallback(response) {
                console.error("There is a error..");
                $('.alertMessage').html("There is a error..").removeClass('hide');
              });
        }else{
         // alert("Please enter email id correctly");
          $('.alertMessage').html("Please enter email id correctly").removeClass('hide');
        }
      }
    });