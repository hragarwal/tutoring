angular.module('homeController', ['factories','services'])
    .controller('homeController', function($scope, LessonService, AppConstants, $location,ProfileService,
                                           $sessionStorage, FileService, TutoringFactory,$route,$rootScope) {

      $scope.headingTitle = 'Create Lesson';
      $scope.currentProfile =  TutoringFactory.getProfile();
      $scope.isStudent=false;
      if($scope.currentProfile.role.id ==  AppConstants.STUDENT_ROLE_ID) {
    	$scope.active= 'lessons';
      	$scope.isStudent=true;
  	  } else {
	        $scope.active='tutorhome';
	        // get all available lesson for profile not for student
	        LessonService.getAllAvailableLessons().then(function(response) {
	    	$scope.availableLessonList = response.data.data;
	        }).catch(function (error) {
			   alert(error.data.message);
	        }).finally(function () {
	        });
      }

      // get all lesson for profile
      LessonService.getAllLessons().then(function(response) {
    	    $scope.lessonList = response.data.data;
	   }).catch(function (error) {
		   alert(error.data.message);
	   }).finally(function () {
	   });



      // fetch all subjects from lesson
      if(TutoringFactory.getSubjectList()){
    	  $scope.subjectsList = TutoringFactory.getSubjectList();
      } else {
	       LessonService.getLessonSubjects().then(function(response) {
	    	   $scope.subjectsList = response.data.data;
	           TutoringFactory.setSubjectList($scope.subjectsList);
	       }).catch(function (error) {
	    	   alert(error.data.message);
	       }).finally(function () {
	       });
      }
      
      // fetch all lesson status
      if(!TutoringFactory.getLessonStatus()){
    	  LessonService.getLessonStatus().then(function(response) {
    		 TutoringFactory.setLessonStatus(response.data.data);
          	 $route.reload();
	       }).catch(function (error) {
	    	   alert(error.data.message);
	       }).finally(function () {
	       });
      }

      $scope.lesson={
        'subjectID': '',
        'title': '',
        'taskDescription': '',
        'specialRequirement': ''
      };
        
      $scope.createLesson = function(lessonForm) {
        if(lessonForm.$valid){
           LessonService.createLesson($scope.lesson).then(function(response) {
	    		  $scope.lessonList = response.data.data;
                  alert("created");
           }).catch(function (error) {
        	   alert(error.data.message);
           }).finally(function () {
           });
        }else{
        	alert('Enter all fields');
        }
      }
      

      $scope.openLesson = function(lessonId, lessonStatus)
      {
        LessonService.getLesson(lessonId).then(function(response) {
        	 TutoringFactory.setLesson(response.data.data);
             if(lessonStatus == 1) {
               $location.path('lessontalks');
             } else {
               $location.path('lessondetail');
             }
    	   }).catch(function (error) {
    		   alert(error.data.message);
    	   }).finally(function () {
    	   });
      };
      
      $scope.setActiveTab = function(tabName) {
        $scope.active=tabName;
        if(tabName== 'newLesson'){
          deleteTemporaryFilesOnServer();
        }

      };

     function deleteTemporaryFilesOnServer(){
    	  FileService.deleteTempUserFiles().then(function(response) {
    		  console.log('Files deleted');
 	       }).catch(function (error) {
 	    	  console.log('Error at Files deleted');
 	       }).finally(function () {
 	       });
    	  
      }

      $scope.routeToPage = function(routeUrl){
        $location.path(routeUrl);
      }
      
    });