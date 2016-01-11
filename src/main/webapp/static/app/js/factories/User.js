/**
 * Created by Dmitrij on 06.01.2016.
 */
angular.module['UserF']
    .factory('User', ['$resource', function ($resource) {
        var domain = 'http://127.0.0.1';
        return $resource(domain + '/user/:id',
            {},
            {
                get: {
                    method: 'GET',
                    cache: true
                }
            },
            {}
        )
    }]);