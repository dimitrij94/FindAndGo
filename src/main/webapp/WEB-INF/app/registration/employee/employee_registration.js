/**
 * Created by Dmitrij on 22.12.2015.
 */
var app = angular.module("KeyStyle", []);

app.directive("timePicker", function () {
    var scroller = angular.element("<input type='text' />");
    this.link = function () {
        console.log("Element!");
    };
    return {
        restrict: "E",
        replace: true,
        compile: function (tElem) {
            tElem.append(scroller);
            return link;
        }
    }
});
app.controller("EmployeeRegistration", ["$scope", function ($scope) {
    $scope.descriptionError = false;
    $scope.descriptionLeft = 500;

    $scope.updateDescription = function () {
        var i = 500 - $scope.description.length;
        if (i >= 0) {
            $scope.descriptionError = false;
            $scope.descriptionLeft = i;
        }

        if (i < 0) {
            $scope.descriptionError = true;
            $scope.descriptionLeft = 0;
        }
    };
    $scope.descriptionHasError = function () {
        return $scope.descriptionError;
    };

    $scope.services = [
        {
            name: "massage",
            checked: false
        },
        {
            name: "haircut",
            checked: false
        },
        {
            name: "consult",
            checked: false
        }
    ]
}]);

app.directive("keyup", function () {
    return function (scope, element, attr) {
        element.bind("keyup", function () {
            scope.$apply(attr.keyup, [element.val().length]);
        })
    }
});
