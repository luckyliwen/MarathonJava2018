package com.sap.csr.model;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import com.sap.csr.odata.ServiceConstant;

@IdClass(AttachmentPK.class)
@Entity(name="Attachment")
@NamedQueries({ 
    @NamedQuery(name=ServiceConstant.ATTACHMENT_BY_ID_AND_TYPE,query="select a from Attachment a where a.userId = :userId and a.type= :type"),
    @NamedQuery(name=ServiceConstant.ATTACHMENT_BY_ID, query="select a from Attachment a where a.userId = :userId")
}) 

public class Attachment implements Serializable {

	private static long serialVersionUID = 1L;
	
   
	
    @Id
    @Column(name = "ATT_USERID", length = 10, nullable = false)
    private String userId;
    
    @Id
    private String type;  //now only accept the photo,  id, form  
    
    //private java.sql.Time  modifiedTime;
	
	//@EmbeddedId 
	//private AttachmentKey  attachmentKey;
    
    private String fileName;
    private String mimeType;
    
    //@Transient
//    private String theUserId;
    
   private byte [] content;
    //@Lob
    //private java.sql.Blob content;
    
   
   //@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
   
//   @JoinColumn(name="ATT_USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
//   @ManyToOne
//   private Registration registration;
   
  
    public Attachment() {
    	super();
    	//attachmentPK = new AttachmentPK();
    }
    
    public Attachment(String userId, String type){
    	super();
    	
    	//attachmentKey = new AttachmentKey(userId, type);
    	
    	//attachmentPK = new AttachmentPK(userId, type);
    	this.userId = userId;
    	this.type = type;
    }
    
	/**
	 * @return the userId
	 */
//	public final String getUserId() {
//		return userId;
//	}
//	/**
//	 * @param userId the userId to set
//	 */
//	public final void setUserId(String userId) {
//		this.userId = userId;
//	}
	/**
	 * @return the type
	 */
//	public final String getType() {
//		return type;
//	}
//	/**
//	 * @param type the type to set
//	 */
//	public final void setType(String type) {
//		this.type = type;
//	}
	/**
	 * @return the modifiedTime
	 */
//	public final java.sql.Time getModifiedTime() {
//		return modifiedTime;
//	}
//	/**
//	 * @param modifiedTime the modifiedTime to set
//	 */
//	public final void setModifiedTime(java.sql.Time modifiedTime) {
//		this.modifiedTime = modifiedTime;
//	}
	/**
	 * @return the fileName
	 */
	public final String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public final void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the mimeType
	 */
	public final String getMimeType() {
		return mimeType;
	}
	/**
	 * @param mimeType the mimeType to set
	 */
	public final void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	/**
	 * @return the content
	 */
	public final byte[] getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public final void setContent(byte[] content) {
		this.content = content;
	}

	public void updateProperty(final Attachment newAttachment) {
		setFileName(newAttachment.getFileName());
		setMimeType(newAttachment.getMimeType());
		setContent(newAttachment.getContent());
	}
	/**
	 * @return the content
	 */
//	public final java.sql.Blob getContent() {
//		return content;
//	}
//
//	/**
//	 * @param content the content to set
//	 */
//	public final void setContent(java.sql.Blob content) {
//		this.content = content;
//	}

//	/**
//	 * @return the registration
//	 */
//	public final Registration getRegistration() {
//		return registration;
//	}
//
//	/**
//	 * @param registration the registration to set
//	 */
//	public final void setRegistration(Registration registration) {
//		this.registration = registration;
//	}

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
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(String type) {
		this.type = type;
	}

	public String createDownloadFileName(String userId) {
		String name = userId +"_" + type;
		//if the file name is . like a.pdf then directly use the suffix, otherwise use the mime
		int pos = fileName.lastIndexOf(".");
		if (pos != -1) {
			return name + fileName.substring(pos);
		} else {
			//
			return name;
		}
	}
	
//	/**
//	 * @return the attachmentKey
//	 */
//	public final AttachmentKey getAttachmentKey() {
//		return attachmentKey;
//	}
//
//	/**
//	 * @param attachmentKey the attachmentKey to set
//	 */
//	public final void setAttachmentKey(AttachmentKey attachmentKey) {
//		this.attachmentKey = attachmentKey;
//	}


//
//	/**
//	 * @return the type
//	 */
//	public final String getType() {
//		return type;
//	}
//
//	/**
//	 * @param type the type to set
//	 */
//	public final void setType(String type) {
//		this.type = type;
//	}

//	/**
//	 * @return the attachmentPK
//	 */
//	public final AttachmentPK getAttachmentPK() {
//		return attachmentPK;
//	}
//
//	/**
//	 * @param attachmentPK the attachmentPK to set
//	 */
//	public final void setAttachmentPK(AttachmentPK attachmentPK) {
//		this.attachmentPK = attachmentPK;
//	}
	
}