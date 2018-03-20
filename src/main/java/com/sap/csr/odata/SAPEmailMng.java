package com.sap.csr.odata;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAPEmailMng {

	private Session mailSession;
    private InternetAddress  fromAddress;
    
	private static final Logger logger = LoggerFactory.getLogger(SAPEmailMng.class);
	
	private void getSession()  {
		if (mailSession == null) {
	    	try {
		    	InitialContext ctx = new InitialContext();
		    	mailSession = (Session)ctx.lookup("java:comp/env/mail/SAPInternalNWCloudSession");
	//	    	mailSession.setDebug(true);
		    	
		    	//now use this provided by hr
		    	fromAddress = new InternetAddress("chinacommunications@global.corp.sap");
		    	
		    	System.out.println("create mail session ok " + mailSession.toString());
		    	
	    	} catch (Exception e) {
	    		System.out.println("SAPEmailMng create mail session error"+ e);
	    		e.printStackTrace();
	    	}
		}
    }
	
	public boolean sendEmail(EmailContent email) throws Exception {
		return sendEmail( email.getEmailAddress(), email.getSubject(),email.getBody());
	}
	
	public boolean sendEmail(String to, String subject, String body) throws Exception {
		getSession();
		
		try {
			Message message = new MimeMessage(mailSession);
			message.setFrom(fromAddress ); 
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse( to));
			message.setSubject(subject);
			message.setText(body); 
		
			Transport.send(message);
			return true;
		} catch (Exception e) {
			logger.error("^^SAPEmailMng throw exception for "+ to + " :", e);
			throw e;
		}
	}
	
	
	
}
