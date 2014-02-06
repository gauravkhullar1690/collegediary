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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.MaskFormatter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.hibernate.HibernateException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.collegediary.common.CommonConstants;
import com.collegediary.common.EmailNotifier;
import com.collegediary.model.user.FileMeta;
import com.collegediary.model.user.MasterUser;
import com.collegediary.model.user.UserDetails;
import com.collegediary.platform.dao.UserDAO;
import com.collegediary.platform.hbm.StringUtils;
import com.collegediary.platform.logging.CollegeDiaryLogger;

/**
 * @author gaurav.khullar
 * 
 */
public class UserServices implements IUserServices {

	private UserDAO userDAO;
	private HttpSession session = null;
	private final String CLASS_NAME = this.getClass().getName();

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (getUserDAO)
	 * -----------------------------------------------------------------------
	 * This is getter method for setting userDAO variable
	 * 
	 * @return : UserDAO The initialised class variable is returned.
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
	 *            The object that is used to initialise class variable.
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
	 *            The bean for the user details that to be create in database.
	 * 
	 * @return : Record that is added.
	 * @throws Exception
	 * @throws HibernateException
	 * 
	 ***************************************************************************/

	public MasterUser saveMasterUser(MasterUser masterUser)
			throws HibernateException, Exception {
		return userDAO.saveMasterUser(masterUser);
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (saveUserDetails)
	 * -----------------------------------------------------------------------
	 * This is method used to create given user record in database.
	 * 
	 * @param MasterUser
	 *            The bean for the user details that to be create in database.
	 * 
	 * @return : Record that is added.
	 * @throws Exception
	 * @throws HibernateException
	 * 
	 ***************************************************************************/

	public UserDetails saveUserDetails(UserDetails userDetails)
			throws HibernateException, Exception {
		return userDAO.saveUserDetails(userDetails);
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (deleteUser)
	 * -----------------------------------------------------------------------
	 * This is method used to delete given user record from database.
	 * 
	 * @param MasterUser
	 *            The bean for the user details that to be delete in database.
	 * 
	 * @return : void
	 * @throws Exception
	 * @throws HibernateException
	 * 
	 ***************************************************************************/

	public void deleteUser(MasterUser masterUser) throws HibernateException,
			Exception {
		userDAO.deleteUser(masterUser);
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (updateUser)
	 * -----------------------------------------------------------------------
	 * This is method used to update given user record in database.
	 * 
	 * @param MasterUser
	 *            The bean for the user details that to be updated in database.
	 * 
	 * @return : void
	 * @throws Exception
	 * @throws HibernateException
	 * 
	 ***************************************************************************/

	public void updateUser(MasterUser masterUser) throws HibernateException,
			Exception {
		userDAO.updateMasterUser(masterUser);
	}

	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (authenticateUser)
	 * -----------------------------------------------------------------------
	 * This is method that check whether user is registered or not.
	 * 
	 * @param MasterUser
	 *            The bean for the user details.
	 * 
	 * @param HttpServletResponse
	 *            Response object to add cookie
	 * 
	 * @return : True or False
	 * @throws Exception
	 * @throws HibernateException
	 * 
	 ***************************************************************************/

	public String authenticateUser(MasterUser masterUser) throws HibernateException, Exception 
	{
		String tokenValueBase64 = null;
		if (StringUtils.isNotNullOrNotEmpty(masterUser.getToken())) 
		{
			// login using the token
			String token = new String(Base64.decodeBase64(masterUser.getToken().getBytes()));
			String email = token.substring(0, token.indexOf(':'));
			List<MasterUser> users = userDAO.findMasterUserByEmail(email);
			System.out.println(token.substring(0, token.indexOf(':')));
			System.out.println(token.substring(token.lastIndexOf(':')));

			/**
			 * I have username here fetching record & matching it provides login
			 **/
			/**
			 * Have selectAll function providing allUser list can do we that
			 * also
			 **/

			for (int i = 0; i < users.size(); i++) 
			{
				MasterUser user = users.get(i);
				if (token.equals(user.getToken())) 
				{
				
					return CommonConstants.SUCCESS_MSG;
				}
			}
		} 
		else  
		{

			// normal login without remember me
			List<MasterUser> masterUserList = userDAO.findMasterUserByEmail(masterUser.getEmail());
			if(masterUserList != null && !masterUserList.isEmpty())
			{
				if(masterUserList.get(0).getPassword().equals(masterUser.getPassword()))
				{

					if (StringUtils.isNotNullOrNotEmpty(masterUser.getRemmberme())
							&& masterUser.getRemmberme().equalsIgnoreCase("true"))
					{
						// login using the token 
						String signatureValue = DigestUtils.md5Hex(masterUser.getEmail()
								+ ":" + CommonConstants.EXPIRYTIME + ":"
								+ masterUser.getPassword() + ":"
								+ CommonConstants.REST_SERVICES_COOKIE_KEY);
						String tokenValue = masterUser.getEmail() + ":"
						+ CommonConstants.EXPIRYTIME + ":" + signatureValue;
						tokenValueBase64 = new String(Base64.encodeBase64(tokenValue
								.getBytes()));
						masterUser.setToken(tokenValueBase64);
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("token", masterUser.getToken());
						userDAO.updateMasterUser(masterUser.getEmail(),tempMap);
					}
					return CommonConstants.SUCCESS_MSG;
				}
				else 
				{
					return null;
				}
			} 
			else 
			{
				return null;
			}
		}
		return null;
	}
	
	public String resetPassword(String email) throws HibernateException,
			Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			List<MasterUser> userList = userDAO.findMasterUserByEmail(email);
			if (userList != null && !userList.isEmpty()) {
				String randomPassword = RandomStringUtils
						.randomAlphanumeric(CommonConstants.RANDOM_PASSWORD_LENGTH);
				try {
					Map<String, Object> tempMap = new HashMap<String, Object>();
					tempMap.put("password", randomPassword);
					userDAO.updateMasterUser(email, tempMap);
				} catch (Exception e) {
					CollegeDiaryLogger.error(CLASS_NAME,
							"resetPassword Exception in updating new Passowrd for User "
									+ email, e, true);
					throw e;
				}
				String[] toList = new String[1];
				toList[0] = email;
				try {
					EmailNotifier.sendNewPasswordMail(toList, randomPassword,
							CommonConstants.CONTACT_EMAIL_ADDRESS);
				} catch (Exception e) {
					CollegeDiaryLogger
							.error(CLASS_NAME,
									"resetPassword Exception in Sending Email",
									e, true);
					throw e;
				}
				return CommonConstants.PASSWORD_RESET_SUCCESS;
			} else {
				CollegeDiaryLogger.trace(CLASS_NAME, "resetPassword",
						"Unable to find user with email");
				return CommonConstants.USER_EMAIL_NOT_FOUND;
			}
		} catch (Exception e) {
			CollegeDiaryLogger.error(CLASS_NAME,
					"resetPassword unable to find user with email " + email, e,
					true);
			throw e;
		}
	}

	/*********************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (fileUpload)
	 * -----------------------------------------------------------------------
	 * This is method that uploads the file.
	 * 
	 * @param MultipartHttpServletRequest
	 *            Request that uploads multiple files.
	 * 
	 * @return : SUCCESS or FAILURE
	 * 
	 ********************************************************************************/
	public boolean fileUpload(MultipartHttpServletRequest request) {
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		boolean resp = CommonConstants.SUCCESS;
		
		session = request.getSession();

		while (itr.hasNext()) {
			mpf = request.getFile(itr.next());
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
			fileMeta.setFileType(mpf.getContentType());
			
			try {
				fileMeta.setBytes(mpf.getBytes());
				if (!(new File(CommonConstants.PATH + String.valueOf(session.getAttribute("username"))).exists())) {
	                new File(CommonConstants.PATH + String.valueOf(session.getAttribute("username"))).mkdir();
	            }
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
						CommonConstants.PATH + String.valueOf(session.getAttribute("username")) + "/" + mpf.getOriginalFilename()));
				files.add(fileMeta);
			} catch (IOException e) {
				e.printStackTrace();
				CollegeDiaryLogger.error(CLASS_NAME, "fileUpload for "+ fileMeta.getFileName() + " failed", e, true);
				resp = CommonConstants.FAILURE;
			}
		}
		return resp;
	}

	/**********************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (webcamUpload)
	 * -----------------------------------------------------------------------
	 * This is method that uploads the file.
	 * 
	 * @param HttpServletRequest
	 *            Request object that uploads  webcam taken snapshot
	 * 
	 * @return : SUCCESS or FAILURE
	 * 
	 *********************************************************************************/
	public boolean webcamUpload(String image) {
		String imageDataString = null;
		boolean resp = CommonConstants.SUCCESS;
		byte[] imageByteArray = null;
		//imageDataString = request.getParameter("txtPic").toString();
		FileOutputStream imageOutFile = null;
		try {
			//session = request.getSession();
			// Converting a Base64 String into Image byte array
			imageByteArray = Base64.decodeBase64(image);
			// Write a image byte array into file system
			if (!(new File(CommonConstants.PATH + "gorav\\").exists())) {
                new File(CommonConstants.PATH + "gorav\\").mkdir();
            }
			imageOutFile = new FileOutputStream(CommonConstants.PATH + "gorav\\profilePicture.png" );
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image in webcamUpload " + ioe);
			resp = CommonConstants.FAILURE;
		}
		return resp;
	}
}
