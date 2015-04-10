var controller = angular.module('gameController', []);

controller.controller('GameController', [
		'$scope',
		'$modal',
		'$growl',
		'Page',
		function($scope, $modal, $growl, Page) {

			$scope.listGames = function() {
				$scope.loading = true;
				gapi.client.gameApi.gameEndpointApi.getGameCommentary()
						.execute(function(result) {
							$scope.commentary = result.data;
							$scope.loading = false;
							$scope.$apply();
						});
			}

			$scope.start = function() {
				$scope.loading = false;
			};

			$scope.start();
		}]);