angular.module('UserRegistrationCtrl', ['ngMaterial', 'ngFileUpload',
    'ngImgCrop', 'ngMessages'])
    .controller('URegistrationCtrl', ['$scope', 'Upload', '$timeout',
        function ($scope, Upload, $timeout) {

            $scope.showHints = true;
            $scope.userPassConf = '';
            $scope.activeTab = '';
            $scope.activatedTabs = {
                main: true,
                photo: false,
                confirm: false
            };

            $scope.submitForm = function () {
                $scope.showHints = false;
            };

            $scope.isActive = function (name) {
                return $scope.activeTab === name;
            };


            $scope.checkUserData = function (form) {

                if (!isEmpty(form.username.$error) && !isEmpty(form.email.$error) && !isEmpty(form.pass.$error)
                    && !isEmpty(form.passConf.$error)) {
                    console.log("there are still some errors");
                    $scope.showHints = false;
                }
                else {
                    console.log("no errors where found");
                    $scope.showHints = true;
                    $scope.activatedTabs.photo = true;
                    $scope.activeTab = "selectPhoto";
                }
            };

            $scope.upload = function (dataUrl, formValid) {
                if (formValid && formValid === true) {

                    Upload.upload({
                        url: 'http://localhost:8080/user/',
                        data: $scope.setPhotoAndReturnUser(Upload.dataUrltoBlob(dataUrl))
                    }).then(function (response) {
                        $timeout(function () {
                            $scope.result = response.data;
                        });
                    }, function (response) {
                        if (response.status > 0) $scope.errorMsg = response.status
                            + ': ' + response.data;
                        $scope.activatedTabs.confirm = true;
                        $scope.activeTab = "confirmEmail";

                    }, function (evt) {
                        $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
                    });
                }
                else $scope.showHints = false;
            };

            $scope.setPhotoAndReturnUser = function (photo) {
                $scope.user.photo.image = photo;
                return $scope.user;
            };

            $scope.isPasswordsEqual = function () {
                return $scope.user.userPass === $scope.userPassConf;
            };

            function isEmpty(obj) {

                // null and undefined are "empty"
                if (obj == null) return true;

                // Assume if it has a length property with a non-zero value
                // that that property is correct.
                if (obj.length > 0)    return false;
                if (obj.length === 0)  return true;

                // Otherwise, does it have any properties of its own?
                // Note that this doesn't handle
                // toString and valueOf enumeration bugs in IE < 9
                for (var key in obj) {
                    if (hasOwnProperty.call(obj, key)) return false;
                }

                return true;
            }

            $scope.user = {
                userEmail: '',
                userName: '',
                userPass: '',
                address: {
                    longitude:'',
                    latitude:'',
                    country: '',
                    city: '',
                    town: '',
                    district: ''
                },
                sex: '',
                age: 0,
                name: '',
                sname: '',
                photo:{
                    image:null
                }
            };
        }]);