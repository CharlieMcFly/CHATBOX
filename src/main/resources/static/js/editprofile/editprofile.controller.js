(function() {
	'use strict';

	angular.module('app').controller('editprofileController',
			editprofileController);

	editprofileController.$inject = [ 'User', '$http', '$state' ];

	function editprofileController(User, $http, $state) {

		var vm = this;

		var user = User.getUser();
		if (user == undefined){
			$state.go('login');
		}
		else {
			vm.username = user.username;
			vm.photo = user.photo;
			vm.email = user.email;

			vm.signOut = function() {
				User.cleanUser();
				$state.go('index');
			}

			vm.editProfile = function() {
				var userEdited = {
					"username" : vm.username,
					"id" : user.id,
					"password" : user.password,
					"photo" : vm.photo + "",
					"email" : vm.email + ""
				}
				$http.put("http://localhost:8989/users/" + userEdited.id,
						userEdited).then(function(response) {
					alert(response.data.message);
					User.setUser(response.data.user);
					var user = User.getUser();
					vm.username = user.username;
					vm.photo = user.photo;
					vm.email = user.email;
				})
			}
		}
	}
})();
