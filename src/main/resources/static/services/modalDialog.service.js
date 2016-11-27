angular.module('trackerWebApp')
    .service('modalDialog', function () {
        this.modalShown = false;
        this.toggleOnModal = function() {
            this.modalShown = true;
            return this.modalShown;
        }
        this.toggleOffModal = function() {
            this.modalShown = false;
            return this.modalShown;
        }
    });