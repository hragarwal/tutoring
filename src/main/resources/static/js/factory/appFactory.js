/**
 * 
 */
angular.module('customFactory',[])
    .factory('AppFactory', function(){
      var appFactory= {};
  	  var TOAST_LENGTH = 3000; 
  	  
      appFactory.toastSuccess= function (message) {
    	  Materialize.toast(message, TOAST_LENGTH, 'toast-success');
      };

      appFactory.toastError= function (message) {
    	  Materialize.toast(message, TOAST_LENGTH, 'toast-error');
      };
      
      appFactory.toastInfo= function (message) {
    	  Materialize.toast(message, TOAST_LENGTH, 'toast-info');
      };
      
      appFactory.toastWarning= function (message) {
    	  Materialize.toast(message, TOAST_LENGTH, 'toast-warning');
      };

      return appFactory;
});

