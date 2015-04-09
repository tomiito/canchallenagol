var growlModule = angular.module('growlModule', ['notifications']);

growlModule.factory('$growl', [
		'$notification',
		'$translate',
		function($notification, $translate) {
			return {
				info : function(title, content, opts) {
					$translate([title, content], opts).then(
							function(translations) {
								$notification.info(translations[title], // 
								translations[content], null);
							});
					return;
				},

				error : function(title, content, opts) {
					$translate([title, content], opts).then(
							function(translations) {
								$notification.error(translations[title], // 
								translations[content], null);
							});
				},

				success : function(title, content, opts) {
					$translate([title, content], opts).then(
							function(translations) {
								$notification.success(translations[title], // 
								translations[content], null);
							});
				},

				warning : function(title, content, opts) {
					$translate([title, content], opts).then(
							function(translations) {
								$notification.warning(translations[title], //
								translations[content], null);
							});
				}
			};
		}]);