angular.module('lessonController', ['factories','services','chatServices'])
		.controller('lessonController', function($scope, LessonService, AppConstants,FileService,
												$sessionStorage, ChatServices, TutoringFactory) {

			$scope.lesson = $sessionStorage.lesson;
			$scope.maxFilesize=5;
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
			
			$scope.currentProfile =  TutoringFactory.getProfile();
			$scope.lessonStausList =  TutoringFactory.getLessonStatus();
			
			$scope.lessonUpdate={
		  	        "id": $scope.lesson.id,
		  	        "status":{
						"id":"",
					},
		  	        "dueAmount":"",
		  	        "estimatedWorkEffort":"",
		    };
			
			$scope.message={
				"lesson":{
					"id":$scope.lesson.id
				},
				"description":"",
				"currentProfile": TutoringFactory.getProfile().id
			};

			$scope.addMessage = function() {
				ChatServices.send($scope.message);
				$scope.message.description = "";
			};
			
			  $scope.lessonFilterExpression = function(lesson) {
	                return (lesson.id> $scope.lesson.id );
	            };

			ChatServices.receive().then(null, null, function(message) {
				var responseMessage = JSON.parse(message);
				if(responseMessage.status == AppConstants.API_SUCCESS) {
					$scope.messageList.push(responseMessage.data);
				} else {
					alert(responseMessage.message);
				}
			});

			$scope.uploadFile = function(){
			console.log($scope.myFile);
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
			}

			
		$scope.updateStatus = function(lessonStatus){
			 $scope.lessonUpdate.status.id = lessonStatus;
			 LessonService.updateLessonStatus($scope.lessonUpdate)
			 .then(function successCallback(response) {
			 if(response.data.status == AppConstants.API_SUCCESS) {
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