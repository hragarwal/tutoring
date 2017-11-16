angular.module('factories',[])


    .constant("AppConstants", {
      "HTTP_BASE_URL": "http://localhost:8089",
      "API_SUCCESS" : 0,
      "API_OK" : 200,
      'MISSING_FIELD_ERROR' : 'Please enter required fields.',
      "STUDENT_ROLE_ID":16,
      "LESSON_AVAILABLE": 1,
  	  "LESSON_ACCEPTED" : 2,
  	  "LESSON_REJECTED" : 4,
  	  "LESSON_IN_PROGRESS" : 8,
      "LESSON_WAITING_PAYMENT" : 16,
      "LESSON_PAYMENT_MADE" : 32,
      "LESSON_SUBMITTED":64,
      "LESSON_COMPLETED":128,
      "LESSON_CANCELLED":256,
      "LESSON_EXPIRED":512
    })

    .factory('TutoringFactory', function($sessionStorage,$location, AppConstants){
        var factory={};
        factory.checkIfSessionExistFOrData = function(dataObject){
          if(dataObject){
            return dataObject;
          }else{
            $location.path('landing');
          }
        }
        factory.clearSessionStorage = function(){
          $sessionStorage.profile="";
          $sessionStorage.lessonStatus="";
          $sessionStorage.lessonId="";
          $sessionStorage.subjectList="";
          $sessionStorage.lessonStatus=""
        }
        factory.setProfile= function (profile) {
          $sessionStorage.profile = profile;
        }
        factory.getProfile= function () {
          return this.checkIfSessionExistFOrData($sessionStorage.profile);
        }
        // to store all lesson status
        factory.setLessonStatus= function (lessonStatus) {
  	      $sessionStorage.lessonStatus = lessonStatus;
  	    }
  	    factory.getLessonStatus= function () {
  	      return $sessionStorage.lessonStatus;
  	    }
        factory.setLessonId = function (lessonId) {
          $sessionStorage.lessonId = lessonId;
        }
        factory.getLessonId= function () {
          return this.checkIfSessionExistFOrData($sessionStorage.lessonId);
        }
        factory.setAuthorized = function(flag){
          $sessionStorage.isAuthorized = flag;
        }
        factory.getAuthorized = function(){
          return this.checkIfSessionExistFOrData($sessionStorage.isAuthorized);
        }
        factory.setSubjectList= function (subjectList) {
          $sessionStorage.subjectList = subjectList;
        }
        factory.getSubjectList= function () {
          return $sessionStorage.subjectList;
        }
        factory.getFilterLessonStatusList = function (currentStatus) {
        	var updateLessonStatus = [];
        	if($sessionStorage.lessonStatus) {
        	$sessionStorage.lessonStatus.forEach(function (entry, index, array) {
        		if(entry.id > currentStatus) {
        			// current status is available than completed status should not be visisble for student
        			if(currentStatus == AppConstants.LESSON_AVAILABLE && entry.id == AppConstants.LESSON_COMPLETED) {
        				// do nothing
        			}
        			else if(currentStatus == AppConstants.LESSON_COMPLETED) {
        				return updateLessonStatus;
        			}
        			else if(currentStatus == AppConstants.LESSON_SUBMITTED && entry.id == AppConstants.LESSON_CANCELLED) {
        				// do nothing
        			}
        			else {
        				updateLessonStatus.push(entry);
        			}
        		}
            });
        	}
        	return updateLessonStatus;
        }
        
      return factory;
    });

