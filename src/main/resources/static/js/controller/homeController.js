angular.module('homeController', ['factories', 'services'])
        .controller('homeController', function ($scope, LessonService, AppConstants, $location, ProfileService,
                $sessionStorage, FileService, TutoringFactory, $route, $rootScope, AppFactory) {

        	var pendingLesson = AppConstants.LESSON_AVAILABLE + AppConstants.LESSON_ACCEPTED +
        		AppConstants.LESSON_IN_PROGRESS + AppConstants.LESSON_WAITING_PAYMENT + 
        		AppConstants.LESSON_PAYMENT_MADE;
        		
        	var lessonHistory = AppConstants.LESSON_SUBMITTED + AppConstants.LESSON_COMPLETED;
        	
            $scope.headingTitle = 'Create Lesson';
            $scope.currentProfile = TutoringFactory.getProfile();
            $scope.isStudent = false;
            if ($scope.currentProfile.role.id == AppConstants.STUDENT_ROLE_ID) {
                $scope.active = 'pendingLesson';
                $scope.isStudent = true;
            } else {
                $scope.active = 'tutorhome';
                // get all available lesson for profile not for student
                LessonService.getAllAvailableLessons().then(function (response) {
                    $scope.availableLessonList = response.data.data;
                }).catch(function (error) {
                    alert(error.data.message);
                }).finally(function () {
                });
            }

            // get all lesson for profile for lesson history includes lesson submitted , completed
            LessonService.getLessonByStatus(lessonHistory).then(function (response) {
                $scope.lessonList = response.data.data;
            }).catch(function (error) {
                alert(error.data.message);
            }).finally(function () {
            });
            
            // get lesson by status for pending lesson
            
            LessonService.getLessonByStatus(pendingLesson).then(function (response) {
                $scope.pendingLessonList = response.data.data;
            }).catch(function (error) {
                alert(error.data.message);
            }).finally(function () {
            });

            // fetch all subjects from lesson
            if (TutoringFactory.getSubjectList()) {
                $scope.subjectsList = TutoringFactory.getSubjectList();
            } else {
                LessonService.getLessonSubjects().then(function (response) {
                    $scope.subjectsList = response.data.data;
                    TutoringFactory.setSubjectList($scope.subjectsList);
                }).catch(function (error) {
                    alert(error.data.message);
                }).finally(function () {
                });
            }

            // fetch all lesson status
            if (!TutoringFactory.getLessonStatus()) {
                LessonService.getLessonStatus().then(function (response) {
                    TutoringFactory.setLessonStatus(response.data.data);
                    $route.reload();
                }).catch(function (error) {
                    alert(error.data.message);
                }).finally(function () {
                });
            }

            $scope.lesson = {
                'subjectID': '',
                'title': '',
                'taskDescription': '',
                'specialRequirement': ''
            };
            $scope.newLessonProgress = false;


            $scope.createLesson = function (lessonForm) {
                if (lessonForm.$valid) {
                    $scope.newLessonProgress = true;
                    LessonService.createLesson($scope.lesson).then(function (response) {
                        $scope.lessonList = response.data.data;
                        AppFactory.toastSuccess(response.data.message);
                        $scope.active = 'lessons';
                        $('#lessonForm')[0].reset();
                        $scope.newLessonProgress = false;
                    }).catch(function (error) {
                        AppFactory.toastError(error.data.message);
                        $scope.newLessonProgress = false;
                    }).finally(function () {
                        $scope.newLessonProgress = false;
                    });
                } else {
                    AppFactory.toastError(AppConstants.MISSING_FIELD_ERROR);
                }
            }


            $scope.openLesson = function (lessonId, lessonStatus)
            {
                TutoringFactory.setLessonId(lessonId);
                if (lessonStatus == 1) {
                    $location.path('lessontalks');
                } else {
                    $location.path('lessondetail');
                }
            };

            $scope.setActiveTab = function (tabName) {
                $scope.active = tabName;
                if (tabName == 'newLesson') {
                    $scope.deleteTemporaryFilesOnServer();
                }

            };

            $scope.deleteTemporaryFilesOnServer = function () {
                FileService.deleteTempUserFiles().then(function (response) {
                    console.log('Files deleted');
                }).catch(function (error) {
                    console.log('Error at Files deleted');
                }).finally(function () {
                });

            }

            $scope.routeToPage = function (routeUrl) {
                $location.path(routeUrl);
            }

        });