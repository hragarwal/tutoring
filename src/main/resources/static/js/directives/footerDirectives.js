
angular.module('app').directive('footer', function() {
    return {
      restrict: 'EA',
      templateUrl: '../views/footer.html',
      controller: 'footerController as footerController'
    };
  });	
