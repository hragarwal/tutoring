var app = angular.module('app', ['ngRoute','ngResource','ngStorage','interceptorFactory',
                                'loginController','registerController','homePageController','openLessonController',
                                'factories','services']);
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
        
        .when('/home',{
            templateUrl: '../views/home.html',
            controller: 'homePageController'
        })
        .when('/lessondetail',{
            templateUrl: '../views/lessondetail.html',
            controller: 'openLessonController'
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

