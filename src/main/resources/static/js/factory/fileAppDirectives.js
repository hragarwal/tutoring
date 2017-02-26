angular.module('fileAppDirectives', ['factories'])

.directive('dropzone', function(AppConstants) {
  return {
    restrict: 'C',
    link: function(scope, element, attrs) {

      var config = {
        url: AppConstants.HTTP_BASE_URL+'/file/upload',
        maxFilesize: 5,
        paramName: "uploadfile",
        maxThumbnailFilesize: 5,
        parallelUploads: 5,
        autoProcessQueue: true
      };

      var eventHandlers = {
        'addedfile': function(file) {
          scope.file = file;
          /*if (this.files[1]!=null) {
            this.removeFile(this.files[0]);
          }*/
          scope.$apply(function() {
            scope.fileAdded = true;
          });
        },

        'success': function (file, response) {
        }

      };

      dropzone = new Dropzone(element[0], config);

      angular.forEach(eventHandlers, function(handler, event) {
        dropzone.on(event, handler);
      });

      scope.processDropzone = function() {
        dropzone.processQueue();
      };

      scope.resetDropzone = function() {
        dropzone.removeAllFiles();
      }
    }
  }
});