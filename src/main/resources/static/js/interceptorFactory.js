angular.module('interceptorFactory',[])

    .factory('httpInterceptor',function($q){
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
            alert("Unauthorized");
            $rootScope.$broadcast('unauthorized');
          }else{
            alert("System encountered some error, please try after sometime");
          }
          return $q.reject(response);
        }
      };
      return httpInterceptor;
    });