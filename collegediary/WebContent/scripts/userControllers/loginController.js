function loginController($scope,$http,$location, $cookieStore,commonService) {
	
	$scope.performLogin = function() {
		if(typeof ($cookieStore.get('collegediarycookie')) !== 'undefined') {
			var masterUser = {
					token : $cookieStore.get('collegediarycookie')
				};
			$scope.authenticateUser(masterUser);	
		} else if(typeof ($scope.form) !== 'undefined'){
			$scope.authenticateUser($scope.form.masterUser);
		}
	};
	
	$scope.authenticateUser = function(matserUser) {
		
		$http.post('rest/user/authenticateUser', {
			masterUser : matserUser
		}).success(function(data, status, headers, config) {
			if(data !== null){
				$cookieStore.put('collegediarycookie',data);
				$location.url('/home');
			} else {
				bootbox.alert("Please check emailId or password");
				$location.url('/login');
			}
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

		commonService.customDialog(title,message,html,buttons);
	};

	$scope.resetPassword = function(email) {
		$http.get('rest/user/resetPassword?email='+email).success(function(data, status, headers, config) {
			bootbox.alert(data);
		}).error(function(data, status, headers, config) {
			bootbox.alert(data);
		});
	};
	
	$scope.performLogin();
}