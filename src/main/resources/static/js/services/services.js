angular.module('services', ['factories'])
 
	// create profile service
	.service('ProfileService', function ($http) {
      this.createProfile = function (profile){
	  return $http({
          method: 'POST',
          headers: {
            'accept': 'application/json',
            'content-type': 'application/json'
          },
          data: profile,
          url: '/profile/'
        });
      }
    })

    // login service
    .service('LoginService', function ($http) {
        this.validateUser = function (login) {
          return $http({
            method: 'POST',
            headers: {
              'accept': 'application/json',
              'content-type': 'application/json'
            },
            data: login,
            url: '/login/'
          });
        }
    })
    
    // create lesson service
	.service('LessonService', function ($http) {
	    this.createLesson = function (lesson){
		  return $http({
	        method: 'POST',
	        headers: {
	          'accept': 'application/json',
	          'content-type': 'application/json'
	        },
	        data: lesson,
	        url: '/newlesson/'
	      });
	    }
	  });