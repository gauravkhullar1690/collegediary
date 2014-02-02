/************************************************************************
 * 
 * 	FileName	: StringUtils.java
 *  
 *  Description : It contains all common functions for the string checking.
 *  
 *  Revision History:
 *  ---------------------------------------------------------------------
 *  	DATE	 	NAME			MODULE 			  Changes
 *  ---------------------------------------------------------------------
 *  14-12-2013	Gorav Dhiman	String Utils     isNotNullOrNotEmpty added
 *  
 ************************************************************************/

/*
 * @author gorav.dhiman
 */

package com.collegediary.platform.hbm;

public class StringUtils
{
	/*****************************************************************************
	 * -----------------------------------------------------------------------
	 * Public Methods (isNotNullOrNotEmpty)
	 * -----------------------------------------------------------------------
	 * This is method that check whether string contains something.  
	 * 
	 * @param String
	 * 			 String which needs to be checked 
	 * 
	 * @return : True if contains something False otherwise
	 * 
	 ***************************************************************************/

	public static Boolean  isNotNullOrNotEmpty(String str)
	{
		if( null == str || str.equals("")){
			return false;
		}
		return true;
	}
}
