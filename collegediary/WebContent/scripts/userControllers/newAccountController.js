angular.module('collegediary', []);

function newAccountController($scope) {

  $scope.signIn = function() {
	  alert($scope.form);
  };
}