/**
 * Created by Charlie
 */
(function() {
	'use strict';

	angular.module('app').controller('hashtagController', hashtagController);

	hashtagController.$inject = [ '$state', '$stateParams', '$http' ];

	function hashtagController($state, $stateParams, $http) {

		var vm = this;
		var tag = $stateParams.tag;
		vm.tag = '#'+ tag;
	
		

		$http.get("http://localhost:8989/messages/hashtag/" + tag).then(
				function(response) {
					vm.allMessages = response.data;
				});

	}
})();
