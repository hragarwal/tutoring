angular.module('editProfileController', ['factories', 'services'])
        .controller('EditProfileController', function ($scope, ProfileService, AppConstants, TutoringFactory, AppFactory) {
            console.log("Inside EditProfileController");
            var profileData = TutoringFactory.getProfile();
            $scope.profileProgress = false;
            $scope.profile = {
                "id": profileData.id,
                "name": profileData.name,
                "email": profileData.email,
                "country": profileData.country,
                "contactNumber": profileData.contactNumber,
                "skypeId": profileData.skypeId
            };

            $scope.updateProfile = function (editProfileForm) {
                console.log("update details:" + JSON.stringify($scope.profile.name));
                if (editProfileForm.$valid) {
                    $scope.profileProgress = true;
                    ProfileService.updateProfile($scope.profile)
                            .then(function successCallback(response) {
                                if (response.data.status == AppConstants.API_SUCCESS) {
                                    alert(response.data.message);
                                    TutoringFactory.setProfile(response.data.data);
                                } else {
                                    alert(response.data.message);
                                }
                                $scope.profileProgress = false;
                            }, function errorCallback(response) {
                                console.error("There is a error..");
                                $scope.profileProgress = false;
                            });
                } else {
                    alert("Enter all fields");
                }
            }


            $scope.headingTitle = 'Change Password';
            $scope.passwordProgress = false;
            $scope.profilea = {
                'oldPassword': '',
                'password': '',
                'confirmPassword': ''
            };

            $scope.updatePassword = function (updatePasswordForm) {
                if (updatePasswordForm.$valid) {
                    $scope.passwordProgress = true;
                    if ($scope.profilea.password === $scope.profilea.confirmPassword) {
                        ProfileService.changePassword($scope.profilea).then(function (response) {
                            AppFactory.toastSuccess(response.data.message);
                            TutoringFactory.setProfile(response.data.data);
                            $('#updatePasswordForm')[0].reset();
                            $scope.passwordProgress = false;
                        }).catch(function (error) {
                            AppFactory.toastError(error.data.message);
                            $scope.passwordProgress = false;
                        }).finally(function () {
                            $scope.passwordProgress = false;
                        });
                    } else {
                        AppFactory.toastError("New password and confirm password doesn't match");
                        $scope.passwordProgress = false;
                    }
                } else {
                    AppFactory.toastError(AppConstants.MISSING_FIELD_ERROR);
                }
            };


        });