/**
 * Created by Charlie
 */
(function () {
'use strict';

    angular
        .module('app')
        .controller('loginController', loginController);

    loginController.$inject = ["$http", "User", "$state"];

    function loginController($http, User, $state) {

        var vm = this;
        
        vm.signIn = function(){
        	var user = {
        		"username" : vm.username,
        		"password" : vm.password
        		}
        	$http.post("http://localhost:8989/users/connexion", user)
        		.then(function(response){
        			User.setUser(response.data.user);
        			alert(response.data.message);
        			if(response.data.user != undefined)
        				$state.go('profile');
        		});
        }
    

    }
})();
