angular.module('lessonController', ['factories','services','chatServices'])
		.controller('lessonController', function($scope, LessonService, AppConstants,FileService,$location,
												 AppFactory, $sessionStorage, ChatServices, TutoringFactory) {
        $scope.lessonAvailableStatus = AppConstants.LESSON_AVAILABLE;
        $scope.lessonExpiredStatus = AppConstants.LESSON_EXPIRED;
        LessonService.getLesson(TutoringFactory.getLessonId()).then(function(response) {
        $scope.lesson = response.data.data;
        $scope.isStatusUpdateAllowed = false;
        if($scope.lesson.status.id == AppConstants.LESSON_ACCEPTED ||
            $scope.lesson.status.id == AppConstants.LESSON_IN_PROGRESS ||
            $scope.lesson.status.id == AppConstants.LESSON_WAITING_PAYMENT ||
            $scope.lesson.status.id == AppConstants.LESSON_SUBMITTED) {
                 $scope.isStatusUpdateAllowed = true;
                 $scope.deleteTemporaryFilesOnServer();
        }
        
        if($scope.lesson.status.id == AppConstants.LESSON_SUBMITTED || $scope.lesson.status.id== AppConstants.LESSON_COMPLETED) {
            $scope.showAnswerFromTutor = true;
        }
        $scope.lessonUpdate={
			"id": $scope.lesson.id,
			"status":{
				"id":"",
			},
			"dueAmount":"",
			"estimatedWorkEffort":"",
			"lessonAnswerDesc":""
		};

		$scope.message={
			"lesson":{
				"id": $scope.lesson.id
			},
			"description":"",
			"currentProfile": TutoringFactory.getProfile().id
		};
		
        $scope.lessonStatusList =  TutoringFactory.getFilterLessonStatusList($scope.lesson.status.id);
        
        $scope.getAllMessagesForLesson();
        }).catch(function (error) {
            if(error.status == 406){
                 $location.path('home');
             }
     	   AppFactory.toastError(error.data.message);
        }).finally(function () {
        });
        
        $scope.maxFilesize=5;
        $scope.showAnswerFillSection=false;
        $scope.showAnswerFromTutor=false;
		$scope.currentProfile =  TutoringFactory.getProfile();
		$scope.isStudent=false;
		if($scope.currentProfile.role.id ==  AppConstants.STUDENT_ROLE_ID) {
			$scope.isStudent=true;
		}
		

		$scope.getAllMessagesForLesson = function(){
            LessonService.getAllMessagesForLesson($scope.lesson.id).then(function (response) {
            if(response.data.status == AppConstants.API_SUCCESS) {
                $scope.messageList = response.data.data;
            } else {
                AppFactory.toastSuccess(response.data.message);
            }
            }).catch(function (error) {
				   AppFactory.toastError(error.data.message);
			   }).finally(function () {
			});
		};

		$scope.addMessage = function() {
			if($scope.message) {
				ChatServices.send($scope.message);
				$scope.message.description = "";
			}else{
				AppFactory.toastError('Please add a message to sent.');
			}
		};

		ChatServices.receive().then(null, null, function(message) {
			var responseMessage = JSON.parse(message);
			if(responseMessage.status == AppConstants.API_SUCCESS) {
				if(responseMessage.data.lesson.id == $scope.lesson.id){
					if(responseMessage.data.lesson.status.id == AppConstants.LESSON_AVAILABLE || (responseMessage.data.receiverProfile.id 
						== TutoringFactory.getProfile().id) || (responseMessage.data.senderProfile.id 
						== TutoringFactory.getProfile().id))
					 $scope.messageList.push(responseMessage.data);
				} else {
					AppFactory.toastError('I am not for you.');
				}
			} else {
				AppFactory.toastError(responseMessage.message);
			}
		});
		
		$scope.openSelectFileWindow = function() {
			$('#selectFile').click();
		};

		$("#selectFile").change(function(){
			setTimeout(function(){
				if($scope.myFile) {
					if($scope.myFile.name.indexOf('.exe')>-1){
						AppFactory.toastError('Such file types are not allowed.');
					}else if($scope.myFile.size==0){
						AppFactory.toastError('File seems to be of 0KB, please upload a valid file.');
					}else if($scope.myFile.size>$scope.maxFilesize * 1024 * 1024){
						AppFactory.toastError('Please add a file less than 5 MB.');
					}else{
						FileService.uploadFile($scope.myFile, $scope.lesson.id).then(function(response) {
							//AppFactory.toastSuccess(response.data.message);
							console.log(response.data.message);
					   }).catch(function (error) {
						   AppFactory.toastError(error.data.message);
					   }).finally(function () {
					   });
					}
				} 
			}, 200);
			
		});

		$scope.updateStatus = function(lessonStatus){
			if(lessonStatus) {
				if(lessonStatus ==  AppConstants.LESSON_ACCEPTED && !$scope.lessonUpdate.dueAmount){
					AppFactory.toastError("Please enter price for the lesson.");
				} else if(lessonStatus==AppConstants.LESSON_SUBMITTED && !$scope.lessonUpdate.lessonAnswerDesc){
					AppFactory.toastError("Please enter the answer description.");
				} else {
				$scope.lessonUpdate.status.id = lessonStatus;
				LessonService.updateLessonStatus($scope.lessonUpdate).then(function(response) {
					$scope.message = response.data.data;
					AppFactory.toastSuccess(response.data.message);
                    $location.path('home');
					}).catch(function (error) {
						AppFactory.toastError(error.data.message);
						if(error.data.status == 406) {
							$location.path('home');
						}
					}).finally(function () {
					});
				}
			}else {
				AppFactory.toastError('Please select valid status.');
			}
		};

		$scope.checkSubmittedStatus = function (currentSelectedLessonId) {
			console.log("currentSelectedLessonId:"+currentSelectedLessonId);
			if(currentSelectedLessonId==AppConstants.LESSON_SUBMITTED){
				$scope.showAnswerFillSection = true;
			}
		}

		$scope.deleteTemporaryFilesOnServer = function(){
            	  FileService.deleteTempUserFiles().then(function(response) {
            		  console.log('Files deleted');
         	       }).catch(function (error) {
         	    	  console.log('Error at Files deleted');
         	       }).finally(function () {
         	       });
        }

});