(function(){
    'use strict';

    angular
        .module('app')
        .config(appRouting);

    appRouting.$inject = ['$stateProvider', '$urlRouterProvider'];

    function appRouting($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/');

        $stateProvider
        	.state('index',{
        		url:'/',
        		templateUrl: 'index.html',
        		controller: 'mainController'
        	})
            .state('login', {
                url: '/login',
                templateUrl: '/js/login/login.html',
                controller : 'loginController'
            })
            .state('profile', {
                url: '/profile',
                controller : 'profileController',
                templateUrl: '/js/profile/profile.html'
            })
            .state('edit', {
                url: '/editprofile',
                controller : 'editprofileController',
                templateUrl: '/js/editprofile/editprofile.html'
            })
            .state('detailsprofile', {
                url: '/detailsprofile/:userid',
                controller : 'detailsprofileController',
                templateUrl: '/js/detailsprofile/detailsprofile.html'
            })
            .state('hashtag', {
                url: '/hashtag/:tag',
                controller : 'hashtagController',
                templateUrl: '/js/hashtag/hashtag.html'
            })
            ;
        
    }
})();