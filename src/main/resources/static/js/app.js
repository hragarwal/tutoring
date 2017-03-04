var app = angular.module('app', ['ngRoute','ngResource','ngStorage','interceptorFactory','fileAppDirectives',
                                'loginController','registerController','homeController','lessonController', 
                                 'forgotPasswordController','changePasswordController','chatServices',
                                'editProfileController','factories','services']);
app.config(function($routeProvider,$httpProvider){
    $httpProvider.interceptors.push('httpInterceptor');
    $routeProvider
        .when('/login',{
            templateUrl: '../views/login.html',
            controller: 'LoginController'
        })
        .when('/register',{
            templateUrl: '../views/register.html',
            controller: 'RegisterController'
        })

        .when('/forgotPassword',{
            templateUrl: '../views/forgotPassword.html',
            controller: 'ForgotPasswordController'
        })

        .when('/editProfile',{
            templateUrl: '../views/editProfile.html',
            controller: 'EditProfileController'
        })

        .when('/changePassword',{
            templateUrl: '../views/changePassword.html',
            controller: 'ChangePasswordController'
        })
        
        .when('/home',{
            templateUrl: '../views/home.html',
            controller: 'homeController'
        })
        .when('/lessondetail',{
            templateUrl: '../views/lessondetail.html',
            controller: 'lessonController'
        })
        .otherwise(
            { redirectTo: '/login'}
        );
})

.run(function($rootScope,$location){
    $rootScope.$on('unauthorized', function() {
        console.log("user is not authorized");
        $location.path('login');
    });
});

