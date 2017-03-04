angular.module('changePasswordController', ['factories','services'])
    .controller('ChangePasswordController', function($scope,ProfileService) {
        console.log("Inside ChangePasswordController");

      $scope.profile={
        "oldPassword":"",
        "password":"",
        "confirmPassword":""
      };

      $scope.updatePassword = function(updatePasswordForm){
        if(updatePasswordForm.$valid){
          if($scope.profile.password==$scope.profile.confirmPassword){
            ProfileService.changePassword($scope.profile)
                .then(function successCallback(response) {
                  alert(response.data.message);
                  //Reset data again
                  $scope.profile={
                    "oldPassword":"",
                    "password":"",
                    "confirmPassword":""
                  };
                }, function errorCallback(response) {
                  console.error("There is a error..");
                });
          }else{
            alert("New password and confirm password doesn't match");
          }
        }else{
          alert("Please enter all fields");
        }
      }
    });