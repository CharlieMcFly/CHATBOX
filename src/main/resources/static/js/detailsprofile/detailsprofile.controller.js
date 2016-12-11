/**
 * Created by Charlie
 */
(function() {
	'use strict';

	angular.module('app').controller('detailsprofileController',
			detailsprofileController);

	detailsprofileController.$inject = [ '$state', '$stateParams', '$http' , 'User'];

	function detailsprofileController($state, $stateParams, $http, User) {

		var vm = this;
		
		var iduser = $stateParams.userid;

		$http.get("http://localhost:8989/users/" + iduser).then(
				function(response) {
					var u = response.data;
					vm.username = u.username;
					vm.photo = u.photo;
					vm.email = u.email;
					vm.idFacebook = u.idFacebook;
					vm.idGoogle = u.idGoogle;
					vm.idTweeter = u.idTweeter;
				});

		$http.get("http://localhost:8989/messages/user/" + iduser).then(
				function(response) {
					vm.allMessages = response.data.messages;
				});

	}
})();
