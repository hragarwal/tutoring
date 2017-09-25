angular.module('homeController', ['factories','services'])
    .controller('homeController', function($scope, LessonService, AppConstants, $location,ProfileService,
                                           $sessionStorage, FileService, TutoringFactory,$route,$rootScope) {

      console.log("Inside lessonController");
      $scope.headingTitle = "Create Lesson";
      $scope.currentProfile =  TutoringFactory.getProfile();
      $scope.isStudent=false;
      if($scope.currentProfile.role.id ==  AppConstants.STUDENT_ROLE_ID) {
        $scope.active="lessons";
        $scope.isStudent=true;
      }else{
        $scope.active="tutorhome";
        // get all available lesson for profile not for student
        LessonService.getAllAvailableLessons()
            .then(function successCallback(response) {
              if(response.data.status == AppConstants.API_SUCCESS) {
                $scope.availableLessonList = response.data.data;
              } else {
                alert(response);
              }
            }, function errorCallback(response) {
              console.error("There is a error..");
            });
      }

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



      // fetch all subjects from lesson
      if(TutoringFactory.getSubjectList()){
        $scope.subjectsList = TutoringFactory.getSubjectList();
        
      }else {
        LessonService.getLessonSubjects()
            .then(function successCallback(response) {
              if (response.data.status == AppConstants.API_SUCCESS) {
                $scope.subjectsList = response.data.data;
                TutoringFactory.setSubjectList($scope.subjectsList);
              }
            }, function errorCallback(response) {
              console.error("There is a error..");
            });
      }
      // fetch all lesson status
      if(!TutoringFactory.getLessonStatus()){
        LessonService.getLessonStatus()
            .then(function successCallback(response) {
              if (response.data.status == AppConstants.API_SUCCESS) {
                TutoringFactory.setLessonStatus(response.data.data);
            	$route.reload();
              }
            }, function errorCallback(response) {
              console.error("There is a error..");
            });
      }

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

      $scope.openLesson = function(lessonId, lessonStatus)
      {
        LessonService.getLesson(lessonId)
            .then(function successCallback(response) {
              if(response.data.status == AppConstants.API_SUCCESS) {
                TutoringFactory.setLesson(response.data.data);
                if(lessonStatus == 1) {
                  $location.path('lessontalks');
                } else {
                  $location.path('lessondetail');
                }
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
          // $('#lesson_select').material_select();

          //   // $(".select-wrapper input.select-dropdown").css({"height": "40px", "border": "none"});
            
          //   $('#deadline-date').pickadate({
          //       selectMonths: true, // Creates a dropdown to control month
          //       selectYears: 15, // Creates a dropdown of 15 years to control year,
          //       today: 'Today',
          //       clear: 'Clear',
          //       close: 'Ok',
          //       closeOnSelect: false // Close upon selecting a date,
          //   });
          //   alert("Done");
          deleteTemporaryFilesOnServer();
          
        
            
            // $(".dropdown-content").css('top', 'initial');
            
       
        
    
        }

      };

      function deleteTemporaryFilesOnServer(){
        FileService.deleteTempUserFiles()
            .then(function successCallback(response) {
              console.log("Success, files deleted");
            }, function errorCallback(response) {
              console.error("There is a error..");
            });
      }

      $scope.routeToPage = function(routeUrl){
        $location.path(routeUrl);
      }

      $scope.logout = function(){
        TutoringFactory.clearSessionStorage();
        ProfileService.logoutUser()
            .then(function successCallback(response) {
              console.log("User successfully logged out");
              $location.path('login');
              $rootScope.isLoggedIn=false;
            }, function errorCallback(response) {
              console.error("There is a error..");
            });
      }
    });