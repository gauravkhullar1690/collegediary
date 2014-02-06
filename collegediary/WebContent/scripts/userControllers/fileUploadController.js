
/************************************************************************
 *  Function 	: fileUploadController
 *	Description : Called to upload multiple files server.It has event 
 *				  registered with it which tells when done 	
 * 	Inputs 		: Scope,Location
 * 	Outputs		: 
 ************************************************************************/

function fileUploadController($scope,$location,commonService){
	$scope.uploadFinished = function(e, data) {
		var title = "File Upload complete";
		var message = "File Upload Successful";
		var html = "";
		var buttons = {
			success : {
				label : "Success",
				className : "btn-info",
				callback : function() {
					
				}
			}
		};

		commonService.customDialog(title,message,html,buttons);
		$location.path("/additionalInfo");
	};
}

/************************************************************************
 *  Function 	: webcamUploadController
 *	Description : Called to upload webcam snapshot on server.
 * 	Inputs 		: Scope,Location
 * 	Outputs		: 
 ************************************************************************/

function webcamUploadController($scope,$http,$location){
	alert("In webcam controller");
	$("#webcam").scriptcam({
		showMicrophoneErrors : false,
		cornerRadius : 20,
		onError : onError,
		cornerColor : 'e3e5e2',
		onWebcamReady : onWebcamReady,
		onPictureAsBase64 : base64_tofield_and_image		
	});
	
	$scope.base64tofield = function () {
		var frame = $.scriptcam.getFrameAsBase64();
		var matersuser = {
				
				image : $.scriptcam.getFrameAsBase64()
			
			};
		$('#txtPic').val(frame);
		$('#image').attr("src","data:image/png;base64," + frame);
		console.debug(matersuser);
		$http.post('rest/user/webcamUpload', {
			masterUser : matersuser
		}).success(function(data, status, headers, config) {
				$location.url('/login');
		}).error(function(data, status, headers, config) {
			alert("error while uploading pic");
			// Handle the error
		});
	}		
}