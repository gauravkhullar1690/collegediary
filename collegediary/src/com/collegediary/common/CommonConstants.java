/************************************************************************
 * 
 * 	FileName	: CommonConstants.java
 *  
 *  Description : It contains all common constants that are used all over
 *  			  the application & mentioned here so that they can change
 *  			  through one file.
 *  
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE	 	NAME			MODULE 			  Changes
 *  ---------------------------------------------------------------------
 *  14-12-2013	Gorav Dhiman	COMMON CONSTANTS  Constants added
 *  
 ************************************************************************/

/*
 * @author gorav.dhiman
 */

package com.collegediary.common;

public class CommonConstants {
	public static String SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY_REST_SERVICES = "collegeDiaryRemmberMeKey";
	public static String REST_SERVICES_COOKIE_KEY = "collegeDiary_REST_SERVICE";
	public static int EXPIRYTIME = 1800;
	public static String DOMAIN = "http://localHost:8080/";
	public static boolean SUCCESS = true;
	public static boolean FAILURE = false;
	public static String MASTER_USER = "masterUser";
	public static String USER_DETAILS = "userDetails";
	public static String USER_TRAVELLER = "userTraveller";
	public static String MASTER_USER_ID = "masterUserId";
	public static String PATH = "C:\\Users\\Gorav\\Desktop\\collegediary\\WebContent\\images\\";
	public static String EMAIL = "email";
	public static Integer RANDOM_PASSWORD_LENGTH = 20;
	public static String USER_EMAIL_NOT_FOUND = "We were unable to find any user with the email id you provided.\n\nPlease try again by giving correct email id.";
	public static String SUCCESS_MSG = "SUCCESS";
	public static String FAILURE_MSG = "FAILURE";
	public static String PASSWORD_RESET_SUCCESS = "Your new password has been dispatched from our side. Please check your email in some time";
	public static String PASSWORD_RESET_FAILURE = "Currently unable to reset your password. Please try again in some time or Contact Us";
	public static String CONTACT_EMAIL_ADDRESS = "gauravkhullar1690@gmail.com";

	public interface EmailParameters {
		public static String SMTP_PORT = "465";
		public static String USERNAME = "daburmaintenance@gmail.com";
		public static String PASSWORD = "daburmails";
		public static String HOST = "smtp.gmail.com";
		public static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	}
}
