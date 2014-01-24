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

package com.collegediary.platform.hbm;

public class CommonConstants 
{
	public static String SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY_REST_SERVICES = "collegeDiaryRemmberMeKey";
	public static String REST_SERVICES_COOKIE_KEY = "collegeDiary_REST_SERVICE";
	public static int EXPIRYTIME = 1800;
	public static String DOMAIN = "http://localHost:8080/";
	public static boolean SUCCESS = true;
	public static boolean FAILURE = false;
}
