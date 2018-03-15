package com.sap.csr.odata;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.csr.model.Attachment;
import com.sap.csr.model.Registration;
import com.sap.csr.model.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Arrays;

public class AttachmentMng extends HttpServlet implements ServiceConstant {
	static Logger logger = LoggerFactory.getLogger(AttachmentMng.class);
	private EntityManager em = null;

	// ??need check the lifecycle later
	public void doInit() {
		try {
			em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static String unicode2String(String unicode) {
		 
	    StringBuffer sb = new StringBuffer();
	 
	    String[] aHex = unicode.split("%");
	 
	    for (int i = 0; i < aHex.length; i++) {
	    	String hex = aHex[i];
	    	int data;
	    	if (hex.length()==0)
	    		continue;
	    	
	    	if (hex.charAt(0) == 'u') {
	    		//means the remain 4 is a unicode
	    		data = Integer.parseInt(hex.substring(1,5), 16);
	    		hex = hex.substring(5);
	    	} else {
	    		//means the continue 2 is a unicode
	    		data = Integer.parseInt(hex.substring(0,2),16);
	    		hex = hex.substring(2);
	    	}
	    	
	         // 追加成string
	    	sb.append((char) data);
	        
	        //then the remains
	        if (hex.length()>0) {
	        	sb.append(hex);
	        }
	    }
	 
	    return sb.toString();
	}
	
	private static String decodeUrlParam(String value) {
		StringBuffer sb = new StringBuffer();
		int len = value.length();
		for(int i=0; i<len; i++) {
			char chr = value.charAt(i);
			if (chr < 128) {
				sb.append(chr);
			} else {
				//it like %E6%9D%8E
				StringBuffer tmpSb = new StringBuffer("%");
				tmpSb.append( Integer.toHexString(chr));
				tmpSb.append("%");
				tmpSb.append( Integer.toHexString( value.charAt(i+1) ));
				tmpSb.append("%");
				tmpSb.append( Integer.toHexString( value.charAt(i+2) ));
				
				String unicodeStr = tmpSb.toString();
				String uniStr = "";
				
				try {
					uniStr = URLDecoder.decode(unicodeStr, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb.append(uniStr);
				i+=2;
			}
		}
		return sb.toString();
	}
	
	private Attachment getAttachmentInfo(HttpServletRequest request, boolean needContent) {
		Attachment ret = new Attachment(request.getParameter("UserId"), request.getParameter("Type"));

		
		if (needContent) {
			/*String fileName = request.getParameter("FileName");
			int c0=fileName.charAt(0);
			int c1=fileName.charAt(1);
			StringBuffer sb = new StringBuffer("file name char code ");
			for (int i=0; i<fileName.length();i++) {
				sb.append( String.valueOf(fileName.charAt(i)) + " ");
			}
			String chars = sb.toString();
			
			sb = new StringBuffer("char point: ");
			for (int i=0; i<fileName.length();i++) {
				sb.append( String.valueOf(fileName.codePointAt(i)) + " ");
			}
			String chars2 = sb.toString();
			
			logger.error("old string [" + fileName + "] char 0 1 " + String.valueOf(c0) + " " + String.valueOf(c1) + chars );
			
			String decodeFn = URLDecoder.decode(fileName);
			String decodeFn2 ="";
			try {
				decodeFn2 = URLDecoder.decode(fileName, "UTF-8");
				logger.error("two decode " + decodeFn + " --  " + decodeFn2 );
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				decodeFn2 = decodeFn;
			} 
			*/

			String uniFn = decodeUrlParam(request.getParameter("FileName"));
			
			//here the name in ui will be auto encoded, so here need do the decode 
//			ret.setFileName( URLDecoder.decode(request.getParameter("FileName")));
			ret.setFileName(uniFn);
			//ret.setMimeType(request.getParameter("MineType"));
			//now the mini type can get from content-type 
			ret.setMimeType(request.getContentType());
			
			ServletInputStream is;
			try {
				is = request.getInputStream();
				ret.setContent(this.readBody(is));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ret;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doInit();

		Attachment input = getAttachmentInfo(request, true);
		Query query = null;
		query = em.createNamedQuery(ATTACHMENT_BY_ID_AND_TYPE);
//		query.setParameter("userId", input.getAttachmentKey().getUserId());
//		query.setParameter("type", input.getAttachmentKey().getType());
		query.setParameter("userId", input.getUserId());
		query.setParameter("type", input.getType());
		 
//		String statement = "SELECT a from Attachment a where where a.attachmentKey.userId = "
//				+ input.getAttachmentKey().getUserId() + " and a.attachmentKey.type = " +
//				 input.getAttachmentKey().getType();
//		Query query = em.createQuery(statement);
				
		
		Attachment old = null;
		try {
			old = (Attachment) query.getSingleResult();
		} catch (NoResultException noResult) {
			old = null;
		} catch (Exception e) {
			Util.logException("upload attachment, call getSingleResult", e);
			old = null;
		}

		em.getTransaction().begin();
		if (old != null) {
			// existed, then just update
			old.updateProperty(input);
		} else {
			// need add new
			//Registration reg = new Registration();
			//reg.setUserId(input.getAttachmentKey().getUserId());
			//reg.setUserId(input.getUserId());
//			input.setRegistration(reg);
			
			em.persist(input);
		}
		em.getTransaction().commit();
		em.close();

		response.getWriter().println("OK");
	}

	private byte[] readBody(InputStream is) throws IOException {
		int capacity = 4096 * 1000;
		byte[] temp = new byte[capacity];

		ByteBuffer result = ByteBuffer.allocate(capacity);
		int read = is.read(temp);
		while (read >= 0) {
			if (result.remaining() < read) {
				ByteBuffer tmpResult = ByteBuffer.allocate(result.capacity() + capacity);
				//tmpResult.put(result);
				byte []oldArray = result.array();
//				logger.error("curent old result " + String.valueOf(oldArray.length) + " " + String.valueOf(result.position()));
				tmpResult.put(oldArray, 0, result.position());
				result = tmpResult;
			}
			result.put(temp, 0, read);
			read = is.read(temp);
		}

		return Arrays.copyOf(result.array(), result.position());
	}
	
	
//	int capacity = 1024 * 1000;
//	byte[] temp = new byte[capacity];
//
//	ByteBuffer result = ByteBuffer.allocate(capacity);
//	int read = is.read(temp);
//	while (read >= 0) {
//		if (result.remaining() < read) {
//			ByteBuffer tmpResult = ByteBuffer.allocate(result.capacity() + capacity);
//			tmpResult.put(result);  //==this is just copy the remain
//			result = tmpResult;
//		}
//		result.put(temp, 0, read);
//		read = is.read(temp);
//	}
//
//	return Arrays.copyOf(result.array(), result.position());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doInit();
		
		Attachment input = getAttachmentInfo(request, false);
		Query query = null;
		query = em.createNamedQuery(ATTACHMENT_BY_ID_AND_TYPE);
//		query.setParameter("userId", input.getAttachmentKey().getUserId());
//		query.setParameter("type", input.getAttachmentKey().getType());
		query.setParameter("userId", input.getUserId());
		query.setParameter("type", input.getType());

//		String statement = "SELECT a from Attachment a where where a.attachmentKey.userId = "
//				+ input.getAttachmentKey().getUserId() + " and a.attachmentKey.type = " +
//				 input.getAttachmentKey().getType();
//		Query query = em.createQuery(statement);
		
		Attachment old = null;
		String error = "";
		try {
			old = (Attachment) query.getSingleResult();
		} catch (NoResultException noResult) {
			old = null;
			error = "NoResultException";
		} catch (Exception e) {
			old = null;
			error = Util.logException("getSingleResult", e);
		} finally {
			em.close();
		}

		if (old == null) {
			response.setStatus(501);
			response.getWriter().println(error);
			return;
		}

		OutputStream out = response.getOutputStream();
		response.setContentType(old.getMimeType());
		out.write(old.getContent());
	}

	public AttachmentMng() {
		super();
	}

}
