angular.module('forgotPasswordController', ['factories','services'])
    .controller('ForgotPasswordController', function($scope,ProfileService, AppFactory, AppConstants) {
      $scope.headingTitle = "Forgot Password";

      $scope.profile={
        "email": ""
      };

      $scope.generateTemporaryPassword = function(forgotPasswordForm) {
          if(forgotPasswordForm.$valid) {
        		  ProfileService.forgotPassword($scope.profile.email).then(function(response) {
        		  AppFactory.toastSuccess(response.data.message);
        		  $('#forgotPasswordForm')[0].reset();
               }).catch(function (error) {
            	   AppFactory.toastError(error.data.message);
               }).finally(function () {
               });
          } else{
        	  AppFactory.toastError(AppConstants.MISSING_FIELD_ERROR);
          }
      };

      
    });