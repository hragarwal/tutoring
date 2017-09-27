angular.module('interceptorFactory',['factories'])

    .factory('httpInterceptor',function($q, $rootScope, TutoringFactory){
      var httpInterceptor = {
        request:function(config){
          return config;
        },
        response: function(response){
          return response;
        },
        requestError:function(rejectReason){
          return $q.reject(rejectReason);
        },
        responseError: function(response){
          if(response.status == 401 && TutoringFactory.getAuthorized()){
              alert("Session expired");
              TutoringFactory.setAuthorized(false);
              $rootScope.$broadcast('unauthorized');
          }
          return $q.reject(response);
        }
      };
      return httpInterceptor;
      
});