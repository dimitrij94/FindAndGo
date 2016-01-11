/**
 * Created by Dmitrij on 21.12.2015.
 */
var app = angular.module("HomeContentCtrl", ['ngMaterial'])
    .controller("ContentCtrl", ["$scope", '$q', "$http", '$timeout',
        function ($scope, $q, $http, $timeout) {

            $scope.queryDisabled = true;

           // $scope.gmapsService = new google.maps.places.AutocompleteService();

            $scope.toggleQueryState = function () {
                $scope.queryDisabled = !$scope.queryDisabled;
            };

            function searchAddress(address) {

                var deferred = $q.defer();
                getResults(address).then(
                    function (predictions) {
                        var results = [];
                        for (var i = 0, prediction; prediction = predictions[i]; i++) {
                            results.push(prediction.description);
                        }
                        deferred.resolve(results);
                    }
                );
                return deferred.promise;
            }

            function getResults(address) {
                var deferred = $q.defer();
                $scope.gmapsService.getQueryPredictions({input: address}, function (data) {
                    deferred.resolve(data);
                });
                return deferred.promise;
            }


            $scope.search = {
                query: "",
                location: "",
                type: ""
            };

            $scope.searchTypes = [
                {
                    label: "Employees",
                    value: "employee"
                },
                {
                    label: "Places",
                    value: "place",
                },
                {
                    label: "Services",
                    value: "service",
                }];
        }
    ]);


