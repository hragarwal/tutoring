angular.module('lessonController', ['factories','services'])
    .controller('lessonController', function($scope, LessonService, AppConstants, $sessionStorage) {
    	
    		$scope.lesson = $sessionStorage.lesson;    		
    		//delete $sessionStorage.lesson;
    		
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
    		
    		
    		$scope.postMessage = function(){
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
    			
    			
    		}
    });