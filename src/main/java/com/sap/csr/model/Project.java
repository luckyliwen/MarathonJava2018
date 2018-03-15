package com.sap.csr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.sap.csr.odata.ServiceConstant;

@Entity(name="Project")
@NamedQueries({ 
	@NamedQuery(name=ServiceConstant.PROJECT_BY_MARATHON, query="select p from Project p where p.projectName = \"marathon2018\""),
})

public class Project implements Serializable {
	@Id
	private String projectName;
	private String vipIds;
	
//	private int maxRegisterNum;
	private int freeVipNum;  //reversed vip ids, then not enough regisger, so can add to the maxRegisterNum
	
	private int  maxRegGcoNum, maxRegLabsNum, maxRegDbsNum;
	
	public Project() {
		
	}

	/**
	 * @return the projectName
	 */
	public final String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public final void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the vipIds
	 */
	public final String getVipIds() {
		return vipIds;
	}

	/**
	 * @param vipIds the vipIds to set
	 */
	public final void setVipIds(String vipIds) {
		this.vipIds = vipIds;
	}

	
	/**
	 * @return the freeVipNum
	 */
	public final int getFreeVipNum() {
		return freeVipNum;
	}

	/**
	 * @param freeVipNum the freeVipNum to set
	 */
	public final void setFreeVipNum(int freeVipNum) {
		this.freeVipNum = freeVipNum;
	}
	
	//for 2018 as capacity will be devided into several department, so freeVip
	//??
	public int getTotalNormalCapacity() {
		return maxRegGcoNum + maxRegLabsNum + maxRegDbsNum;
	}
	
	
	public int getMaxRegGcoNum() {
		return maxRegGcoNum;
	}

	public void setMaxRegGcoNum(int maxRegGcoNum) {
		this.maxRegGcoNum = maxRegGcoNum;
	}

	public int getMaxRegLabsNum() {
		return maxRegLabsNum;
	}

	public void setMaxRegLabsNum(int maxRegLabsNum) {
		this.maxRegLabsNum = maxRegLabsNum;
	}

	public int getMaxRegDbsNum() {
		return maxRegDbsNum;
	}

	public void setMaxRegDbsNum(int maxRegDbsNum) {
		this.maxRegDbsNum = maxRegDbsNum;
	}
	
}

