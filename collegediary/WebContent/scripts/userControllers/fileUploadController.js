
/************************************************************************
 *  Function 	: fileUploadController
 *	Description : Called to upload multiple files server.It has event 
 *				  registered with it which tells when done 	
 * 	Inputs 		: Scope,Location
 * 	Outputs		: 
 ************************************************************************/

function fileUploadController($scope,$location,commonService){
	$scope.uploadFinished = function(e, data) {
		resMessage = "We just finished uploading this baby...";
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

function webcamUploadController($scope){
	alert("In webcam controller");
	$("#webcam").scriptcam({
		showMicrophoneErrors : false,
		cornerRadius : 20,
		onError : onError,
		cornerColor : 'e3e5e2',
		onWebcamReady : onWebcamReady,
		onPictureAsBase64 : base64_tofield_and_image		
	});
	
	resMessage = "We just finished uploading this baby...";
}