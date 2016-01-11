/**
 * Created by Dmitrij on 21.12.2015.
 */
angular.module("KeyStyle", []).controller("PlaceRegistration", ["$scope", function ($scope) {
    var newPlace = {name: "", description: "", file: new byte[0], type: "", x: 0, y: 0, h: 0, w: 0}
    $scope.createNewPlace = function(newPlace){

    };
    $scope.getDescriptionLen=function(){
        return newPlace.description.length;
    }
}]);