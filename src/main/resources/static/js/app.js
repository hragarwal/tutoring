var app = angular.module('app', ['ngRoute','ngResource','ngStorage','interceptorFactory','fileAppDirectives',
                                'loginController','registerController','homeController','lessonController','indexController',
                                 'headerController','footerController', 'forgotPasswordController','changePasswordController','chatServices',
                                'supportController','editProfileController','factories','services','fileModalDirective', 'ui.materialize', 'customFactory']);

app.config(function($routeProvider,$httpProvider){
    $httpProvider.interceptors.push('httpInterceptor');
    $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.cache = false;
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
        .when('/lessontalks',{
            templateUrl: '../views/lessontalks.html',
            controller: 'lessonController'
        })
        .when('/support',{
            templateUrl: '../views/support.html',
            controller: 'SupportController'
        })
        .otherwise(
            { redirectTo: '/landing', templateUrl: '../views/landing.html'}
        );
})

.run(function($rootScope,$location){
    $rootScope.$on('unauthorized', function() {
        console.log("user is not authorized");
        $location.path('login');
        $rootScope.isLoggedIn=false;
    });
});



