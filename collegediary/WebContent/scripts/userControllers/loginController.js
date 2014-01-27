function loginController($scope,$http,$location) {

	$scope.performLogin = function() {
		$scope.authenticateUser();
	};
	
	$scope.authenticateUser = function() {
		$http.post('rest/user/authenticateUser', {
			masterUser : $scope.form.masterUser
		}).success(function(data, status, headers, config) {
			$location.url('/additionInfo').hash('dashboard');
		}).error(function(data, status, headers, config) {
			// Handle the error
		});
	};

	$scope.forgotPassword = function() {
		var title = "Forgot Password";
		var message = "Please provide your email address we will send your New Password in some time";
		var html = "<input ng-model=forgotEmail class='forgotPassword bootbox-input bootbox-input-email form-control' autocomplete='off' type='email' autofocus/>";
		var buttons = {
			danger : {
				label : "Send my Password",
				className : "btn-info",
				callback : function() {
					$scope.resetPassword($('.forgotPassword').val());
				}
			}
		};

		$scope.customDialog(title,message,html,buttons);
	};

	$scope.customDialog = function(title, message, html, buttons) {
		message = message + ('\n') + html;
		bootbox.dialog({
			message : message,
			title : title,
			buttons : buttons
		});
	};
	
	$scope.resetPassword = function(email) {
		$http.get('rest/user/resetPassword?email='+email).success(function(data, status, headers, config) {
			bootbox.alert("Your new password has been dispatched from our side. Please check your email in some time");
		}).error(function(data, status, headers, config) {
			bootbox.alert("Currently unable to reset your password. Please try again in some time or contact Us");
		});
	};
}