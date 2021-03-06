/************************************************************************
 * 
 * 	FileName	:  UserController.java
 *  
 *  Description : This is controller class for all request under User 
 *  			  category.
 *  			    
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE	 	NAME			MODULE 			  Changes
 *  ---------------------------------------------------------------------
 *  07-12-2013	Gaurav Khullar 	User Controller	 createNewUser added
 *  14-12-2013  Gorav Dhiman	User Controller  delete,update,find & 
 *  											 authenticate User added
 *  
 ************************************************************************/

package com.collegediary.ui.controller;
/**
 * 

 *
 **/

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.collegediary.common.CommonConstants;
import com.collegediary.model.user.MasterUser;
import com.collegediary.model.user.UserDetails;
import com.collegediary.platform.logging.CollegeDiaryLogger;
import com.collegediary.platform.services.IUserServices;
@Controller
@RequestMapping("/user")
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;
	// Properties
	private final String CLASS_NAME = this.getClass().getName();
	
	 @Autowired
	 private IUserServices userService;

	 /*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * createNewUser.
	 * 
	 * @param MasterUser
	 *           The bean for the user details.
	 * 
	 * @return :  
	 * 
	 ***************************************************************************/
	 
	@RequestMapping(value = "/saveMasterUser", method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody Map<String, String> saveMasterUser(@RequestBody HashMap<String, MasterUser> userMap){
		CollegeDiaryLogger.trace(CLASS_NAME, "saveMasterUser", "Entering saveMasterUser method");
		Map<String,String> returnMap = new HashMap<String, String>();
		try{
			   MasterUser masterUser = userService.saveMasterUser(userMap.get(CommonConstants.MASTER_USER));
			   returnMap.put(CommonConstants.MASTER_USER_ID, masterUser.getId().toString());
			  CollegeDiaryLogger.info(CLASS_NAME, "saveMasterUser : User with id = " + masterUser.getId() + " updation Successful.", true);
		} 
		catch (Exception e) {
			CollegeDiaryLogger.error(CLASS_NAME, "saveMasterUser", e,true);
		}
		CollegeDiaryLogger.trace(CLASS_NAME, "saveMasterUser", "Exiting saveMasterUser method");
		return returnMap;
	}
	
	
	@RequestMapping(value = "/saveUserDetails", method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody Map<String, String> saveUserDetails(@RequestBody HashMap<String, UserDetails> userMap){
		CollegeDiaryLogger.trace(CLASS_NAME, "saveUserDetails", "Entering saveUserDetails method");
		Map<String,String> returnMap = new HashMap<String, String>();
		try{
			   UserDetails userDetails = userService.saveUserDetails(userMap.get(CommonConstants.USER_DETAILS));
			   returnMap.put(CommonConstants.MASTER_USER, userDetails.getFirstName());
			   CollegeDiaryLogger.info(CLASS_NAME, "saveUserDetails : User with firstName = " + userDetails.getFirstName() + " updation Successful.", true);
		} 
		catch (Exception e) {
			CollegeDiaryLogger.error(CLASS_NAME, "saveUserDetails", e,true);
		}
		CollegeDiaryLogger.trace(CLASS_NAME, "saveUserDetails", "Exiting saveUserDetails method");
		return returnMap;
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * deleteUser.
	 * 
	 * @param MasterUser
	 *           The bean for the user details.
	 * 
	 * @return :  
	 * 
	 ***************************************************************************/
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody Map<String, String> deleteUser(@RequestBody MasterUser masterUser){
		CollegeDiaryLogger.trace(CLASS_NAME, "deleteUser", "Entering deleteUser method");
		Map<String, String> returnMap = new HashMap<String,String>();
		try{
			 userService.deleteUser(masterUser);
			 CollegeDiaryLogger.info(CLASS_NAME, "deleteUser : User deletion Successful ", true);
		} 
		catch (Exception e) {
			CollegeDiaryLogger.error(CLASS_NAME, "deleteUser", e,true);
		}
		CollegeDiaryLogger.trace(CLASS_NAME, "deleteUser", "Exiting deleteUser method");
		return returnMap;
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * updateUser.
	 * 
	 * @param MasterUser
	 *           The bean for the user details.
	 * 
	 * @return : 
	 * 
	 ***************************************************************************/
		
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody Map<String, String> updateUser(@RequestBody MasterUser masterUser){
		CollegeDiaryLogger.trace(CLASS_NAME, "updateUser", "Entering updateUser method");
		Map<String, String> returnMap = new HashMap<String,String>();
		try{
			 userService.updateUser(masterUser);
			 CollegeDiaryLogger.info(CLASS_NAME, "updateUser : User updation Successful ", true);
		} 
		catch (Exception e) {
			CollegeDiaryLogger.error(CLASS_NAME, "updateUser", e,true);
		}
		CollegeDiaryLogger.trace(CLASS_NAME, "updateUser", "Exiting updateUser method");
		return returnMap;
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * findUser.
	 * 
	 * @param MasterUser
	 *           The bean for the user details.
	 * 
	 * @return : Boolean whether successful login or invalid login. 
	 * 
	 ***************************************************************************/
	
	@RequestMapping(value = "/authenticateUser", method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody String authenticateUser(@RequestBody HashMap<String, MasterUser> requestMap){
		CollegeDiaryLogger.trace(CLASS_NAME, "loggingUser", "Entering loggingUser method");
		String token = null;
		try{
			 token = userService.authenticateUser(requestMap.get(CommonConstants.MASTER_USER));
			 CollegeDiaryLogger.info(CLASS_NAME, "authenticateUser : User logged in successfully ", true);
		} 
		catch (Exception e) {
			e.printStackTrace();
			CollegeDiaryLogger.error(CLASS_NAME, "authenticateUser catch", e,true);
		}
		CollegeDiaryLogger.trace(CLASS_NAME, "authenticateUser", "Exiting authenticateUser method");
		return token;
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * findUser.
	 * 
	 * @param MasterUser
	 *           The bean for the user details.
	 * 
	 * @return : ArrayList of masterUser bean as JSON object 
	 * 
	 ***************************************************************************/

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findUser", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = "application/json")
	public @ResponseBody ArrayList<MasterUser> findUser(@RequestBody MasterUser masterUser){
		CollegeDiaryLogger.trace(CLASS_NAME, "findUser", "Entering findUser method");
		Map<String, String> returnMap = new HashMap<String,String>();
		ArrayList<MasterUser> usersList = null;
		MasterUser user = null;
		try{
			 //usersList = (ArrayList) userService.findUsers();
			 for(int i=0; i < usersList.size(); i++)
			 {
				 user = (MasterUser)usersList.get(i);
				 System.out.println(user.getEmail());
			 }
			 CollegeDiaryLogger.info(CLASS_NAME, "findUser : All User List find Successful ", true);
		} 
		catch (Exception e) {
			e.printStackTrace();
			CollegeDiaryLogger.error(CLASS_NAME, "findUser catch", e,true);
		}
		CollegeDiaryLogger.trace(CLASS_NAME, "findUser", "Exiting findUser method");
		return usersList;
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * perform login.
	 * 
	 * @param MasterUser
	 *           The bean for the user details.
	 * 
	 * @return :  
	 * 
	 ***************************************************************************/
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET, headers = { "Accept=application/json" })
	public @ResponseBody String resetPassword(@RequestParam String email)throws Exception{
		CollegeDiaryLogger.trace(CLASS_NAME, "resetPassword", "Entering resetPassword method");
		String result = "";
		try{
			 result = userService.resetPassword(email);
			 CollegeDiaryLogger.info(CLASS_NAME, "deleteUser : User deletion Successful ", true);
		} 
		catch (Exception e) {
			CollegeDiaryLogger.error(CLASS_NAME, "resetPassword", e,true);
			return CommonConstants.PASSWORD_RESET_FAILURE;
		}
		CollegeDiaryLogger.trace(CLASS_NAME, "resetPassword", "Exiting resetPassword method");
		return result;
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * fileUpload.
	 * 
	 * @param MultipartHttpServletRequest
	 *            The multiple files Servlet request.
	 * 
	 * @return : SUCCESS or FAILURE
	 * 
	 ***************************************************************************/

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public @ResponseBody boolean fileUpload(MultipartHttpServletRequest request) {
		boolean resp = CommonConstants.FAILURE;
		CollegeDiaryLogger.trace(CLASS_NAME, "fileUpload","Entering fileUpload method");
		resp = userService.fileUpload(request);
		CollegeDiaryLogger.trace(CLASS_NAME, "fileUpload","Exiting fileUpload method");
		return resp;
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (controller methods)
	 * -----------------------------------------------------------------------
	 * This is controller method which performs action on receiving request for
	 * webcamUpload.
	 * 
	 * @param HttpServletRequest
	 *        	 The HTTP files Servlet request.
	 * 
	 * @return : SUCCESS or FAILURE
	 * 
	 ***************************************************************************/
	
	@RequestMapping(value = "/webcamUpload", method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody boolean webcamUpload(@RequestBody HashMap<String, MasterUser> requestMap) {
		boolean resp = CommonConstants.SUCCESS;
		CollegeDiaryLogger.trace(CLASS_NAME, "webcamUpload","Entering webcamUpload method");
		resp = userService.webcamUpload(requestMap.get(CommonConstants.MASTER_USER).getImage());
		CollegeDiaryLogger.trace(CLASS_NAME, "webcamUpload","Exiting webcamUpload method");
		return resp;

	}
}