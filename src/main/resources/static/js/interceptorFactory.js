angular.module('interceptorFactory',['factories'])

    .factory('httpInterceptor',function($q,$rootScope,TutoringFactory){
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
          if(response.status==401){
            if(!TutoringFactory.getUnAuthorizedFlag()){
              alert("Session expired");
              TutoringFactory.setUnAuthorizedFlag(true);
              $rootScope.$broadcast('unauthorized');
            }
          }else{
            alert("System encountered some error, please try after sometime");
          }
          return $q.reject(response);
        }
      };
      return httpInterceptor;
    });