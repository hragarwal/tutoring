
angular.module('homePageController', ['factories','services'])
.controller('homePageController', function($scope, LessonService, AppConstants) {
	
	
	console.log("Inside lessonController");
	
    $scope.headingTitle = "Create Lesson";
    var lessons;
    LessonService.getAllLessons()
    .then(function successCallback(response) {
                if(response.data.status = AppConstants.API_SUCCESS) {
                	lessons = response.data.data;
             	  } else {
             		  alert(response);
             	  }
              }, function errorCallback(response) {
                console.error("There is a error..");
     });
    $scope.lessonList = lessons;
    
    LessonService.getLessonSubjects()
    .then(function successCallback(response) {
                if(response.data.status = AppConstants.API_SUCCESS) {
             		  $scope.subjectsList= response.data.data;
             	  } else {
             		  alert(response);
             	  }
              }, function errorCallback(response) {
                console.error("There is a error..");
     });
    
    $scope.lesson={
  	        "subjectID":"",
  	        "title":"",
  	        "taskDescription":"",
  	        "specialRequirement":"",
    };
    
    $scope.createLesson = function(lessonForm){
        if(lessonForm.$valid){
      	  LessonService.createLesson($scope.lesson)
              .then(function successCallback(response) {
                if(response.data.status = AppConstants.API_SUCCESS) {
             		  alert('Created');
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