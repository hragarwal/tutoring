angular.module('factories',[])


    .constant("AppConstants", {
      "HTTP_BASE_URL": "http://localhost:8080",
      "API_SUCCESS" : 0,
      "STUDENT_ROLE_ID":16,
      "LESSON_SUBMITTED":32,
      "LESSON_COMPLETED":64
    })

    .factory('TutoringFactory', function($sessionStorage,$location){
        var factory={};
        factory.checkIfSessionExistFOrData = function(dataObject){
          if(dataObject){
            return dataObject;
          }else{
            $location.path('login');
          }
        }
        factory.clearSessionStorage = function(){
          $sessionStorage.profile="";
          $sessionStorage.lessonStatus="";
          $sessionStorage.lesson="";
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
        factory.setLesson= function (lesson) {
          $sessionStorage.lesson = lesson;
        }
        factory.getLesson= function () {
          return this.checkIfSessionExistFOrData($sessionStorage.lesson);
        }
        factory.setUnAuthorizedFlag = function(flag){
          $sessionStorage.unAuthorizedFlagShown=flag;
        }
        factory.getUnAuthorizedFlag = function(){
          return this.checkIfSessionExistFOrData($sessionStorage.unAuthorizedFlagShown);
        }
        factory.setSubjectList= function (subjectList) {
          $sessionStorage.subjectList = subjectList;
        }
        factory.getSubjectList= function () {
          return $sessionStorage.subjectList;
        }
  	    
      return factory;
    });

