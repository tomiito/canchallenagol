var module = angular.module('modalModule', []);

/** MODAL WIDGETS **/
module.directive('modal', function() {
	return {
		transclude : true,
		restrict : 'E',
		templateUrl : "app/modules/core/modal/views/modal.html",
		scope : {
			title : '@',
			clazz : '@'
		}
	};
});