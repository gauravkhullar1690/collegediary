/**
 * @author gaurav.khullar
 *
 */
package com.collegediary.platform.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class CollegeDiaryLogger {

	public static final String EXCEPTION_PREFIX = "Exception: ";
	public static final String ERROR_NO_PREFIX = "Error #: ";
	public static final String TEXT_PREFIX = "Text: ";
	public static final String DETAILS_PREFIX = "Details: ";
	/**
	 * -----------------------------------------------------------------------
	 * Public Methods (logging methods)
	 * -----------------------------------------------------------------------
	 * This method logs message of category with debug priority.
	 * 
	 * @param a_categoryName
	 *            The name of the message category.
	 * @param a_methodName
	 *            The method name to be logged.
	 * @param a_message
	 *            The message to be logged.
	 */
	public static final void debug(String a_categoryName, String a_methodName,
			Object a_message) {
		if (a_message instanceof Exception)
			a_message = formatMessage((Exception) a_message);
		if (null != a_message)
			Logger.getLogger(a_categoryName).debug(
					concatMethodName(a_methodName, a_message.toString()));
	}

	/**
	 * This method logs message of category with info priority.
	 * 
	 * @param a_categoryName
	 *            The name of the message category.
	 * @param a_methodName
	 *            The method name to be logged.
	 * @param a_message
	 *            The message to be logged.
	 */
	public static final void info(String a_categoryName, String a_methodName,
			Object a_message) {
		if (a_message instanceof Exception)
			a_message = formatMessage((Exception) a_message);
		if (null != a_message)
			Logger.getLogger(a_categoryName).info(
					concatMethodName(a_methodName, a_message.toString()));
	}


	/**
	 * -----------------------------------------------------------------------
	 * Public Methods (logging methods)
	 * -----------------------------------------------------------------------
	 * This method logs message of category with debug priority.
	 * 
	 * @param a_categoryName
	 *            The name of the message category.
	 * @param a_methodName
	 *            The method name to be logged.
	 * @param a_message
	 *            The message to be logged.
	 */
	public static final void trace(String a_categoryName, String a_methodName,
			Object a_message) {
		if (a_message instanceof Exception)
			a_message = formatMessage((Exception) a_message);
		if (null != a_message)
			Logger.getLogger(a_categoryName).trace(
					concatMethodName(a_methodName, a_message.toString()));
	}



	/**
	 * This method logs message of category with warn priority.
	 * 
	 * @param a_categoryName
	 *            The name of the message category.
	 * @param a_methodName
	 *            The method name to be logged.
	 * @param a_message
	 *            The message to be logged.
	 */
	public static final void warn(String a_categoryName, String a_methodName,
			Object a_message) {
		if (a_message instanceof Exception)
			a_message = formatMessage((Exception) a_message);
		if (null != a_message)
			Logger.getLogger(a_categoryName).warn(
					concatMethodName(a_methodName, a_message.toString()));
	}

	/**
	 * This method logs message of category with error priority.
	 * 
	 * @param a_categoryName
	 *            The name of the message category.
	 * @param a_methodName
	 *            The method name to be logged.
	 * @param exception
	 *            The message to be logged.
	 * @param needStackTrace
	 *            Stack trace needs to be logged.
	 */
	public static final void error(String a_categoryName, String a_methodName,
			Object exception, boolean needStackTrace) {
		if (exception instanceof Exception) {
			String exceptionMsg = "";
			if (needStackTrace) {
				exceptionMsg = formatMessageStackTrace((Exception) exception);
			} else {
				exceptionMsg = formatMessage((Exception) exception);
			}
			Logger.getLogger(a_categoryName).error(
					concatMethodName(a_methodName, exceptionMsg),
					(Exception) exception);
		} else {
			if (null != exception)
				Logger.getLogger(a_categoryName).error(
						concatMethodName(a_methodName, exception.toString()),
						null);
		}
	}

	private static final String formatMessage(Exception a_exception) {
		StringBuffer message = new StringBuffer();
		message.append(EXCEPTION_PREFIX + a_exception.getClass().getName());
		message.append(": " + TEXT_PREFIX + a_exception.getMessage());
		return message.toString();
	}

	private static final String formatMessageStackTrace(Exception a_exception) {
		StringBuffer message = new StringBuffer();
		Date timeStamp = new Date();
		message.append(" Time Stamp: "
				+ formatDate(timeStamp, "yyyy-MM-dd'T'HH:mm:ss.SSS"));
		message.append(" " + EXCEPTION_PREFIX
				+ a_exception.getClass().getName());
		message.append(": " + TEXT_PREFIX + a_exception.getMessage());

		Exception exception = (Exception) a_exception;
		message.append(" Detailed Message: " + a_exception.getMessage());

		if (a_exception instanceof NestedException) {
			NestedException ne = (NestedException) a_exception;
			List<Exception> nestedException = ne.getNestedExceptions();
			if (nestedException != null) {
				int i = 0;
				for (Iterator<Exception> iterator = nestedException.iterator(); iterator
						.hasNext();) {
					Exception exception2 = iterator.next();
					logStackTrace(exception2, message, i++);
				}
			}

		} else {
			logStackTrace(a_exception, message, 0);
		}

		Throwable causeException = a_exception.getCause();
		while (causeException != null) {
			message.append(" Caused By ");
			message.append(" " + EXCEPTION_PREFIX
					+ causeException.getClass().getName());
			message.append(": " + TEXT_PREFIX + causeException.getMessage());
			message.append(" Detailed Message: " + causeException.getMessage());
			StackTraceElement causeElements[] = causeException.getStackTrace();
			if (causeElements != null) {
				message.append(" Stack Trace: ");
				for (int i = 0, n = causeElements.length; i < n; i++) {
					if (causeElements[i] != null) {
						message.append(causeElements[i].toString() + "\n");
					}
				}
			}
			causeException = causeException.getCause();
		}

		message.append("\n");

		if (a_exception instanceof org.hibernate.exception.GenericJDBCException) {
			org.hibernate.exception.GenericJDBCException be = (org.hibernate.exception.GenericJDBCException) a_exception;
			if (((org.hibernate.exception.GenericJDBCException) be)
					.getSQLException() != null) {
				message.append(((org.hibernate.exception.GenericJDBCException) be)
						.getSQLException().getMessage());
				message.append("\n");
			}
		}

		return message.toString();
	}

	private static final String concatMethodName(String a_methodName,
			String a_message) {
		StringBuffer strMessage = new StringBuffer();
		if (null != a_methodName && !("").equals(a_methodName)) {
			strMessage.append(a_methodName);
			strMessage.append(":");
			strMessage.append(a_message);
			return strMessage.toString();
		}
		return a_message;

	}

	private static void logStackTrace(Exception e, StringBuffer message, int x) {
		StackTraceElement elements[] = e.getStackTrace();
		if (elements != null) {
			message.append(" Stack Trace:" + x + ":  ");
			for (int i = 0, n = elements.length; i < n; i++) {
				if (elements[i] != null) {
					message.append(elements[i].toString() + "\n");
				}
			}
		}
	}

	public static final String formatDate(java.util.Date a_date,
			String a_pattern) {
		SimpleDateFormat formater = new SimpleDateFormat(a_pattern);
		String ds = formater.format(a_date);
		return ds;
	}
}
