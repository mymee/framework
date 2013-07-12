/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class AbstractObject.
 */

@XmlTransient
@JsonAutoDetect
public abstract class AbstractObject {

	/** The create id. */
	@XmlTransient
	@JsonIgnore
	private String createID;
	
	/** The create sql id. */
//	@XmlTransient
//	@JsonIgnore
//	private String createSqlID;
	
	/** The create date time. */
	@XmlTransient
	@JsonIgnore
	private Timestamp createDateTime;
	
	/** The modify id. */
	@XmlTransient
	@JsonIgnore
	private String modifyID;
	
	/** The modify sql id. */
//	@XmlTransient
//	@JsonIgnore
//	private String modifySqlID;
	
	/** The modify date time. */
	@XmlTransient
	@JsonIgnore
	private Timestamp modifyDateTime;
	
	/**
	 * Gets the creates the id.
	 * 
	 * @return the creates the id
	 */
	public String getCreateID() {
		return createID;
	}

	/**
	 * Sets the creates the id.
	 * 
	 * @param createID the new creates the id
	 */
	public void setCreateID(String createID) {
		this.createID = createID;
	}

	/**
	 * Gets the creates the sql id.
	 * 
	 * @return the creates the sql id
	 */
//	public String getCreateSqlID() {
//		return createSqlID;
//	}

	/**
	 * Sets the creates the sql id.
	 * 
	 * @param createSqlID the new creates the sql id
	 */
//	public void setCreateSqlID(String createSqlID) {
//		this.createSqlID = createSqlID;
//	}

	/**
	 * Gets the creates the date time.
	 * 
	 * @return the creates the date time
	 */
	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * Sets the creates the date time.
	 * 
	 * @param createDateTime the new creates the date time
	 */
	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * Gets the modify id.
	 * 
	 * @return the modify id
	 */
	public String getModifyID() {
		return modifyID;
	}

	/**
	 * Sets the modify id.
	 * 
	 * @param modifyID the new modify id
	 */
	public void setModifyID(String modifyID) {
		this.modifyID = modifyID;
	}

	/**
	 * Gets the modify sql id.
	 * 
	 * @return the modify sql id
	 */
//	public String getModifySqlID() {
//		return modifySqlID;
//	}

	/**
	 * Sets the modify sql id.
	 * 
	 * @param modifySqlID the new modify sql id
	 */
//	public void setModifySqlID(String modifySqlID) {
//		this.modifySqlID = modifySqlID;
//	}

	/**
	 * Gets the modify date time.
	 * 
	 * @return the modify date time
	 */
	public Timestamp getModifyDateTime() {
		return modifyDateTime;
	}

	/**
	 * Sets the modify date time.
	 * 
	 * @param modifyDateTime the new modify date time
	 */
	public void setModifyDateTime(Timestamp modifyDateTime) {
		this.modifyDateTime = modifyDateTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
