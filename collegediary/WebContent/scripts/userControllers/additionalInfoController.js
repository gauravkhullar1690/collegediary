function additionalInfoController($scope, $http, $location) {

	$scope.switchView = function(step) {
		if (step === 1) {
			$scope.html = "Step 1";
			$('.additional-info-page').html("Step 1");
		} else if (step === 2) {
			$('.additional-info-page').html("Step 2");
		} else {
			$('.additional-info-page').html("Step 3");
		}
	};
}