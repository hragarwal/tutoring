angular.module('changePasswordController', ['factories','services'])
    .controller('ChangePasswordController', function($scope,ProfileService,TutoringFactory,AppConstants) {
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
                  if(response.data.status == AppConstants.API_SUCCESS) {
                    alert(response.data.message);
                    TutoringFactory.setProfile(response.data.data);
                  } else {
                    alert(response.data.message);
                  }
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