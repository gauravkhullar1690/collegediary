function newAccountController($scope, $http, $location) {

	$scope.signUp = function() {
		alert($scope.form);
		$scope.saveMasterUser();
	};

	$scope.saveMasterUser = function() {
		$http.post('rest/user/saveMasterUser', {
			masterUser : $scope.form.masterUser
		}).success(function(data, status, headers, config) {
			$scope.saveUserDetails(data.masterUserId);
		}).error(function(data, status, headers, config) {
			// Handle the error
		});
	};

	$scope.saveUserDetails = function(masterUserId) {
		var userDetails = $scope.form.userDetails;
		userDetails.masterUser = {
			id : masterUserId
		};
		$http.post('rest/user/saveUserDetails', {
			userDetails : userDetails
		}).success(function(data, status, headers, config) {
			$location.url('/additionlInfo');
		}).error(function(data, status, headers, config) {
			// Handle the error
		});
	};
}