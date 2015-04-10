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
				message.sending = true;
				gapi.client.gameApi.gameEndpointApi.pushMessage({
					'gameId' : $scope.commentary.gameId,
					'messageId' : message.messageId
				}).execute(function(result) {
					message.sending = false;
					$scope.$apply();
				});
			}

			$scope.start = function() {
				$scope.loading = false;
				$scope.listGames();
			};

			$scope.start();
		}]);