/**
 * Created by Dmitrij on 28.12.2015.
 */
angular.module('HomeHeaderController', ['ngMaterial'])
    .controller("HeaderCtrl", ['$scope', '$mdSidenav','$state','$timeout',
        function ($scope, $mdSidenav,$state, $timeout) {
            $scope.$state = $state;
            $scope.toggleLeft = buildDelayedToggler('left');

            $scope.close = function () {
                $mdSidenav('left').close();
            };

            $scope.openMenu = function ($mdOpenMenu, event) {
                originatorEv = event;
                $mdOpenMenu(event);
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