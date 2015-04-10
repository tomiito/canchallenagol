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
							$scope.commentary = result;
							$scope.loading = false;
							$scope.$apply();
						});
			}

			$scope.sendMessage = function(message) {
				gapi.client.gameApi.gameEndpointApi.pushMessage({
					'messageId' : message.messageId
				}).execute(function(result) {
					$scope.commentary = result;
					$scope.loading = false;
					$scope.$apply();
				});
			}

			$scope.start = function() {
				$scope.loading = false;
				$scope.listGames();
			};

			$scope.start();
		}]);