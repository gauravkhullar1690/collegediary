/************************************************************************
 * 
 * 	FileName	: remmberMeCheck.js
 *  
 *  Description : It contains all functions required for remmber me
 *  			  functionality.
 *  
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  DATE		 NAME			MODULE 			Changes
 *  ---------------------------------------------------------------------
 *  10-12-2013	Gorav Dhiman	RemmberMe		remmberme functions added
 *  
 ************************************************************************/

/************************************************************************
 *  Function 	: getCookie
 *	Description : This is to getCookie a given cookie from a browser 
 *				  if exists. 
 * 	Inputs 		: Name of cookie.
 * 	Outputs		: Value of a cookie null otherwise.
 ************************************************************************/


function getCookie(c_name) {
	var c_value = document.cookie;
	var c_start = c_value.indexOf(" " + c_name + "=");
	if (c_start == -1) {
		c_start = c_value.indexOf(c_name + "=");
	}
	if (c_start == -1) {
		c_value = null;
	} else {
		c_start = c_value.indexOf("=", c_start) + 1;
		var c_end = c_value.indexOf(";", c_start);
		if (c_end == -1) {
			c_end = c_value.length;
		}
		c_value = unescape(c_value.substring(c_start+1, c_end-1));
	}
	return c_value;
}

/************************************************************************
 *  Function 	: setCookie
 *	Description : This is to set a cookie in a browser with a given name,
 *				  value & expiry time. 
 * 	Inputs 		: Name of cookie,value to set & expiry time.
 * 	Outputs		: None.
 ***********************************************************************/


function setCookie(c_name, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value)
	+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
	document.cookie = c_name + "=" + c_value;
}

/***********************************************************************
 *  Function 	: checkCookie
 *	Description : This is to check if given cookie exists or not. 
 * 	Inputs 		: Name of cookie.
 * 	Outputs		: True if exists False otherwise.
 **********************************************************************/

function checkCookie(name) {
	var collegeDiaryCookie = getCookie(name);
	if (collegeDiaryCookie != null && collegeDiaryCookie != "") {
		alert("Welcome again " + collegeDiaryCookie);
		return true;
	}
	return false;
}
