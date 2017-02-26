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
	        url: '/lesson/'
	      });
	    }
	    
	    this.getLessonSubjects = function (){
			  return $http({
		        method: 'GET',
		        headers: {
		          'accept': 'application/json',
		          'content-type': 'application/json'
		        },
		        url: '/subject/'
		      });
		    }
	    
	    this.getAllLessons = function (){
			  return $http({
		        method: 'GET',
		        headers: {
		          'accept': 'application/json',
		          'content-type': 'application/json'
		        },
		        url: '/lesson/profile'
		      });
		    }

	    this.getLesson = function (lessonId){
			  return $http({
		        method: 'GET',
		        headers: {
		          'accept': 'application/json',
		          'content-type': 'application/json'
		        },
		        url: '/lesson/'+lessonId
		      });
		    }
	    
	    this.postMyMessage = function (userMessage, lessonID){
	    	var postLessonMessage = {
	    			"lesson":{
	    				"id":lessonID
	    			},
	    			"description":userMessage
	    		
	    	}
			  return $http({
		        method: 'POST',
		        headers: {
		          'accept': 'application/json',
		          'content-type': 'application/json'
		        },
		        data: postLessonMessage,
		        url: '/message/'
		      });
		    }
	    
	    this.getAllMessagesForLesson = function (lessonID){
	    	
			  return $http({
		        method: 'GET',
		        headers: {
		          'accept': 'application/json',
		          'content-type': 'application/json'
		        },
		        url: '/message/'+lessonID
		      });
		    }
	    
	  })

		.service('FileService', function ($http) {
				this.uploadFile = function(file){
					var fd = new FormData();
					fd.append('file', file);
					$http.post('/file/upload', fd, {
						transformRequest: angular.identity,
						headers: {'Content-Type': undefined}
					});
				}
		});