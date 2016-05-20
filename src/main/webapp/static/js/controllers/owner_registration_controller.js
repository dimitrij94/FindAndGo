/**
 * Created by Dmitrij on 07.02.2016.
 */
angular.module("OwnerRegistrationController", ["OwnerFactory", "DomainService", "ngMessages"])
    .controller("ownerRegistrationController", ["$scope", "Owner", "$http", "domain", "breadcrumbsService",
        function ($scope, Owner, $http, domain, breadcrumbsService) {

            updateBreadcrumbs();

            $scope.nameIsValid = false;



            $scope.validateOwnerEmail = function (email) {
                $http.get(domain.address + "/owner/email", {email: email}).then(
                    function (responce) {
                        $scope.nameIsValid = responce;
                    });

            };

            $scope.registerOwner = function (owner) {
                Owner.save(owner,
                    function (result) {

                    }, function () {

                    }
                )
            };

            function updateBreadcrumbs() {
                $scope.$applyAsync(function () {
                    breadcrumbsService.updateBreadcrumbs(
                        [{title: "Home", link: "KeyStyle"},
                            {title: "New owner", link: "KeyStyle.owner.registration"}]);
                });
            }
        }]);