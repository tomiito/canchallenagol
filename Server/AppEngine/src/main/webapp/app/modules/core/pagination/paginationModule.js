var pagination = angular.module('paginationModule', []);

pagination.factory('Page', function() {

	function Page(listFn) {
		this.data = {};
		this.size = 10;
		this.offset = 0;
		this.cursors = null;
		this.search = null;
		this.listFn = listFn;
	}

	Page.prototype = {
		changedSize : function(size) {
			this.size = parseInt(size, 10);
			this.listFn();
		},

		previous : function() {
			if (this.offset == 0) {
				return;
			}
			this.offset = this.offset - this.size;
			if (this.offset < 0) {
				this.offset = 0;
			}
			this.listFn();
		},
		next : function() {
			if (this.data.length < this.size) {
				return;
			}
			this.offset = this.offset + this.size;
			this.listFn();
		},
		changedSearch : function(search) {
			this.search = search;
			this.page.cursors = null;
			this.listFn();
		},
		parseResult : function(result) {
			this.data = result.data;
			this.cursors = result.cursors;
		}
	}

	return (Page);
});

pagination.directive('recordsPerPage', function() {
	return {
		replace : true,
		restrict : 'E',
		templateUrl : "app/modules/core/pagination/views/recordsPerPage.html",
		scope : {
			page : '='
		},
		link : function(scope, element, attrs) {
			scope.size = scope.page.size;
			scope.changedSize = function(size) {
				scope.page.size = parseInt(size, 10);
			}
			scope.$watch('page.size', function(value) {
				scope.page.changedSize(scope.size);
			})

		}
	};
});

pagination
		.directive(
				'paginationFooter',
				function() {
					return {
						replace : true,
						restrict : 'E',
						templateUrl : "app/modules/core/pagination/views/paginationFooter.html",
						scope : {
							page : '='
						}
					};
				});