/**
 * Created by Dmitrij on 07.02.2016.
 */
angular.module("CompareToDirective", []).directive("compareTo", [function () {
    return {
        require:"ngModel",
        scope: {otherModelValue: "=compareTo"},
        link:function(scope,elemnt,attr,ngModel){
            ngModel.$validators.compareTo = function(modelValue){
                return modelValue == scope.otherModelValue;
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    }
}]);