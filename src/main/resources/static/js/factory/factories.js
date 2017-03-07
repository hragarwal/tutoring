angular.module('factories',[])


    .constant("AppConstants", {
      "HTTP_BASE_URL": "http://localhost:8080",
      "API_SUCCESS" : 0,
      "STUDENT_ROLE_ID":16
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
  	      return this.checkIfSessionExistFOrData($sessionStorage.lessonStatus);
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
  	    
      return factory;
    });

