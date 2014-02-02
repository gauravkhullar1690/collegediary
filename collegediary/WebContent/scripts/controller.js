/************************************************************************
 * 
 * 	FileName	: controller.js
 *  
 *  Description : Set up our mappings between URLs, templates, and 
 *  			  controllers.
 *  			  
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE		 NAME			MODULE 			Changes
 *  ---------------------------------------------------------------------
 *  10-12-2013	Gorav Dhiman	Angular JS		 	Controller for app added		
 *  							Framework
 *  
 ************************************************************************/

/************************************************************************
 *  Function 	: ListController
 *	Description : Publish our messages for the list template.It is called
 *				  when request is mapped to list.html 
 * 	Inputs 		: Scope,Directive
 * 	Outputs		: 
 ************************************************************************/

var resMessage = {};

function ListController($scope,messages) {
	alert("list controller res="+resMessage.username);
	//$scope.messages = messages.query();
	console.log(resMessage);
	$scope.messages = resMessage;
}

/************************************************************************
 *  Function 	: ListController
 *	Description : Get the message id from the route (parsed from the URL) 
 *				  and use it to find the right message object it is called
 *				  when request is mapped to detail.html 
 * 	Inputs 		: Scope
 * 	Outputs		: 
 ************************************************************************/

function DetailController($scope) {
//	$scope.message = (messages.query())[$routeParams.id]
	$scope.message = messages[0];
}

/************************************************************************
 *  Function 	: loginController
 *	Description : Called when login form submit.
 * 	Inputs 		: Scope,http & location & fetch all user list
 * 	Outputs		: 
 ************************************************************************/

function logincontroller($scope,$http,$location)
{
	$scope.login =function(){
		var url = "rest/user/findUser";
        /* while compiling form , angular created this object*/
        var data=$scope.user;  
        /* post to server*/
        $http.post(url, data).success(function(response) {
            $scope.status = status;
            resMessage = response;
            console.log(response);
            $scope.message = response[0]; 
            if (response)
            	$location.path("loginSuccess.html");
            //$location.path("templates/list.html");
            return response;
        }).error(function(data, status, headers, config) {
                console.log(status, headers, config);
        });	
    };
}

/************************************************************************
 *  Function 	: userListController
 *	Description : Called to select given user.
 * 	Inputs 		: Scope
 * 	Outputs		: 
 ************************************************************************/

function userListController($scope){
	$scope.selectedUser = function(row) {
		$scope.selectedRow = row;
	};
}
