
angular.module('homePageController', ['factories','services'])
.controller('homePageController', function($scope, LessonService, AppConstants, $location, $sessionStorage) {
	
	console.log("Inside lessonController");
	
    $scope.headingTitle = "Create Lesson";

    LessonService.getAllLessons()
    .then(function successCallback(response) {
                if(response.data.status == AppConstants.API_SUCCESS) {
                	$scope.lessonList = response.data.data;
             	  } else {
             		  alert(response);
             	  }
              }, function errorCallback(response) {
                console.error("There is a error..");
     });
    
    
    
    LessonService.getLessonSubjects()
    .then(function successCallback(response) {
                if(response.data.status == AppConstants.API_SUCCESS) {
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
                if(response.data.status == AppConstants.API_SUCCESS) {
                	$scope.lessonList = response.data.data;
                	alert("created");
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
    
    $scope.openLesson = function(lessonId)
    {
    	LessonService.getLesson(lessonId)
        .then(function successCallback(response) {
                    if(response.data.status == AppConstants.API_SUCCESS) {
                    	$sessionStorage.lesson = response.data.data;
                    	$location.path('lessondetail');
                    	
                 	  } else {
                 		  alert(response);
                 	  }
                  }, function errorCallback(response) {
                    console.error("There is a error..");
         });
    };
    
});