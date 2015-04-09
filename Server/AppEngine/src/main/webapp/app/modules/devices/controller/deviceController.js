var controller = angular.module('deviceController', []);

controller.controller('DeviceController', [
		'$scope',
		'Page',
		'$modal',
		'$growl',
		'DeviceModalController',
		function($scope, Page, $modal, $growl, DeviceModalController) {

			$scope.listDevices = function() {
				$scope.loading = true;
				gapi.client.devicesApi.devicesEndpointApi.getDevices({
					'offset' : $scope.devicePage.offset,
					'size' : $scope.devicePage.size,
					'cursors' : $scope.devicePage.cursors
				}).execute(function(result) {
					$scope.devicePage.parseResult(result);
					$scope.loading = false;
					$scope.$apply();
				});
			}

			$scope.newDevice = function() {
				var device = {
					isNew : true
				};
				$scope.openModalForDevice(device);
			};

			$scope.deleteDevice = function(device) {
				device.deleting = true;
				gapi.client.devicesApi.devicesEndpointApi.deleteDevice({
					'deviceId' : device.deviceId
				}).execute(function(result) {
					$scope.showDeleteMessage(device);
					$scope.listDevices();
				});
			};

			$scope.showCreateMessage = function(device) {
				$growl.success("DEVICE.CREATE.SUCCESS.TITLE",
						"DEVICE.CREATE.SUCCESS.BODY", {
							id : device.deviceId
						});
			}

			$scope.showDeleteMessage = function(device) {
				$growl.success("DEVICE.DELETE.SUCCESS.TITLE",
						"DEVICE.DELETE.SUCCESS.BODY", {
							id : device.deviceId
						});
			}

			$scope.openModalForDevice = function(device) {
				var modalInstance = $modal.open({
					templateUrl : 'app/modules/devices/views/deviceModal.html',
					controller : DeviceModalController,
					resolve : {
						device : function() {
							return angular.copy(device);
						}
					}
				});

				modalInstance.result.then(function(device) {
					$scope.showCreateMessage(device);
					$scope.listDevices();
				}, function() {

				});
			}

			$scope.start = function() {
				$scope.loading = false;
				$scope.devicePage = new Page($scope.listDevices);
			};

			$scope.start();
		}]);