angular.module('trackerWebApp')
	.directive('modalDialog', modalDialog);

function modalDialog($location, modalDialog) {
	return {
		restrict: 'E',
		replace: true,
		transclude: true,
		link: function(scope, element, attrs) {
			scope.dialogStyle = {};
			if (attrs.width)
				scope.dialogStyle.width = attrs.width;
			if (attrs.height)
				scope.dialogStyle.height = attrs.height;
			scope.show = modalDialog.modalShown;
			scope.hideModal = function() {
				scope.show = modalDialog.toggleOffModal();
				element["0"].ownerDocument.head.ownerDocument.body.style.overflowY = "";
				$location.path('');
			};
			scope.showModal = function() {
				scope.show = modalDialog.toggleOnModal();
				element["0"].ownerDocument.head.ownerDocument.body.style.overflowY = "hidden";
			};
		},
		template:
			"<div class='ng-modal' ng-show='show'>\
				<div class='ng-modal-overlay' ng-click='hideModal()' href=' '></div>\
				<div class='ng-modal-dialog' ng-style='dialogStyle'>\
					<div class='ng-modal-close' ng-click='hideModal()'>\
						&#10060\
					</div>\
					<div class='ng-modal-dialog-content' ng-transclude></div>\
				</div>\
			</div>"
	};
}
