/**
 * Created by Dmitrij on 21.12.2015.
 */
var app = angular.module("KeyStyle", ['ngMaterial', 'ui.router',
    'HomeHeaderController', 'HomeContentCtrl', 'HomeSidenavCtrl','UserRegistrationCtrl'])

    .config(['$stateProvider', '$mdThemingProvider', function ($stateProvider, $mdThemingProvider) {
        $stateProvider

            .state('KeyStyle', {
                url: "",
                abstract: true
            })

            .state('KeyStyle.home', {
                url: '/',
                views: {
                    'header@': {
                        templateUrl: '/templates/home/header',
                        controller: 'HeaderCtrl'
                    },
                    'sidenav@': {
                        templateUrl: '/templates/home/sidenav',
                        controller: 'SidenavCtrl',
                        replace:true
                    },
                    'sidenavContent@KeyStyle.home': {
                        templateUrl: '/templates/home/sidenav-content',
                        controller:'SidenavCtrl'
                    }
                }
            })

            .state('KeyStyle.home.search', {
                url: 'search',
                views: {
                    'content@': {
                        templateUrl: '/templates/home/search',
                        controller: 'ContentCtrl'
                    }
                }
            })

            .state('KeyStyle.home.register', {
                url: 'register',
                views: {
                    'content@': {
                        templateUrl: '/templates/home/registration',
                        controller:'URegistrationCtrl'
                    }
                }
            });

        $mdThemingProvider
            .theme('default')
            .primaryPalette("blue")
            .accentPalette('cyan')
            .warnPalette('red');

        $mdThemingProvider.theme('docs-dark')
            .primaryPalette("blue")
            .accentPalette('deep-orange')
            .warnPalette('red')
            .dark();

        $mdThemingProvider.setDefaultTheme('default');
    }]
);
/*
 Available palettes: red, pink, purple, deep-purple, indigo, blue,
 light-blue, cyan, teal, green, light-green, lime, yellow, amber, orange,
 deep-orange, brown, grey, blue-grey
 */