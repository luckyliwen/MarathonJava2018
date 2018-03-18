package com.sap.csr.odata;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSendService implements Runnable  {
	Logger logger = LoggerFactory.getLogger(EmailSendService.class);
	public static BlockingQueue<EmailContent> s_blockingQueue = new LinkedBlockingQueue();
	
	static {
		Thread thread = new Thread(new EmailSendService());
    	thread.start();
	}

	public void run() {
		EmailMng  emailMng = new EmailMng();
		EmailContent email = null;
		while (true) {
//			logger.debug("!!!! waiting sendig email task");
			try {
				email = s_blockingQueue.take();
//				logger.debug("!!Send email " + email.toString() );
				emailMng.sendEmail(email);
//				logger.debug("Finish send email");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Send email failed" + email.toString(), e);
			}
		}
		
	}
	
	public static void sendEmail(EmailContent email) throws InterruptedException {

		s_blockingQueue.put(email);
		
	}
}
