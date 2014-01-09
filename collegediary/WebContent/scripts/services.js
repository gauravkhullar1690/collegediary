/************************************************************************
 * 
 * 	FileName	: services.js
 *  
 *  Description : Create a module for our core AMail services.
 *  			  
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE		 NAME			MODULE 			Changes
 *  ---------------------------------------------------------------------
 *  10-12-2013	Gorav Dhiman	Angular JS		 	Services for app added		
 *  							Framework
 *  
 ************************************************************************/

/* 
 * Create a module for our core AMail services 
 */

var aMailServices = angular.module('AMail', []);

/*
 * Set up our route so the AMail service can find it
 */
aMailServices.config(emailRouteConfig);


/***************************************************************************
*  
*   Function Name : Factory
*   Description	  : Set up the service factory to create our Items interface 
*   				to the server-side database.
*   
***************************************************************************/
aMailServices.factory('messages', function() {
	//Some fake emails
	var message= {};
	message.query = function(){	
		return [
		        {
		        	id: 0, sender: 'jean@somecompany.com', subject: 'Hi there, old friend',
		        	date: 'Dec 7, 2013 12:32:00', recipients: ['greg@somecompany.com'],
		        	message: 'Hey, we should get together for lunch sometime and catch up.'
		        		+'There are many things we should collaborate on this year.'
		        },
		        { 
		        	id: 1, sender: 'maria@somecompany.com',
		        	subject: 'Where did you leave my laptop?',
		        	date: 'Dec 7, 2013 8:15:12', recipients: ['greg@somecompany.com'],
		        	message: 'I thought you were going to put it in my desk drawer.'
		        		+'But it does not seem to be there.'
		        },
		        {
		        	id: 2, sender: 'bill@somecompany.com', subject: 'Lost python',
		        	date: 'Dec 6, 2013 20:35:02', recipients: ['greg@somecompany.com'],
		        	message: "Nobody panic, but my pet python is missing from her cage."
		        		+"She doesn\'t move too fast, so just call me if you see her."
		        },
		        ];
	};
	return message;
});