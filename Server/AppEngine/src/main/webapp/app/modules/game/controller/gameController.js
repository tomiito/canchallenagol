var controller = angular.module('gameController', []);

controller.controller('GameController', [
		'$scope',
		'$modal',
		'$growl',
		'GamePlayModalController',
		function($scope, $modal, $growl, GamePlayModalController) {

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

			$scope.showPlaySuccessMessage = function() {
				$growl.success("GAME.PLAY.SUCCESS.TITLE",
						"GAME.PLAY.SUCCESS.BODY", {});
			}

			$scope.playGame = function() {
				$scope.openModalForPlayGame();
			}

			$scope.showStopSuccessMessage = function() {
				$growl.success("GAME.STOP.SUCCESS.TITLE",
						"GAME.STOP.SUCCESS.BODY", {});
			}

			$scope.stopGame = function() {

				gapi.client.gameApi.gameEndpointApi.stopGame({}).execute(
						function(result) {
							$scope.showStopSuccessMessage();
						});
			}

			$scope.showCreateDummySuccessMessage = function() {
				$growl.success("GAME.DUMMIES.SUCCESS.TITLE",
						"GAME.DUMMIES.SUCCESS.BODY", {});
			}

			$scope.createDummyGames = function() {

				gapi.client.gameApi.gameEndpointApi.createDummyGames({})
						.execute(function(result) {
							$scope.showCreateDummySuccessMessage();
						});
			}

			$scope.showParseSuccessMessage = function() {
				$growl.success("GAME.PARSE.SUCCESS.TITLE",
						"GAME.PARSE.SUCCESS.BODY", {});
			}

			$scope.parseGame = function() {
				gapi.client.gameApi.gameEndpointApi.parseGame({}).execute(
						function(result) {
							$scope.showParseSuccessMessage();
						});
			}

			$scope.showFindSuccessMessage = function() {
				$growl.success("GAME.FIND.SUCCESS.TITLE",
						"GAME.FIND.SUCCESS.BODY", {});
			}

			$scope.findUrls = function() {
				gapi.client.gameApi.gameEndpointApi.findUrls({
					'gameId' : $scope.commentary.gameId
				}).execute(function(result) {
					$scope.showFindSuccessMessage();
				});
			}

			$scope.openModalForPlayGame = function() {
				var modalInstance = $modal.open({
					templateUrl : //
					'app/modules/game/views/gamePlayModal.html',
					controller : GamePlayModalController,
					resolve : {}
				});

				modalInstance.result.then(function() {
					$scope.showPlaySuccessMessage();
				}, function() {

				});
			}

			$scope.start = function() {
				$scope.loading = false;
				$scope.listGames();
			};

			$scope.start();
		}]);