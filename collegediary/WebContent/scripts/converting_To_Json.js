/************************************************************************
 * 
 * 	FileName	: converting_to_json.js
 *  
 *  Description : It contains all functions required for sending data as
 *  			  JSON object functionality.
 *  
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE	 	NAME			MODULE 			  Changes
 *  ---------------------------------------------------------------------
 *  07-12-2013	Gorav Dhiman	Sending as JSON	 JSON conversion functions
 *  10-12-2013  Gorav Dhiman	Remmber Me		 Cookie check for remmberme
 *  
 ************************************************************************/

/************************************************************************
 *  Function 	: serializeObject
 *	Description : This is used to make JSON object key value Pair. 
 * 	Inputs 		: jquery selector
 * 	Outputs		: JSON Object 
 ************************************************************************/

$.fn.serializeObject = function() {
	var json = {};
	var formElements = this.serializeArray();
	$.each(formElements, function() {
		if (json[this.name] !== undefined) {
			if (!json[this.name].push) {
				json[this.name] = [ json[this.name] ];
			}
			json[this.name].push(this.value || '');
		} else {
			json[this.name] = this.value || '';
		}
	});
	return json;
};

/************************************************************************
 *  Function 	: sendData
 *	Description : This is used to make JSON object key value Pair. 
 * 	Inputs 		: jquery selector.
 * 	Outputs		: send data with post http method.
 ************************************************************************/

$.fn.sendData = function() {
	var form = this;
	var json = $(form).serializeObject();

	$.ajax({
		type : "POST",
		url : $(form).attr("action"),
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify(json, null, '\t'),
		dataType : "json",

		success : function(data) {
			alert(JSON.stringify(json, null, '\t'));
		},
		error : function(data, status, er) {
			alert(JSON.stringify(json, null, '\t'));
		}
	}).done(function() {
		alert("Success to add to-do");
	}).fail(function() {
		alert("Failed to add to-do");
	});
};

/************************************************************************
 *  Function 	: on Ready
 *	Description : Here it is used check cookie if yes just make the JSON
 *				  & send data. 
 * 	Inputs 		: jquery selector.
 * 	Outputs		: send data with post http method.
 ************************************************************************/

$(document).on('ready',function() {
	if (checkCookie("collegeDiaryRemmberMeKey")) {
		document.getElementById("token").value = getCookie("collegeDiaryRemmberMeKey");
		var json = {};
		
		/* Currently nullability constratint so for checking make temporary */
		json = {
				"id" : "1",
				"username" : "utsav",
				"password" : "asdf",
				"token" : getCookie("collegeDiaryRemmberMeKey")
				};
		
		$.ajax({
			type : "POST",
			url : "/CollegeDiary/rest/user/authenticateUser",
			contentType : "application/json; charset=UTF-8",
			data : JSON.stringify(json, null, '\t'),
			dataType : "json"
		}).done(function() {
			alert("Success to add to-do");
			window.location = "#/templates/loginSuccess.html";
		}).fail(function() {
			alert("Failed to add to-do");
		});
		return true;
	}

	$('form#loginform').bind('submit', function(event) {
		event.preventDefault();
		var form = this;
		$(form).sendData(form);
	});
});