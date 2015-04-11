var module = angular.module('gameModule', ['gameController',
		'gamePlayModalController']);

module.provider('gameURL', [function() {
	return {
		provideUrl : function(routeProvider) {
			return routeProvider.when('/game/', {
				templateUrl : 'app/modules/game/views/game.html',
				controller : 'GameController',
				title : 'GAME.TITLE'
			});
		},
		$get : function() {
			return {}
		}
	}
}]);
