angular.module('supportController', ['factories','services'])
    .controller('SupportController', function($scope, SupportService, AppFactory, AppConstants) {
      
      $scope.userSupport ={
    	        'email': '',
    	        'title': '',
    	        'message': '',
      };
      
      $scope.postQuery = function(userSupportForm) {
          if(userSupportForm.$valid){
        	  SupportService.postQuery($scope.userSupport).then(function(response) {
        		  AppFactory.toastSuccess(response.data.message);
        		  $('#userSupportForm')[0].reset();
               }).catch(function (error) {
            	   AppFactory.toastError(error.data.message);
               }).finally(function () {
               });
          } else{
        	  AppFactory.toastError(AppConstants.MISSING_FIELD_ERROR);
          }
      }
      
});