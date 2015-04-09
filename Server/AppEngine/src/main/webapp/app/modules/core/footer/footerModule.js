var module = angular.module('footerModule', []);

module.directive('appFooter', function() {
	return {
		replace : true,
		restrict : 'E',
		templateUrl : "app/modules/core/footer/views/footer.html",
		scope : {
			appVersion : "@"
		},
		link : function($scope, element, attrs) {
			$scope.version = attrs.appVersion;
		}
	};
});