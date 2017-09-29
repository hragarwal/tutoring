
angular.module('app').directive('header', function() {
    return {
      restrict: 'EA',
      templateUrl: '../views/header.html',
      controller: 'headerController as headerController'
    };
  });	
