package com.sap.csr.odata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.PasswordAuthentication;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;



public class EmailMng implements ServiceConstant  {
	//@Resource(name = "mail/Session")
    private Session mailSession;
    private InternetAddress  fromAddress;
    
	private static final Logger logger = LoggerFactory.getLogger(EmailMng.class);
	
	public EmailMng() {
		 getSession();
	}
	 	
	private void getSession() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup("java:comp/env/connectivityConfiguration");

		    DestinationConfiguration mailConfig = configuration.getConfiguration("EmailDestination");
		    Map<String, String> map = mailConfig.getAllProperties();
		    Properties  prop = new Properties();
		    String user="";
			String password="";
			int smtpPort = 465;
		    
		    for (String key :  map.keySet()) {
		    	if ( key.equals("Name") || key.equals("Type")) {
		    		continue;
		    	}
		    	
		    	String val = map.get(key);
		    	logger.error("^^mail prop:" + key + "| val=" + val);
		    	//As now mail destination can't use the upper case letter, so get the low case here		    	
		    	if (key.equals("mail.smtp.socketfactory.port")) {
		    		smtpPort = Integer.parseInt(val);
		    		continue;
		    	} else if  ( key.equals("mail.user")) {
		    		fromAddress = new InternetAddress(val);
		    		user = val;
		    		continue;
		    	} else if (key.equals("mail.password")) {
		    		password = val;
		    		continue;
		    	}
		    	
		    	prop.put(key, val);
		    	
		    }
		    //As now destination can't add the upper case letter, so set it here
		    //??test sap mail, old "465"
		    prop.put("mail.smtp.socketFactory.port", smtpPort);
		    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		    
		    final PasswordAuthentication auth = new javax.mail.PasswordAuthentication(
	                user, password);
		    
		    mailSession = Session.getDefaultInstance(prop, 
		    	    new javax.mail.Authenticator(){
		    	        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		    	            return auth;
		    	        }
		    	});
//		    mailSession.setDebug(true);
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("create mailSession failed", e);
		}
	}
	
	public boolean sendEmail(EmailContent email) throws Exception {
		return sendEmail( email.getEmailAddress(), email.getSubject(),email.getBody());
	}
	
	public boolean sendEmail(String to, String subject, String body) throws Exception {
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
			logger.error("^^throw exception for "+ to + " :", e);
			throw e;
		}
	}
	
	public boolean sendEmail_old(String to, String subject, String body) throws Exception {
		Transport transport = null;
        try {
        	
            // Construct message from parameters
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            //??how to get the from address
//            InternetAddress[] fromAddress = InternetAddress.parse(FROM_ADDRESS);
            InternetAddress[] toAddresses = InternetAddress.parse(to);
//            mimeMessage.setFrom(fromAddress[0]);
            mimeMessage.setRecipients(RecipientType.TO, toAddresses);
            mimeMessage.setSubject(subject, "UTF-8");
            MimeMultipart multiPart = new MimeMultipart("alternative");
            MimeBodyPart part = new MimeBodyPart();
            
            //here auto add the refer and signature 
            StringBuffer sb = new StringBuffer(body);
    
            part.setText(sb.toString(), "utf-8", "plain");
            multiPart.addBodyPart(part);
            mimeMessage.setContent(multiPart);

            // Send mail
            transport = mailSession.getTransport();
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            return true;
         
        } catch (Exception e) {
            logger.error("Mail operation failed", e);
            throw e;
        } finally {
            // Close transport layer
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw e;
                }
            }
        }
	}
	
	public static void main(String[] args) {
		//StringBuffer sb = new StringBuffer();
		Formatter formatter = new Formatter(); //sb, Locale.US);
		//"Hello %s\r\nWe are sorried that your registration has been rejected because: %s \r\nBest Regards.";
		formatter.format(MSG_REJECTED_BODY, "Lucky Li", "not provide id");
		System.out.println(formatter.toString());
		
		System.out.println("==============");
		formatter.format(MSG_APPROVED_BODY, "Lucky Li");
		System.out.println(formatter.toString());
		
		
		EmailMng em = new EmailMng();
		try {
			
//			em.getLocalSession("GmailDestination");
//			em.getLocalSession("QqEmailDestination");
//			em.getLocalSession("EmailDestination");
//			em.getLocalSession("SinaEmailDestination");
			
//			em.sendEmail("lucky.li01@sap.com", "csr again", " second first email " );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
