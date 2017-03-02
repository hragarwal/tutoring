angular.module('homeController', ['factories','services'])
.controller('homeController', function($scope, LessonService, AppConstants, $location, $sessionStorage, FileService) {
	
	console.log("Inside lessonController");
	  $scope.active="lessons";
    $scope.headingTitle = "Create Lesson";
    
    // get all lesson for profile
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
    
    // get all available lesson for profile not for student
   /* LessonService.getAllAvailableLessons()
    .then(function successCallback(response) {
                if(response.data.status == AppConstants.API_SUCCESS) {
                	$scope.availableLessonList = response.data.data;
             	  } else {
             		  alert(response);
             	  }
              }, function errorCallback(response) {
                console.error("There is a error..");
     });*/
    
    // fetch all subjects from lesson
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

    $scope.setActiveTab = function(tabName){
        $scope.active=tabName;
        if(tabName=="newLesson"){
          FileService.deleteTempUserFiles()
              .then(function successCallback(response) {
                console.log("Success, files deleted");
              }, function errorCallback(response) {
                console.error("There is a error..");
              });
        }
    };
    
});