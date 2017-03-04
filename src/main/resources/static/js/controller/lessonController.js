angular.module('lessonController', ['factories','services','chatServices'])
    .controller('lessonController', function($scope, LessonService, AppConstants,FileService,
																						 $sessionStorage, ChatServices, TutoringFactory) {
    	
    		$scope.lesson = $sessionStorage.lesson;    		
    		
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
    	    
    	    ChatServices.receive().then(null, null, function(message) {
    	      var responseMessage = JSON.parse(message);
    	      if(responseMessage.status == AppConstants.API_SUCCESS) {
    	    	  $scope.messageList.push(responseMessage.data);
    	      } else {
    	    	  alert(responseMessage.message);
    	      }
    	    });
    	    
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