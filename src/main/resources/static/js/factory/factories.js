angular.module('factories',[])


    .constant("AppConstants", {
      "HTTP_BASE_URL": "http://localhost:8080",
      "API_SUCCESS" : 0
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