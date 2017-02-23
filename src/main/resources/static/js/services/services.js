angular.module('services', ['factories'])
 

	.service('ProfileService', function ($http) {
      this.createProfile = function (profile){

      }
    })

    .service('LoginService', function ($http) {
        this.validateUser = function (login) {
          return $http({
            method: 'POST',
            headers: {
              'accept': 'application/json',
              'content-type': 'application/json'
            },
            data: login,
            url: '/api/login/'
          });
        }
    });
