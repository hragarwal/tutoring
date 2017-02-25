var app = angular.module('app', ['ngRoute','ngResource','interceptorFactory',
                                'loginController','registerController','lessonController','homePageController',
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
        .when('/newlesson',{
            templateUrl: '../views/lesson.html',
            controller: 'LessonController'
        })
        .when('/home',{
            templateUrl: '../views/home.html',
            controller: 'homePageController'
        })
        .otherwise(
            { redirectTo: '/login'}
        );
})

.run(function($rootScope){
    $rootScope.$on('unauthorized', function() {
        alert("user is not authorized");
        $location.path('login');
    });
});

