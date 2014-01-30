collegediaryapp.service('commonService', function() {
this.customDialog = function(title, message, html, buttons) {
		message = message + ('\n') + html;
		bootbox.dialog({
			message : message,
			title : title,
			buttons : buttons
		});
	};
});