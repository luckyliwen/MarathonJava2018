package com.sap.csr.odata;

public interface ServiceConstant {
	
	public static final String FUNC_GET_MYEGISTRATION = "GetMyRegistration";
	public static final String FUNC_GET_USRERINFO = "GetUserInfo";
	
	public static final String ODATA_ISREGISTED = "IsRegisted";
	public static final String ODATA_APPROVE = "Approve";
	

	//entity type 
	public static final String ET_REGISTRATION = "Registration";
	public static final String ET_USERINFO = "UserInfo";
	public static final String ET_ATTACHMENT =  "Attachment";
	public static final String ET_DONATION  =  "Donation";

	//entity sets 
	public static final String ES_REGISTRATION = "Registrations";
	public static final String ES_ATTACHMENT =  "Attachments";
	public static final String ES_DONATION  =  "Donations";
	public static final String ES_USERINFO  =  "UserInfos";
	
	//some query
	public static final String DONATION_BY_DATE ="GetDonationByDate";
	public static final String REGISTRATION_BY_EMAIL= "GetRegistrationByEmail";
	public static final String REGISTRATION_BY_USERID = "GetRegistrationByUserId";
	public static final String REGISTRATION_NO_SUBMITTIME="GetRegistrationNoSubmittime";
	
	public static final String PROJECT_BY_MARATHON = "GetProjectOfMarathon";
	
	public static final String ATTACHMENT_BY_ID_AND_TYPE = "GetAttachmentByIdAndType";
	public static final String ATTACHMENT_BY_ID = "GetAttachmentById";
	
	public static final String EMAIL = "email";
	public static final String FIRST_NAME = "firstname";
	public static final String LAST_NAME = "lastname";
	public static final String EMPLOYEE_ID = "employeeId";

	public static final String ROLE_ADMIN = "Admin";
	
	public static final String STATUS_NOT_APPROVED = "Not Approved";
	public static final String STATUS_APPORVED = "Approved";
	
	
	public static final String PERSISTENCE_UNIT_NAME = "CSR";
	public static final String HCP_TENANTCONEXT_PATH = "java:comp/env/TenantContext";
	
	
	
	//user mgn attribute
	public static final String ATTR_EMAIL = "email";
	public static final String ATTR_FIRST_NAME = "firstname";
	public static final String ATTR_LAST_NAME = "lastname";
	public static final String ATTR_USER_ID = "name";

	//email 
	public static final String FROM_ADDRESS = "hello.sap.csr@gmail.com";
	
	public static final String MSG_APPROVED_BODY="Hello %s\r\n\tCongratulations! Your 2018 Great Wall Marathon registration has been approved!";
	public static final String MSG_REJECTED_BODY="Hello %s\r\n\tWe are sorried that your 2018 Great Wall Marathon registration has been rejected because: %s .";
	
	
	//some static number
	public static final int  MAX_TEAM_MEMBERS = 0;
	
	public static final String DETAIL_REFER = "\r\n\r\n\tFor more information, please visit: https://flpportal-p2000218960trial.dispatcher.hanatrial.ondemand.com/sites?siteId=1ab27b70-4e91-4150-9b06-4c59a1c365a7\r\n\r\n"
			+ "Best Regards!\r\nTEAM SAP Committee";

	public static final String DONATOR_SAP="SAP";
	public static final String DONATORY_CHARITY="CHARITY";
}
