package com.sap.csr.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.naming.NamingException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.csr.odata.EmailContentMng;
import com.sap.csr.odata.EmailMng;
import com.sap.csr.odata.EmailSendService;
import com.sap.csr.odata.JpaEntityManagerFactory;
import com.sap.csr.odata.ServiceConstant;
import com.sap.csr.odata.Util;


@Entity(name="Registration")
@NamedQueries({ 
	@NamedQuery(name=ServiceConstant.REGISTRATION_BY_USERID, query="select r from Registration r where r.userId = :userId"),
	@NamedQuery(name=ServiceConstant.REGISTRATION_BY_EMAIL, query="select r from Registration r where r.email = :email"),
	@NamedQuery(name=ServiceConstant.REGISTRATION_NO_SUBMITTIME, query="select r from Registration r where r.submittedTime IS NULL and (r.status =\"Approved\" or r.status=\"Submitted\") ")
})

public class Registration extends BaseModel  implements ServiceConstant{
	static private final Logger logger = LoggerFactory.getLogger(Registration.class);
//	private static long serialVersionUID = 1L;

	//private long projectId;
	
	//@Column(unique=true)
	@Id
	@Column(name = "USERID", length = 10)
	private String userId;  //id of sap id
	
	private String firstName;
	private String lastName;
//	private String gender;
	private String nationality;
	private String department;
	
	public String getIdOrPassport() {
		return idOrPassport;
	}


	public void setIdOrPassport(String idOrPassport) {
		this.idOrPassport = idOrPassport;
	}

	private String idOrPassport;
	
	private String club;
	private String title ; //Mr, Mrs
	private String phone;
	//@Column(unique=true)
	private String email;
	private String distance;
	private String tshirtSize;
	
	private String fullBestTime, halfBestTime, funBestTime;
	
	private String updateFlag;  //used to check what really changed, mainly for update group
	
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date submittedTime;


	private int age;
	private String regFirstName, regLastName;
	
	// columnDefinition could simply be = "TIMESTAMP", as the other settings are the MySQL default
	//@Column(name="MODIFIEDTIME",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIME ON UPDATE CURRENT_TIME")
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedTime;
	
	private String status ;
	private String location ;
	
	private String rejectReason;
	private String cancelReason;
			
	private boolean vip,  inWaitingList=false;
	//@OneToMany(mappedBy = "registration",  cascade=CascadeType.ALL )
	
//	@OneToMany(mappedBy = "registration")
//	private Set<Attachment>  attachment = new HashSet<Attachment>();
	//private List<Attachment>  attachment = new ArrayList<Attachment>();
	
	
	//@OneToMany(mappedBy = "registration", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
	//private List<Donation>  donation = new ArrayList<Donation>();
	
	
	public boolean isInWaitingList() {
		return inWaitingList;
	}


	public void setInWaitingList(boolean inWaitingList) {
		this.inWaitingList = inWaitingList;
	}


	public Registration() {
		super();
	}
	

	/**
	 * @return the userId
	 */
	public final String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public final void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the firstName
	 */
	public final String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public final String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public final String getFullName() {
		return firstName + " " + lastName;
	}
	
//	/**
//	 * @return the gender
//	 */
//	public final String getGender() {
//		return gender;
//	}
//
//	/**
//	 * @param gender the gender to set
//	 */
//	public final void setGender(String gender) {
//		this.gender = gender;
//	}

	/**
	 * @return the nationality
	 */
	public final String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public final void setNationality(String nationality) {
		this.nationality = nationality;
	}


	/**
	 * @return the club
	 */
	public final String getClub() {
		return club;
	}

