/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.database;

/**
 * The Class ContextHolder.
 */
public class ContextHolder {
	
	/** The Constant contextHolder. */
	private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();
	
	/**
	 * Sets the data source type.
	 * 
	 * @param dataSourceType the new data source type
	 */
	public static void setDataSourceType(DataSourceType dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
	/**
	 * Gets the data source type.
	 * 
	 * @return the data source type
	 */
	public static DataSourceType getDataSourceType(){
		return contextHolder.get();
	}
	
	/**
	 * Clear data source type.
	 */
	public static void clearDataSourceType(){
		contextHolder.remove();
	}
}

