package com.sap.csr.odata;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfoMng extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long sum = getTotalDonation();
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().print(String.valueOf(sum));
		response.flushBuffer();
	}

	public long getTotalDonation() {
		EntityManager em;
		try {
			em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
			String queryStr =  "select sum(d.amount) from Donation d ";
			Query query = em.createQuery(queryStr);
			Object sum = null;
			try {
				sum= query.getSingleResult();
				return ((Long)sum).longValue();
			} catch(Exception e) {
				return 0;
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
		
	}
}
