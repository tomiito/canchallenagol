var controller = angular.module('configController', []);

controller.controller('ConfigController', ['$scope', '$modal', '$growl',
		function($scope, $modal, $growl) {

			$scope.start = function() {
				$scope.loading = false;
			};

			$scope.start();
		}]);