angular.module('lessonController', ['factories','services','chatServices'])
		.controller('lessonController', function($scope, LessonService, AppConstants,FileService,$location,
																						 AppFactory, $sessionStorage, ChatServices, TutoringFactory) {
            LessonService.getLesson(TutoringFactory.getLessonId()).then(function(response) {
             $scope.lesson = response.data.data;
             if($scope.lesson.status.id==AppConstants.LESSON_SUBMITTED ||
                    $scope.lesson.status.id==AppConstants.LESSON_COMPLETED){
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
             					"id":$scope.lesson.id
             				},
             				"description":"",
             				"currentProfile": TutoringFactory.getProfile().id
             			};
             $scope.getAllMessagesForLesson();
           }).catch(function (error) {
                if(error.status == 406){
                     $location.path('home');
                 }
                 alert(error.data.message);
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
                LessonService.getAllMessagesForLesson($scope.lesson.id)
                        .then(function successCallback(response) {
                            if(response.data.status == AppConstants.API_SUCCESS) {
                                $scope.messageList = response.data.data;
                            } else {
                                alert(response.data.message);
                            }
                        }, function errorCallback(response) {
                            console.error("There is a error..");
                        });
			}

			$scope.lessonStatusList =  TutoringFactory.getLessonStatus();

			$scope.addMessage = function() {
				if($scope.message) {
					ChatServices.send($scope.message);
					$scope.message.description = "";
				}else{
					alert("Please add a message to sent");
				}
			};

			$scope.lessonFilterExpression = function(lesson) {
				return (lesson.id> $scope.lesson.id );
			};

			ChatServices.receive().then(null, null, function(message) {
				var responseMessage = JSON.parse(message);
				if(responseMessage.status == AppConstants.API_SUCCESS) {
					if(responseMessage.data.lesson.id == $scope.lesson.id){
						if(responseMessage.data.lesson.status.id == 1 || (responseMessage.data.receiverProfile.id 
							== TutoringFactory.getProfile().id) || (responseMessage.data.senderProfile.id 
							== TutoringFactory.getProfile().id))
						 $scope.messageList.push(responseMessage.data);
					} else {
						//alert('i am not for you');
					}
				} else {
					alert(responseMessage.message);
				}
			});

			/*$scope.uploadFile = function() {
					alert('YOYO');
					if(!$scope.myFile){
						alert("Please add a file");
					}else if($scope.myFile.name.indexOf(".exe")>-1){
						alert("Such file types are not allowed");
					}else if($scope.myFile.size==0){
						alert("File seems to be of 0KB, please upload a valid file.");
					}else if($scope.myFile.size>$scope.maxFilesize * 1024 * 1024){
						alert("Please add a file less than 5 MB");
					}else{
						FileService.uploadFile($scope.myFile, $scope.lesson.id)
								.then(function successCallback(response) {
									if(response.data.status!=AppConstants.API_SUCCESS){
										alert(response.data.message);
									}else{
										console.log("File uploaded successfully.");
									}
								}, function errorCallback(response) {
									console.error("There is a error..");
								});
					}
				
			};*/
			

			function uploadFile(){
				alert('YOYO');
			};
			
			
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
								AppFactory.toastSuccess(response.data.message);
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
						alert("Please enter price for the lesson");
					}else if(lessonStatus==AppConstants.LESSON_SUBMITTED && !$scope.lessonUpdate.lessonAnswerDesc){
						alert("Please enter the lesson answer description");
					}else {
						$scope.lessonUpdate.status.id = lessonStatus;
						LessonService.updateLessonStatus($scope.lessonUpdate)
								.then(function successCallback(response) {
									if (response.data.status == AppConstants.API_SUCCESS) {
										$scope.message = response.data.data;
										alert(response.data.message);
										$location.path('home');
									} else {
										alert(response.data.message);
									}
								}, function errorCallback(response) {
									console.error("There is a error..");
								});
					}
				}else {
					alert('Please select valid status.');
				}
			}

			$scope.checkSubmittedStatus = function (currentSelectedLessonId) {
				console.log("currentSelectedLessonId:"+currentSelectedLessonId);
				if(currentSelectedLessonId==AppConstants.LESSON_SUBMITTED){
					$scope.showAnswerFillSection = true;
				}
			}

			/*$scope.postMessage = function(){
			 LessonService.postMyMessage($scope.userMessage, $scope.lesson.id)
			 .then(function successCallback(response) {
			 if(response.data.status == AppConstants.API_SUCCESS) {
			 $scope.message = response.data.data;

			 alert("Message Posted Successfully");
			 $scope.userMessage = null;
			 $scope.messageList.push($scope.message);
			 } else {
			 alert(response.data.message);
			 }
			 }, function errorCallback(response) {
			 console.error("There is a error..");
			 });
			 }*/
		});