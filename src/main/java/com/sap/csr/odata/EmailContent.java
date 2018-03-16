package com.sap.csr.odata;

public class EmailContent {

	
	private String subject, body, emailAddress;
	
	public EmailContent(String emailAddress, String subject, String body) {
		this.emailAddress = emailAddress;
		this.subject = subject;
		this.body = body;
	}
	public String getSubject() {
		return subject;
	}
	public String getBody() {
		return body;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String toString() {
		return emailAddress + " subject:" + subject ;
	}
	
	public void debug() {
		System.out.println(subject);
		System.out.println("=== body == ");
		System.out.println(body);
	}
}
