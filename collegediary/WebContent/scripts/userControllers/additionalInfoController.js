function additionalInfoController($scope, $http, $location) {

	$scope.switchView = function(step) {
		if (step === 1) {
			$scope.html = "Step 1";
			$('.additional-info-page').html("Step 1");
		} else if (step === 2) {
			$('.additional-info-page').html("Step 2");
			$location.url("/fileUpload");
		} else {
			$('.additional-info-page').html("Step 3");
			$location.url("/profilePicture");
		}
	};
}

function profilePictureController($scope, $http, $location) {
	$scope.pictureView = function(step) {
		if (step === 1) {
			$location.url("/fileUpload");
		}
		else if (step === 2) {
			$location.url("/webcamUpload");
		}
	};
}