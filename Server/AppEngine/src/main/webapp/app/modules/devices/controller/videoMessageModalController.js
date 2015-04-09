var videoMessageModal = angular.module('videoMessageModalController', []);

videoMessageModal.factory('VideoMessageModalController', function() {

	function VideoMessageModalController($scope, $modalInstance, videoMsg) {
		$scope.title = "Send new video message"
		$scope.videoMsg = videoMsg;

		$scope.cancel = function() {
			$modalInstance.dismiss();
		}

		$scope.confirm = function() {
			if ($("#modalForm").valid()) {
				gapi.client.messagesApi.messagesEndpointApi.videoSendAll({
					'message' : $scope.videoMsg.message, //
					'videoLink' : $scope.videoMsg.videoLink, //
					'gifLink' : $scope.videoMsg.gifLink, //
					'thumbnailLink' : $scope.videoMsg.thumbnailLink
				}).execute(function(result) {
					$modalInstance.close($scope.videoMsg);
				});

			}
		}

	}

	return (VideoMessageModalController);
});
