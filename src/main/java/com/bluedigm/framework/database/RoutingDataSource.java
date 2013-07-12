/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * The Class RoutingDataSource.
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return ContextHolder.getDataSourceType();
	}
}
