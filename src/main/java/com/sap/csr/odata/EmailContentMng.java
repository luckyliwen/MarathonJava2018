package com.sap.csr.odata;

import com.sap.csr.model.Registration;

public class EmailContentMng {
	
	
	/**
	 * Create email conent from the registration information
	 * @param registration
	 */
	public static EmailContent createEmailContent(Registration reg) {
		String body = createBody(reg);
		String subject = reg.isApproved() ? s_approveSubject : s_rejectSubject;
				
		return new EmailContent(reg.getEmail(), subject, body);
	}

	public static EmailContent  createEmailContentForWaiting(Registration reg) {
		return null;
	}
	

	private static final String s_rejectSubject = "Your 2018 Marathon registration has been rejected";
	private static final String s_approveSubject = "Your 2018 Marathon registration has been approved";
	
	private static final String createBody(Registration reg) {
		StringBuilder sb = new StringBuilder(100 + s_approveBodyMain.length());
		sb.append("Dear ");
		sb.append( reg.getFullName());
		if (reg.isApproved())
			sb.append(s_approveBodyMain);
		else
			sb.append(s_rejectBodyMain);
		return sb.toString();
	}
	
	private static final String s_approveBodyMain = 
		",\r\n\r\n" +
		"Congratulations! Your 2018 Great Wall Marathon registration has been confirmed!\r\n" +
		"\r\n" +
		"Please make sure you have already blocked your time for the event on May 19 (SAT).  For any changes before March 28, please Ms. Yang Ying @ ying.yang04@sap.com, or Ms. Bela Zhang @ yanjun.zhang@sap.com; For any withdraw after March 28 or no-show on the event day, you will forfeit the registration fee of RMB 1,800 as a donation to our designated charity.\r\n" +
		"\r\n" +
		"We will send all runners an official Welcome Email on April 03. Please make sure to read it carefully which contains every useful information you need to know.\r\n" + 
		"Here is the 2018 Great Wall Marathon official website for your reference: https://great-wall-marathon.com\r\n" +
		"Again, thanks for being part of TEAM SAP to Run The Wall!\r\n" +
		"\r\n" + 
		"Regards,\r\n" +
		"TEAM SAP Committee";


	private static String s_rejectBodyMain = 
		",\r\n\r\n" +
		"Thanks for your interest in the TEAM SAP event!\r\n" +
		"We are sorry to inform you that your 2018 Great Wall Marathon registration has been rejected due to incomplete information.\r\n" +
		"If you have any questions, please feel free to contact Ms. Yang Ying @ ying.yang04@sap.com, or Ms. Bela Zhang @ yanjun.zhang@sap.com\r\n" +
		"\r\n" +
		"Regards,\r\n" +
		"TEAM SAP Committee";

	public static void main(String []args) {
		Registration reg = new  Registration();
		reg.setFirstName("Lucky");
		reg.setLastName("Li");
		reg.setStatus("Approved");
		EmailContent  email = createEmailContent(reg);
		email.debug();
		
		
		reg.setStatus("rejected");
		
		email = createEmailContent(reg);
		email.debug();
		
	}
}
