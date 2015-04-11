var module = angular.module('configModule', ['configController']);

module.provider('configURL', [function() {
	return {
		provideUrl : function(routeProvider) {
			return routeProvider.when('/config/', {
				templateUrl : 'app/modules/config/views/config.html',
				controller : 'ConfigController',
				title : 'CONFIG.TITLE'
			});
		},
		$get : function() {
			return {}
		}
	}
}]);
