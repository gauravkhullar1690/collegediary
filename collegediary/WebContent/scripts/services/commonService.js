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

collegediaryapp.service('contactsService',function(){
	var users;
	this.saveUsers = function(userList){
			this.users = userList;
	};
	
	this.getUsers = function(){
		return this.users;
	};
});

