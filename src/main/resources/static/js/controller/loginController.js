angular.module('loginController', ['factories', 'services'])
        .controller('LoginController', function ($scope, LoginService, AppConstants, $location, TutoringFactory, $rootScope, AppFactory) {
            $scope.headingTitle = "Login";
            $scope.loginProgress = false;
            $scope.login = {
                'email': '',
                'password': ''
            };

            $scope.validateUser = function (loginForm) {
                if (loginForm.$valid) {
                    $scope.loginProgress = true;
                    LoginService.validateUser($scope.login).then(function (response) {
                        TutoringFactory.setProfile(response.data.data);
                        TutoringFactory.setAuthorized(true);
                        $rootScope.isLoggedIn = true;
                        $rootScope.loggedInUsername = response.data.data.name;
                        $location.path('home');
                    }).catch(function (error) {
                        AppFactory.toastError(error.data.message);
                        $scope.loginProgress = false;
                    }).finally(function () {
                    });
                } else {
                    AppFactory.toastError(AppConstants.MISSING_FIELD_ERROR);
                }
            };

        });