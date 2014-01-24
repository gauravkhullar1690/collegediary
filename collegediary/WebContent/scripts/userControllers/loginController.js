function loginController($scope) {

  $scope.performLogin = function() {
	  alert($scope.form);
	  $http.get('api/user', {params: {id: '5'}
	  }).success(function(data, status, headers, config) {
	  // Do something successful.
	  }).error(function(data, status, headers, config) {
	  // Handle the error
	  });
  };
}

function newAccountController($scope) {

	  $scope.performLogin = function() {
		  alert($scope.form);
		  $http.get('api/user', {params: {id: '5'}
		  }).success(function(data, status, headers, config) {
		  // Do something successful.
		  }).error(function(data, status, headers, config) {
		  // Handle the error
		  });
	  };
	}