	/**
	 * @param club the club to set
	 */
	public final void setClub(String club) {
		this.club = club;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the phone
	 */
	public final String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public final void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the distance
	 */
	public final String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public final void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return the tshirtSize
	 */
	public final String getTshirtSize() {
		return tshirtSize;
	}

	/**
	 * @param tshirtSize the tshirtSize to set
	 */
	public final void setTshirtSize(String tshirtSize) {
		this.tshirtSize = tshirtSize;
	}

	/**
	 * @return the submittedTime
	 */
	public final Date getSubmittedTime() {
		return submittedTime;
	}

	/**
	 * @param submittedTime the submittedTime to set
	 */
	public final void setSubmittedTime(Date submittedTime) {
		this.submittedTime = submittedTime;
	}

	/**
	 * @return the modifiedTime
	 */
	public final Date getModifiedTime() {
		return modifiedTime;
	}

	/**
	 * @param modifiedTime the modifiedTime to set
	 */
	public final void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	/**
	 * @return the status
	 */
	public final String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public final void setStatus(String status) {
		this.status = status;
	}

//	/**
//	 * @return the attachments
//	 */
//	public final List<Attachment> getAttachment() {
//		return attachment;
//	}
//
//	/**
//	 * @param attachments the attachments to set
//	 */
//	public final void setAttachment(List<Attachment> attachment) {
//		this.attachment = attachment;
//	}
//
//	/**
//	 * @return the gotDonations
//	 */
//	public final List<Donation> getGotDonations() {
//		return gotDonations;
//	}
//
//	/**
//	 * @param gotDonations the gotDonations to set
//	 */
//	public final void setGotDonations(List<Donation> gotDonations) {
//		this.gotDonations = gotDonations;
//	}
	
	//last time record
	public static Registration createNewRegistration(UserInfo userInfo){
		Registration reg = new Registration();
		reg.setEmail(userInfo.getEmail());
		reg.setFirstName(userInfo.getFirstName());
		reg.setLastName(userInfo.getLastName());
		reg.setUserId(userInfo.getUserId());
		reg.setStatus("New");
		return reg;
	}

//	/**
//	 * @return the attachments
//	 */
//	public final Set<Attachment> getAttachment() {
//		return attachment;
//	}
//
//	/**
//	 * @param attachments the attachments to set
//	 */
//	public final void setAttachment(Set<Attachment> attachments) {
//		this.attachment = attachments;
//	}

	
	synchronized boolean needEnterWaitingList() {
		boolean need = false;
		if ( ! isVip()) {
			getEntityManager();
			String str = "Select count(r) from Registration r where (r.status=\"Approved\" or r.status=\"Rejected\" or r.status=\"Submitted\") and r.vip=\"false\"";
			Query query = em.createQuery(str);
			Object result = query.getSingleResult();
			long num = 0;
			if (result instanceof Long) {
				num = (Long)result;
			}
			
			query = em.createNamedQuery(PROJECT_BY_MARATHON);
			Project prj = (Project)query.getSingleResult();
			long max = prj.getTotalNormalCapacity();
			need = num >= max;
		}
		return need;
	}
	
	
	synchronized boolean hasEnoughSeat() {
		getEntityManager();
		String str = "Select count(r) from Registration r where r.status=\"Approved\" and r.vip=\"false\"";
		Query query = em.createQuery(str);
		Object result = query.getSingleResult();
		long num = 0;
		if (result instanceof Long) {
			num = (Long)result;
		}
		
		query = em.createNamedQuery(PROJECT_BY_MARATHON);
		Project prj = (Project)query.getSingleResult();
		long max = prj.getTotalNormalCapacity();
		return  num < max ? true: false;
	}

	public void setSubmitTime() {
		//here should ensure it only update once
		if ( status.equals("Submitted")) {
			if (submittedTime == null) {
				submittedTime = new Date();
				
				//only the first time state is Submitted need send email for waiting, as admin can save several time
				if ( needEnterWaitingList() ) {
					
					setInWaitingList(true);
					
					logger.error(" %%% " + userId + " enter to waiting list");
					try {
						EmailSendService.sendEmail( EmailContentMng.createEmailContentForWaiting(this, true));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	//!!Previous forget to add the PerPersist, so some directly submit don't have submit time
	@PrePersist
	public void onPrePersit() {
		modifiedTime = new Date();
		setSubmitTime();
	}
	
	/**
	 * @return the modifiedTime
	 * @throws Exception 
	 */
	@PreUpdate
	public void createUpdateModifiedTime() throws Exception {
		modifiedTime = new Date();
		
		setSubmitTime();
		
		if ( updateFlag == null) {
			return;
		}
		
		if ( updateFlag.equals("approve")) {
		
		
			//only for the 
			//for the reject and approved,need trigger the email
			String subject = "Your 2018 Marathon registration has been ";
			String body = "";
			Formatter fmt = new Formatter();
			
			if ( status.equals("Approved")) {
				//here need check whether has enough number
				if (!vip) {
					if (!hasEnoughSeat()) {
						throw new Error("[[#Can't approved because it exceeds maximum allowed numbers#]]");
					}
				}
			
			} else if ( !status.equals("Rejected")) {
				return ;
			}
			
			
			try {
				EmailSendService.sendEmail( EmailContentMng.createEmailContent(this));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ( updateFlag.equals("Waiting")) {
			logger.debug("Put in waiting list for " + getUserId());
			try {
				setInWaitingList(true);
				EmailSendService.sendEmail( EmailContentMng.createEmailContentForWaiting(this, false));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//need reset it in order next time will not affect
		updateFlag = "";
	}
	
	
	public boolean isApproved() {
		return status.equals("Approved");	
	}
		

	/*@PostLoad
	public void tryLoadAttachment(Object obj) {
		Registration reg = (Registration)obj;
		if (reg != null) {
			Set<Attachment>  attachments = reg.getAttachment();
			if (attachments == null) {
				attachments = new HashSet<Attachment>();
				reg.setAttachment(attachments);
			}
				
			if (attachments.isEmpty()) {
				EntityManager em = null;
				try {
					em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
					TypedQuery<Attachment> query = em.createNamedQuery(ATTACHMENT_BY_ID, Attachment.class);
					List<Attachment> result =  query.getResultList();
					for (Attachment att : result) {
						attachments.add(att);
					}
					
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Util.logException("NamingException", e);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Util.logException("SQLException", e);
				} catch (Exception e) {
					e.printStackTrace();
					Util.logException("General exception", e);
				} finally {
					em.close();
				}
				
			}
		}
	}*/
	
	/*@PostLoad
	public void tryLoadAttachment() {
			Set<Attachment>  attachments = getAttachment();
			if (attachments == null) {
				attachments = new HashSet<Attachment>();
				setAttachment(attachments);
			}
				
			if (attachments.isEmpty()) {
				EntityManager em = null;
				try {
					em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
					TypedQuery<Attachment> query = em.createNamedQuery(ATTACHMENT_BY_ID, Attachment.class);
					List<Attachment> result =  query.getResultList();
					for (Attachment att : result) {
						attachments.add(att);
					}
					
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Util.logException("NamingException", e);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Util.logException("SQLException", e);
				} catch (Exception e) {
					e.printStackTrace();
					Util.logException("General exception", e);
				} finally {
					em.close();
				}
				
			}
		
	}*/
//	/**
//	 * @return the donation
//	 */
//	public final List<Donation> getDonation() {
//		return donation;
//	}
//
//	/**
//	 * @param donation the donation to set
//	 */
//	public final void setDonation(List<Donation> donation) {
//		this.donation = donation;
//	}

//	/**
//	 * @return the attachment
//	 */
//	public final Set<Attachment> getAttachment() {
//		return attachment;
//	}
//
//	/**
//	 * @param attachment the attachment to set
//	 */
//	public final void setAttachment(Set<Attachment> attachment) {
//		this.attachment = attachment;
//	}

	/**
	 * @return the rejectReason
	 */
	public final String getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason the rejectReason to set
	 */
	public final void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * @return the cancelReason
	 */
	public final String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param cancelReason the cancelReason to set
	 */
	public final void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	public String getName() {
		return lastName + " " + firstName;
	}

	/**
	 * @return the updateFlag
	 */
	public final String getUpdateFlag() {
		return updateFlag;
	}

	/**
	 * @param updateFlag the updateFlag to set
	 */
	public final void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

//	/**
//	 * @return the groupId
//	 */
//	public final long getGroupId() {
//		return groupId;
//	}
//
//	/**
//	 * @param groupId the groupId to set
//	 */
//	public final void setGroupId(long groupId) {
//		this.groupId = groupId;
//	}

	/**
	 * @return the age
	 */
	public final int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public final void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the regFirstName
	 */
	public final String getRegFirstName() {
		return regFirstName;
	}

	/**
	 * @param regFirstName the regFirstName to set
	 */
	public final void setRegFirstName(String regFirstName) {
		this.regFirstName = regFirstName;
	}

	/**
	 * @return the regLastName
	 */
	public final String getRegLastName() {
		return regLastName;
	}

	/**
	 * @param regLastName the regLastName to set
	 */
	public final void setRegLastName(String regLastName) {
		this.regLastName = regLastName;
	}

	/**
	 * @return the department
	 */
	public final String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public final void setDepartment(String department) {
		this.department = department;
	}

	

	/**
	 * @return the fullBestTime
	 */
	public final String getFullBestTime() {
		return fullBestTime;
	}

	/**
	 * @param fullBestTime the fullBestTime to set
	 */
	public final void setFullBestTime(String fullBestTime) {
		this.fullBestTime = fullBestTime;
	}

	/**
	 * @return the halfBestTime
	 */
	public final String getHalfBestTime() {
		return halfBestTime;
	}

	/**
	 * @param halfBestTime the halfBestTime to set
	 */
	public final void setHalfBestTime(String halfBestTime) {
		this.halfBestTime = halfBestTime;
	}

	/**
	 * @return the funBestTime
	 */
	public final String getFunBestTime() {
		return funBestTime;
	}

	/**
	 * @param funBestTime the funBestTime to set
	 */
	public final void setFunBestTime(String funBestTime) {
		this.funBestTime = funBestTime;
	}

	/**
	 * @return the vip
	 */
	public final boolean isVip() {
		return vip;
	}

	/**
	 * @param vip the vip to set
	 */
	public final void setVip(boolean vip) {
		this.vip = vip;
	}

	/**
	 * @return the location
	 */
	public final String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public final void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the donation
	 */
//	public final List<Donation> getDonation() {
//		return donation;
//	}
//
//	/**
//	 * @param donation the donation to set
//	 */
//	public final void setDonation(List<Donation> donation) {
//		this.donation = donation;
//	}
}
