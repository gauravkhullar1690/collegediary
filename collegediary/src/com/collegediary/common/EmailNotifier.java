/**
 * 
 */
package com.collegediary.common;

/**
 * @author gaurav.khullar
 *
 */
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.collegediary.platform.logging.CollegeDiaryLogger;

/**
 * 
 * @author gaurav.khullar
 */
public class EmailNotifier {
	private final static String CLASS_NAME = "com.collegediary.common.EmailNotifier";

	public static void sendSSLMessage(String recipients[], String subject,
			String message) throws MessagingException {
		boolean debug = true;

		Properties props = new Properties();
		props.put("mail.smtp.host", CommonConstants.EmailParameters.HOST);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", CommonConstants.EmailParameters.SMTP_PORT);
		props.put("mail.smtp.socketFactory.port",
				CommonConstants.EmailParameters.SMTP_PORT);
		props.put("mail.smtp.socketFactory.class",
				CommonConstants.EmailParameters.SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication(
								CommonConstants.EmailParameters.USERNAME,
								CommonConstants.EmailParameters.PASSWORD);
					}
				});

		session.setDebug(debug);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(
				CommonConstants.EmailParameters.USERNAME);
		msg.setFrom(addressFrom);

		InternetAddress addressTo = null;
		for (String recepient : recipients) {

			addressTo = new InternetAddress(recepient);

			msg.setRecipient(Message.RecipientType.TO, addressTo);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/plain");
			Transport.send(msg);
		}
	}

	public boolean sendMailStock(ArrayList al) throws Exception {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {
			String dept = "";
			String subject = "Reminder for Stock below minimum stock level";
			String message = "This to a automatically generated mail please don't reply:\n\nThe stock left is less than minimum level for the following spares:\n\n";
			for (int i = 0; i < al.size(); i++) {

			}
			message += "\n\nin the Department:\t" + dept;
			String to[] = new String[2];
			to[0] = "gauravkhullar1690@gmail.com";
			to[1] = "gorav_dhiman46@yahoo.com";

			sendSSLMessage(to, subject, message);
			System.out.println("Sucessfully Sent mail to All Users");
			return true;
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			return false;
		}
	}

	public boolean sendMailMaintenance(ArrayList al, String dept)
			throws Exception {

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {

			String subject = "Reminder for Preventive Maintenance";
			String message = "This to a automatically generated mail please don't reply:\n\nThe Maintenance is due for the following Machines:\n\n";
			for (int i = 0; i < al.size(); i++) {

			}
			message += "\n\nin the Department:\t" + dept;
			String to[] = new String[2];
			to[0] = "gauravkhullar1690@gmail.com";
			to[1] = "gorav_dhiman46@yahoo.com";

			sendSSLMessage(to, subject, message);
			System.out.println("Sucessfully Sent mail to All Users");
			return true;
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			return false;
		}
	}

	public boolean sendMailReceipt(Object srb) throws Exception {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {

			String to[] = new String[2];
			to[0] = "gauravkhullar1690@gmail.com";
			to[1] = "gorav_dhiman46@yahoo.com";
			String subject = "New Spare Receipt Added";
			String message = "This to a automatically generated mail please don't reply:\n\nThe Spare has been Recived following are its details:\n\n";
			sendSSLMessage(to, subject, message);
			System.out.println("Sucessfully Sent mail to All Users");
			return true;
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			return false;
		}
	}

	public static boolean sendNewPasswordMail(String to[], String newPassword,
			String contactEmail) throws Exception {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {
			String subject = "College Diary : Your Password has been reset";
			String message = "This to a automatically generated mail please don't reply:\n\n "
					+ "We have recived a request to reset your password.\n\n"
					+ "Your new Password is \n\n"
					+ newPassword
					+ " .\n\n"
					+ "If your have not requested to reset the password please reply us back @ "
					+ contactEmail + "as soon as you see this email.";

			sendSSLMessage(to, subject, message);
			System.out.println("Sucessfully Sent mail to All Users");
			return true;
		} catch (Exception e) {
			CollegeDiaryLogger
					.error(CLASS_NAME, "sendNewPasswordMail", e, true);
			return false;
		}
	}

}
