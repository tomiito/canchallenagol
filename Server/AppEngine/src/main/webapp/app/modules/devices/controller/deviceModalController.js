var deviceModal = angular.module('deviceModalController', []);

deviceModal.factory('DeviceModalController', function() {

	function DeviceModalController($scope, $modalInstance, device) {
		$scope.device = device;
		$scope.title = "Register new device"

		$scope.cancel = function() {
			$modalInstance.dismiss();
		}

		$scope.confirm = function() {
			if ($("#modalForm").valid()) {
				gapi.client.devicesApi.devicesEndpointApi.createDevice(
						$scope.device).execute(function(result) {
					$modalInstance.close($scope.device);
				});

			}
		}

	}

	return (DeviceModalController);
});
