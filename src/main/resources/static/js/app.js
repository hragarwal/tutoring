var app = angular.module('app', ['ngRoute','ngResource',
                                'loginController','registerController','lessonController','homePageController',
                                'factories','services']);
app.config(function($routeProvider){
    $routeProvider
        .when('/login',{
            templateUrl: '../views/login.html',
            controller: 'LoginController'
        })
        .when('/register',{
            templateUrl: '../views/register.html',
            controller: 'RegisterController'
        })
        .when('/newlesson',{
            templateUrl: '../views/lesson.html',
            controller: 'LessonController'
        })
        .when('/homePage',{
            templateUrl: '../views/homePage.html',
            controller: 'homePageController'
        })
        .otherwise(
            { redirectTo: '/login'}
        );
});

