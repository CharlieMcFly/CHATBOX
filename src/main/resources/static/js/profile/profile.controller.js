/**
 * Created by Charlie
 */
(function () {
'use strict';

    angular
        .module('app')
        .controller('profileController', profileController);

    profileController.$inject = ['User', '$state', '$http'];

    function profileController(User, $state, $http) {

        var vm = this;
        
        if(User.getUser() === undefined)
        	$state.go('login');
        
        
        vm.username = User.getUser().username;
        
        $http.get("http://localhost:8989/messages").then(function(response){
        	vm.allMessages = response.data.messages;
        });
        
        
        vm.signOut = function(){
        	User.cleanUser();
        	$state.go('index');
        }
        
        vm.sayMessage = function(){
        	var message = {
        		"iduser" : User.getUser().id,
        		"content" : vm.content
        	}
        	
        	$http.post("http://localhost:8989/messages", message).then(function(response){
        		vm.content = "";
        		vm.allMessages=response.data.messages;
        	})
        }
       
    

    }
})();
