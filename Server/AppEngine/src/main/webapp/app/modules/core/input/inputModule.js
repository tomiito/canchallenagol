var module = angular.module('inputModule', []);

module.directive('ngKeyUp', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			element.bind("keyup", function(event) {
				scope.$apply(function() {
					scope.$eval(attrs.ngKeyUp);
				});
			});
		}
	};
});

module.directive('ngEnter', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			element.bind("keydown keypress", function(event) {
				if (event.which === 13) {
					scope.$apply(function() {
						scope.$eval(attrs.ngEnter);
					});

					event.preventDefault();
				}
			});
		}
	};
});

module.directive('ngEnterSelect', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			element.bind("keydown keypress", function(event) {
				if (event.which === 13) {
					scope.$apply(function() {
						scope.$eval(attrs.ngEnterSelect);
						element.select();
					});
					event.preventDefault();
				}
			});
		}
	};
});

module.directive('ngClickSelect', function() {
	return {
		restrict : 'AC',
		link : function(scope, element, attrs) {
			element.bind('click', function() {
				scope.$apply(function() {
					var params = attrs.ngClickSelect.split(",");
					scope.$eval(params[1]);
					$("#" + params[0]).select();
				});
				event.preventDefault();
			});
		}
	}
});

module.filter('isEmpty', function() {
	var bar;
	return function(obj) {
		for (bar in obj) {
			if (obj.hasOwnProperty(bar)) {
				return false;
			}
		}
		return true;
	};
});

module.directive('focusMe', function($timeout) {
	return {
		scope : {
			trigger : '@focusMe'
		},
		link : function(scope, element) {
			scope.$watch('trigger', function(value) {
				if (value === "true") {
					$timeout(function() {
						element[0].focus();
					});
				}
			});
		}
	};
});

module.directive('appFormBody', function() {
	return {
		transclude : true,
		restrict : 'E',
		template : "<fieldset><section class=\"errorSection\" "
				+ "style=\"display:none\">"
				+ "<label class=\"input error errorLabel\"></label>"
				+ "</section><div ng-transclude></div></fieldset>"
	};
});