/**
 * Created by Charlie
 */
(function(){
    'use strict';
    angular
        .module('app')
        .service('User', User);

    User.$injection = [];

    function User(){

        var user = {};


        this.getUser = function(){
        	return this.user;
        }

        this.setUser = function(userAuth){
        	this.user = userAuth;
            return this.user;
        }

        this.cleanUser = function(){
            this.user = {};
            return this.user;
        }

    }

})();
