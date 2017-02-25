angular.module('factories',[])


    .constant("AppConstants", {
      "HTTP_BASE_URL": "http://192.168.0.14:8080",
      "API_SUCCESS" : "0"
    })

    .factory('TutoringFactory', function(){
        var factory={};
        var profile;
        factory.setProfile= function (profile) {
          this.profile = profile;
        }
        factory.getProfile= function () {
          return this.profile;
        }
      return factory;
    });