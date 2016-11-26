angular.module('trackerWebApp')
	.directive('modalDialog', modalDialog);

function modalDialog() {
	return {
        compile: function compile(temaplateElement, templateAttrs) {
            return {
                pre: function (scope, element, attrs) {
                    scope.dialogStyle = {};
                },
                post: function(scope, element, attrs) {
                    scope.hideModal = function() {
                        scope.show = false;
                        element["0"].ownerDocument.head.ownerDocument.body.style.overflowY = "";
                    };
                }
            }
        },
		restrict: 'E',
		scope: {
			show: '='
		},
        replace: true,
		transclude: true,
		link: function(scope, element, attrs) {
			if (attrs.width)
				scope.dialogStyle.width = attrs.width;
			if (attrs.height)
				scope.dialogStyle.height = attrs.height;
		},
		template:
			"<div class='ng-modal' ng-show='show'>\
				<div class='ng-modal-overlay' ng-click='hideModal()'></div>\
				<div class='ng-modal-dialog' ng-style='dialogStyle'>\
					<div class='ng-modal-close' ng-click='hideModal()'>&#10060</div>\
					<div class='ng-modal-dialog-content' ng-transclude></div>\
				</div>\
			</div>"
	};
}
