var app = angular.module('app', ['ngRoute', 'pascalprecht.translate',
		'ui.bootstrap', 'appModules'])

app.config(['$routeProvider', 'dashboardURLProvider', '$translateProvider',
		function($routeProvider, dashboardURLProvider, $translateProvider) {
			// /
			dashboardURLProvider.provideUrl($routeProvider);
			// other
			$routeProvider.otherwise({
				redirectTo : '/'
			});
			//
			// Translator
			$translateProvider.useStaticFilesLoader({
				prefix : '/app/i18n/locale-',
				suffix : '.json'
			});
			$translateProvider.preferredLanguage('en');

		}]);

app.run([
		"$rootScope",
		"$location",
		"$translate",
		"$templateCache",
		function($rootScope, $location, $translate, $templateCache) {
			$rootScope.user = {
				email : "test@ironsoft.com.ar"
			};
			console.log("Logged in as user: " + $rootScope.user.email);
			//
			$rootScope.$on('$routeChangeStart', function(event, next, current) {
				if (typeof (current) !== 'undefined') {
					$templateCache.remove(current.templateUrl);
				}
			});
			//
			$rootScope.$on("$routeChangeSuccess", function(event, currentRoute,
					previousRoute) {
				$translate(currentRoute.title).then(function(title) {
					$rootScope.title = title;
				});
			});
			//
			if (typeof String.prototype.startsWith != 'function') {
				// see below for better implementation!
				String.prototype.startsWith = function(str) {
					return this.indexOf(str) == 0;
				};

				String.prototype.isEmail = function() {
					var re = /\S+@\S+\.\S+/;
					return re.test(this);
				}

				/**
				 * ReplaceAll by Fagner Brack (MIT Licensed)
				 * Replaces all occurrences of a substring in a string
				 */
				String.prototype.replaceAll = function(token, newToken,
						ignoreCase) {
					var _token;
					var str = this + "";
					var i = -1;

					if (typeof token === "string") {

						if (ignoreCase) {

							_token = token.toLowerCase();

							while ((i = str.toLowerCase().indexOf(token,
									i >= 0 ? i + newToken.length : 0)) !== -1) {
								str = str.substring(0, i) + newToken
										+ str.substring(i + token.length);
							}

						} else {
							return this.split(token).join(newToken);
						}

					}
					return str;
				};
			}
		}]);

//Called by google endpoints
function init() {
	var MARROCCL = '//' + window.location.host + '/_ah/api';
	var apisToLoad;
	var callback = function() {
		if (--apisToLoad == 0) {
			//bootstrap manually angularjs after our api are loaded
			angular.bootstrap(document, ["app"]);
		}
	}
	apisToLoad = 1; // must match number of calls to gapi.client.load()
	gapi.client.load('devicesApi', 'v1', callback, MARROCCL);
}