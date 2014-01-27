/************************************************************************
 * 
 * 	FileName	:  UserServices.java
 *  
 *  Description :  This is class that provides different services to user
 *  			   to provide database access. 
 *  			    
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE	 	NAME			MODULE 			  Changes
 *  ---------------------------------------------------------------------
 *  07-12-2013	Gaurav Khullar 	User Services	 createNewUser added
 *  14-12-2013  Gorav Dhiman	User Services    delete,update,find & 
 *  											 authenticate User added
 *  
 ************************************************************************/

package com.collegediary.platform.services;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.collegediary.common.CommonConstants;
import com.collegediary.model.user.MasterUser;
import com.collegediary.model.user.UserDetails;
import com.collegediary.platform.dao.UserDAO;
import com.collegediary.platform.hbm.StringUtils;

/**
 * @author gaurav.khullar
 * 
 */
public class UserServices implements IUserServices {

	private UserDAO userDAO;

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (getUserDAO)
	 * -----------------------------------------------------------------------
	 * This is getter method for setting userDAO variable 
	 * 
	 * @return : UserDAO
	 * 		  The initialised class variable is returned. 
	 * 
	 ***************************************************************************/
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (setUserDAO)
	 * -----------------------------------------------------------------------
	 * This is setter method for setting userDAO variable 
	 * 
	 * @param UserDAO
	 * 		  The object that is used to initialise class variable. 
	 * 
	 * @return : void
	 * 
	 ***************************************************************************/
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (saveMasterUser)
	 * -----------------------------------------------------------------------
	 * This is method used to create given user record in database. 
	 * 
	 * @param MasterUser
	 *           The bean for the user details that to be create in database.
	 * 
	 * @return : Record that is added.
	 * 
	 ***************************************************************************/
	
	public MasterUser saveMasterUser(MasterUser masterUser) {
		return  userDAO.saveMasterUser(masterUser);
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (saveUserDetails)
	 * -----------------------------------------------------------------------
	 * This is method used to create given user record in database. 
	 * 
	 * @param MasterUser
	 *           The bean for the user details that to be create in database.
	 * 
	 * @return : Record that is added.
	 * 
	 ***************************************************************************/
	
	public UserDetails saveUserDetails(UserDetails userDetails) {
		return  userDAO.saveUserDetails(userDetails);
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (deleteUser)
	 * -----------------------------------------------------------------------
	 * This is method used to delete given user record from database. 
	 * 
	 * @param MasterUser
	 *           The bean for the user details that to be delete in database.
	 * 
	 * @return : void
	 * 
	 ***************************************************************************/
	
	public void deleteUser(MasterUser masterUser) {
		userDAO.deleteUser(masterUser);
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (updateUser)
	 * -----------------------------------------------------------------------
	 * This is method used to update given user record in database. 
	 * 
	 * @param MasterUser
	 *           The bean for the user details that to be updated in database.
	 * 
	 * @return : void
	 * 
	 ***************************************************************************/
	
	public void updateUser(MasterUser masterUser) {
		userDAO.updateUser(masterUser);
	}
	
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (findUsers)
	 * -----------------------------------------------------------------------
	 * This method is used to find the list of all users. 
	 * 
	 * @return : List of all users.
	 * 
	 ***************************************************************************/
	
	public List<MasterUser> findUsers() {
		return userDAO.findUsers();
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (authenticateUser)
	 * -----------------------------------------------------------------------
	 * This is method that check whether user is registered or not. 
	 * 
	 * @param MasterUser
	 *           The bean for the user details.
	 *           
	 * @param HttpServletResponse
	 * 			 Response object to add cookie
	 * 
	 * @return :  True or False
	 * 
	 ***************************************************************************/

	public boolean authenticateUser(MasterUser masterUser,HttpServletResponse response) {
		boolean result = CommonConstants.FAILURE;		
		if(StringUtils.isNotNullOrNotEmpty(masterUser.getToken())){
			String token = "";//new String(Base64.decodeBase64(masterUser.getToken()));
			String username = token.substring(0, token.indexOf(':'));
			List<MasterUser> users = userDAO.findUserByName(username);
			System.out.println(token.substring(0, token.indexOf(':')));
			System.out.println(token.substring(token.lastIndexOf(':')));
			
			/** I have username here fetching record & matching it provides login **/
			/** Have selectAll function providing allUser list can do we that also **/
			
			for (int i=0; i < users.size();i++){
				MasterUser user = users.get(i);
				if (token.equals(user.getToken())){
				
					result = CommonConstants.SUCCESS;
				}
			}
		}
		else if (StringUtils.isNotNullOrNotEmpty(masterUser.getRemmberme()) && 
				masterUser.getRemmberme().equalsIgnoreCase("on")) {

			String signatureValue = DigestUtils.md5Hex(masterUser.getEmail()
					+ ":" + CommonConstants.EXPIRYTIME + ":"
					+ masterUser.getPassword() + ":"
					+ CommonConstants.REST_SERVICES_COOKIE_KEY);
			String tokenValue = masterUser.getEmail() + ":"
					+ CommonConstants.EXPIRYTIME + ":" + signatureValue;
			String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue
					.getBytes()));
			
			Cookie cookie = new Cookie(
					CommonConstants.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY_REST_SERVICES,	
					tokenValueBase64);
			if (StringUtils.isNotNullOrNotEmpty(CommonConstants.DOMAIN)) {
				//cookie.setDomain(CommonConstants.DOMAIN);
			}
			System.out.println("Cookie = "+ tokenValue + " token = "+ tokenValueBase64 + "sign value = "+ signatureValue);
			cookie.setPath("/");
			masterUser.setToken(tokenValueBase64);
			response.addCookie(cookie);
			result = CommonConstants.SUCCESS;
		}
		userDAO.updateUser(masterUser);
		return result;
	}

	/**
	 * 
	 */
	public String resetPassword(String email) {
		return null;
	}
}
