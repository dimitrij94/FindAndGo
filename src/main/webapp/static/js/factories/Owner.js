/**
 * Created by Dmitrij on 07.02.2016.
 */
angular.module('OwnerFactory', ["ngResource", "DomainService"]).
    factory("Owner", ["$resource", "domain", function ($resource, domain) {
        return $resource(domain.address + "/owner/:id",
            {id: '@id'},
            {
                update: {method: "PUT"}
            }
        )
    }
    ])
    .factory("Place", ["$resource", "domain", function ($resource, domain) {
        return $resource(domain.address + "/place:id",
            {id: "@id"}, {}, {})
    }]);
    