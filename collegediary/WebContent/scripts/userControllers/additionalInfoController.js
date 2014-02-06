/************************************************************************
 *  Function 	: additionalInfoController
 *	Description : Called to show user different views & switch accordingly
 * 	Inputs 		: Scope,Location,Http services
 * 	Outputs		: 
 ************************************************************************/
function additionalInfoController($scope, $http, $location) {

	$scope.switchView = function(step) {
		if (step === 1) {
			$scope.html = "Step 1";
			$('.additional-info-page').html("Step 1");
			$location.url("/findFriends");
		} else if (step === 2) {
			$('.additional-info-page').html("Step 2");
			$location.url("/fileUpload");
		} else {
			$('.additional-info-page').html("Step 3");
			$location.url("/profilePicture");
		}
	};
}

/************************************************************************
 *  Function 	: profilePictureController
 *	Description : Called to load template (webcam Upload or FileUpload)
 *				  in accordance to parameter passed. 
 * 	Inputs 		: Scope,Location & Http services
 * 	Outputs		: 
 ************************************************************************/
function profilePictureController($scope, $http, $location) {
	// User selection on Profile Picture 
	$scope.pictureView = function(step) {
		if (step === 1) {
			$location.url("/fileUpload");
		}
		else if (step === 2) {
			$location.url("/webcamUpload");
		}
	};
}

/************************************************************************
 *  Function 	: profilePictureController
 *	Description : Called to load template (webcam Upload or FileUpload)
 *				  in accordance to parameter passed. 
 * 	Inputs 		: Scope,Location & Http services
 * 	Outputs		: 
 ************************************************************************/

function contactsController($scope,contactsService,paginatorService) {
	// show 'Add Connections' Plugin in "divConnect"
    gigya.socialize.showAddConnectionsUI({ 
		height:65
		,width:120
		,showTermsLink:false // remove 'Terms' link
		,hideGigyaLink:true // remove 'Gigya' link
		,requiredCapabilities: "Contacts" // we want to show only providers that support retrieving contacts.
		,containerID: "divConnect" // The component will embed itself inside the divConnect Div 
	});
	
    // get user info
    gigya.socialize.getUserInfo({ callback: renderUI });
    // register for connect status changes
    gigya.socialize.addEventHandlers({ onConnectionAdded: renderUI, onConnectionRemoved: renderUI });
    // Get the user's contacts
    gigya.socialize.getContacts({ callback: getContacts_callback });

    function renderUI(res) {
	    // enable/disable "Get Contacts" button
	    var connected = (res.user != null && res.user.isDisabled);
	    //$scope.isDisabled = !connected;
	    document.getElementById('btnGetContacts').disabled = !connected;

	    // clear contact list if not connected
	    if (!connected){
	    	$scope.users = "";
	    }
	}
	
    $scope.getContacts = function(){
    	 $scope.users = contactsService.getUsers(); 
    	 var fetchFunction = function(offset, limit, callback) {
    		 callback($scope.users.slice(offset, offset + limit));
    	 };
    	 $scope.searchPaginator = paginatorService(fetchFunction, 3);
    	 //document.getElementById('btnGetContacts').disabled = true;
    }
    
    // Use the reponse of getContacts and render HTML to display the first five contacts.
    function getContacts_callback(response) {
       	document.getElementById('btnGetContacts').disabled = false;
        if (response.errorCode == 0) {
        	var array = response.contacts.asArray();
            contactsService.saveUsers(array);
        } else {
            alert('Error :' + response.errorMessage);
        }
    }
}

function FilterCtrl($scope, filterService) {
	$scope.filterService = filterService;
}