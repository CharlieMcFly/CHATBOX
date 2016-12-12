(function() {
	'use strict';

	angular.module('app').controller('hashtagController', hashtagController);

	hashtagController.$inject = [ '$state', '$stateParams', '$http', 'User' ];

	function hashtagController($state, $stateParams, $http, User) {

		var vm = this;

		var user = User.getUser();
		if (user == undefined)
			$state.go('login');
		else {
			var tag = $stateParams.tag;
			vm.tag = '#' + tag;

			$http.get("http://localhost:8989/messages/hashtag/" + tag).then(
					function(response) {
						vm.allMessages = response.data;
					});
		}
	}
})();
