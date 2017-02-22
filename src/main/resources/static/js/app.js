var app = angular.module('app', ['ngRoute','ngResource',
                                'loginController','registerController',
                                'factories','services']);
app.config(function($routeProvider){
    $routeProvider
        .when('/login',{
            templateUrl: '/views/login.html',
            controller: 'LoginController'
        })
        .when('/register',{
            templateUrl: '/views/register.html',
            controller: 'RegisterController'
        })
        .otherwise(
            { redirectTo: '/login'}
        );
});

