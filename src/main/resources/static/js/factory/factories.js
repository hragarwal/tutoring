angular.module('factories',[])


    .constant("AppConstants", {
      "HTTP_BASE_URL": "http://localhost:8080",
      "API_SUCCESS" : 0
    })

    .factory('TutoringFactory', function($sessionStorage){
        var factory={};
        factory.setProfile= function (profile) {
          $sessionStorage.profile = profile;
        }
        factory.getProfile= function () {
          return $sessionStorage.profile;
        }
      return factory;
    });