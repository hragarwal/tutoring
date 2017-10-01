angular.module('changePasswordController', ['factories','services'])
    .controller('ChangePasswordController', function($scope,ProfileService,TutoringFactory,AppConstants, AppFactory) {
      
    $scope.headingTitle = 'Change Password';

      $scope.profile={
        'oldPassword': '',
        'password': '',
        'confirmPassword':''
      };
      
      $scope.updatePassword = function(updatePasswordForm) {
          if(updatePasswordForm.$valid) {
        	  if($scope.profile.password == $scope.profile.confirmPassword) {
        	  ProfileService.changePassword($scope.profile).then(function(response) {
        		  AppFactory.toastSuccess(response.data.message);
                  TutoringFactory.setProfile(response.data.data);
        		  $('#updatePasswordForm')[0].reset();
               }).catch(function (error) {
            	   AppFactory.toastError(error.data.message);
               }).finally(function () {
               });
        	  } else {
        		  AppFactory.toastError("New password and confirm password doesn't match");
        	  }
          } else{
        	  AppFactory.toastError(AppConstants.MISSING_FIELD_ERROR);
          }
      };
      
    });