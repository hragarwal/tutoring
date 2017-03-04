angular.module('editProfileController', ['factories','services'])
    .controller('EditProfileController', function($scope, ProfileService, AppConstants, TutoringFactory) {
      console.log("Inside EditProfileController");
      var profileData = TutoringFactory.getProfile();
      $scope.profile={
        "id":profileData.id,
        "name":profileData.name,
        "email":profileData.email,
        "country":profileData.country,
        "contactNumber":profileData.contactNumber,
        "skypeId":profileData.skypeId
      };

      $scope.updateProfile = function(editProfileForm){
        console.log("update details:"+JSON.stringify($scope.profile.name));
        if(editProfileForm.$valid){
          ProfileService.updateProfile($scope.profile)
              .then(function successCallback(response) {
                if(response.data.status == AppConstants.API_SUCCESS) {
                  alert(response.data.message);
                  TutoringFactory.setProfile(response.data.data);
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