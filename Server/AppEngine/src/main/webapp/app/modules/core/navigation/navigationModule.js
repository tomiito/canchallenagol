var module = angular.module('navigationModule', []);

module.directive('navigation', function() {
	return {
		replace : true,
		restrict : 'E',
		templateUrl : "app/modules/core/navigation/views/navigation.html"
	};
});

module.directive('topNav', function() {
	return {
		replace : true,
		restrict : 'E',
		templateUrl : "app/modules/core/navigation/views/top.html"
	};
});

module.directive('sideNav', function() {
	return {
		replace : true,
		restrict : 'E',
		templateUrl : "app/modules/core/navigation/views/side.html",
		link : function($scope, element, attrs) {
			$('#side-menu').metisMenu();
		}
	};
});