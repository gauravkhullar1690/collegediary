/************************************************************************
 * 
 * 	FileName	:  IUserServices.java
 *  
 *  Description :  This is interface class that provides different services
 *  			   to user to provide database access. 
 *  			    
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE	 	NAME			MODULE 			  Changes
 *  ---------------------------------------------------------------------
 *  07-12-2013	Gaurav Khullar 	IUser Services	 createNewUser added
 *  14-12-2013  Gorav Dhiman	IUser Services   delete,update,find & 
 *  											 authenticate User added
 *  
 ************************************************************************/
package com.collegediary.platform.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.collegediary.model.user.MasterUser;
import com.collegediary.model.user.UserDetails;

/**
 * @author gaurav.khullar
 * 
 */
public interface IUserServices {
	/**
	 * This method is used to create the Master user in the CollegeDiary Application 
	 * A Master user also has a UserDetails Object
	 * 
	 * @param masterUser
	 * @return MasterUser that is updated in database.
	 */
	public MasterUser saveMasterUser(MasterUser masterUser)throws HibernateException, Exception ;
	
	/**
	 * This method is used to save the user details for a master User
	 * @param userDetails
	 * @return UserDetails that is updated in database.
	 */
	public UserDetails saveUserDetails(UserDetails userDetails)throws HibernateException, Exception;
	/**
	 * This is method used to delete given user record from database. 
	 * 
	 * @param MasterUser
	 *           The bean for the user details that to be delete in database.
	 * 
	 * @return : void
	 * 
	 **/
	public void deleteUser(MasterUser masterUser)throws HibernateException, Exception;
	/**
	 * This is method used to update given user record from database. 
	 * 
	 * @param MasterUser
	 *           The bean for the user details that to be update in database.
	 * 
	 * @return : void
	 * 
	 **/
	public void updateUser(MasterUser masterUser)throws HibernateException, Exception;
	/**
	 * This method is used to find the list of all users. 
	 * 
	 * @return : List of all users.
	 * 
	 **/	
	public String authenticateUser(MasterUser masterUser)throws HibernateException, Exception;
	
	/**
	 * 
	 * @param public boolean authenticateUser(MasterUser masterUser,HttpServletResponse response);
	 * @return messageString
	 */
	public String resetPassword(String email)throws HibernateException, Exception;
}
