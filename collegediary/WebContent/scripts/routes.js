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
var collegediaryapp = angular.module('collegediary', ['ngRoute','ngCookies']);
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
	}).otherwise({
		redirectTo : '/login'
	});
} ]);
