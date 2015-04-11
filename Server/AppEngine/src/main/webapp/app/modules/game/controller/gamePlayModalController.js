var deviceModal = angular.module('gamePlayModalController', []);

deviceModal.factory('GamePlayModalController', function() {

	function GamePlayModalController($scope, $modalInstance) {
		$scope.title = "Play game at"
		$scope.model = {
			minute : 24,
			second : 22
		};

		$scope.cancel = function() {
			$modalInstance.dismiss();
		}

		$scope.confirm = function() {
			if ($("#modalForm").valid()) {
				gapi.client.gameApi.gameEndpointApi.startGame({
					'minute' : $scope.model.minute,
					'second' : $scope.model.second
				}).execute(function(result) {
					$modalInstance.close();
				});
			}
		}

	}

	return (GamePlayModalController);
});
