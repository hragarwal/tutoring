angular.module('lessonController', ['factories','services'])
    .controller('LessonController', function($scope, LessonService, AppConstants) {
      console.log("Inside lessonController");
      $scope.headingTitle = "Create Lesson";
      
      $scope.lesson={
    	        "subject":"",
    	        "title":"",
    	        "taskDescription":"",
    	        "specialRequirement":"",
      };
      
      $scope.createLesson = function(lessonForm){
          if(lessonForm.$valid){
        	  LessonService.createLesson($scope.lesson)
                .then(function successCallback(response) {
                  if(response.data.status == AppConstants.API_SUCCESS) {
               		  alert(response.data.message);
               	  } else {
               		  alert(response.data.message);
               	  }
                }, function errorCallback(response) {
                  console.error("There is a error..");
                });
          }else{
            alert("Enter all fields");
          }
        }
      
    });