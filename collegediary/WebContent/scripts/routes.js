/************************************************************************
 * 
 * 	FileName	: routes.js
 *  
 *  Description : Set up our mappings between URLs, templates, and 
 *  			  controllers.
 *  			  
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE		 NAME			MODULE 			Changes
 *  ---------------------------------------------------------------------
 *  10-12-2013	Gorav Dhiman	Angular JS		 	Routes for app added		
 *  							Framework
 *  
 ************************************************************************/

/*******************************************************************************
 * Function : emailRouteConfig Description : This is to provide a routehandler
 * for all http request Inputs : Route Provider. Outputs :
 ******************************************************************************/
collegediaryapp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'htmls/login/sign-up.html',
		controller : newAccountController
	}).when('/login', {
		templateUrl : 'htmls/login/sign-up.html',
		controller : newAccountController
	}).when('/additionlInfo',{
		templateUrl : 'htmls/additional-info/additionl-info-base.html',
		controller : additionalInfoController
	}).when('/profilePicture',{
		templateUrl : 'htmls/additional-info/profile-Picture.html',
		controller : profilePictureController
	}).when('/fileUpload',{
		templateUrl : 'htmls/fileUploads/fileUpload.html',
		controller : fileUploadController
	}).when('/webcamUpload',{
		templateUrl : 'htmls/fileUploads/webcamUpload.html',
		controller : webcamUploadController
	}).otherwise({
		redirectTo : '/login'
	});
} ]);

//templateUrl : 'htmls/fileUploads/webcamUpload.html',
//controller : webcamUploadController