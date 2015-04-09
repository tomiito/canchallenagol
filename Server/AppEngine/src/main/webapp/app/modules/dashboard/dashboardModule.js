var dashboardModule = angular
		.module('dashboardModule', ['dashboardController']);

dashboardModule.provider('dashboardURL', [function() {
	return {
		provideUrl : function(routeProvider) {
			return routeProvider.when('/', {
				templateUrl : 'app/modules/dashboard/view/dashboard.html',
				controller : 'DashboardController',
				title : 'DASHBOARD.TITLE'
			});
		},
		$get : function() {
			return {}
		}
	}
}]);
