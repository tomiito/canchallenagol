var module = angular.module('deviceModule', ['deviceModalController',
		'deviceController']);

module.provider('deviceURL', [function() {
	return {
		provideUrl : function(routeProvider) {
			return routeProvider.when('/devices/', {
				templateUrl : 'app/modules/devices/views/devices.html',
				controller : 'DeviceController',
				title : 'DEVICES.TITLE'
			});
		},
		$get : function() {
			return {}
		}
	}
}]);
