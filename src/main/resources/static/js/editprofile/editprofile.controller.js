/**
 * Created by Charlie
 */
(function () {
'use strict';

    angular
        .module('app')
        .controller('editprofileController', editprofileController);

    editprofileController.$inject = ['User', '$http', '$state'];

    function editprofileController(User, $http, $state) {

        var vm = this;
        
        vm.signOut = function(){
        	User.cleanUser();
        	$state.go('index');
        }
        
        var user = User.getUser();
        
        vm.username = user.username;
        vm.photo = user.photo;
        vm.email = user.email;
        vm.idFacebook = user.idFacebook;
        vm.idGoogle = user.idGoogle;
        vm.idTweeter = user.idTweeter;

        vm.editProfile = function(){
        	var userEdited = {
        			"username" 	: vm.username,
        			"id"		: user.id,
        			"password"	: user.password,
        			"photo"		: vm.photo+"",
        			"email"		: vm.email+"",
        			"idFacebook": vm.idFacebook+"",
        			"idGoogle"	: vm.idGoogle+"",
        			"idTweeter"	: vm.idTweeter+""
        	}
        	$http.put("http://localhost:8989/users/"+userEdited.id, userEdited)
        		.then(function(response){
        			alert(response.data.message);
        			User.setUser(response.data.user);
        	})
        }
        
    }
})();
