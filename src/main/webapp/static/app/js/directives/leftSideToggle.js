/**
 * Created by Dmitrij on 31.12.2015.
 */
angular.module("leftToggle", ['ngMaterial'])
    .directive('sidenavtoggle', ['$mdSidenav', '$timeout',
        function ($mdSidenav, $timeout) {

            return {
                restrict: "E",
                scope: {
                    navId: '@'
                },
                template: "<md-button><i class='fa fa-home'></i></md-button>",
                replace:true,
                link: function ($scope, elm, attr) {
                    elm.bind("click", function () {
                        buildDelayedToggler(navId);
                    });
                }
            };

            function buildDelayedToggler(navID) {
                return debounce(function () {
                    $mdSidenav(navID)
                        .toggle()
                        .then(function () {

                        });
                }, 200);
            }

            function debounce(func, wait, context) {
                var timer;
                return function debounced() {
                    var context = $scope,
                        args = Array.prototype.slice.call(arguments);

                    $timeout.cancel(timer);
                    timer = $timeout(function () {
                        timer = undefined;
                        func.apply(context, args);
                    }, wait || 10);
                };
            }

        }]);