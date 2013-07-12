/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.service;

import java.io.Serializable;


/**
 * The Interface GenericService.
 * 
 * @param <T> the generic type
 * @param <PK> the generic type
 */
public interface GenericService<T, PK extends Serializable> {
	
	/**
	 * Gets the.
	 * 
	 * @param id the id
	 * @return the t
	 */
	T get(PK id);

	/**
	 * Exists.
	 * 
	 * @param id the id
	 * @return true, if successful
	 */
	boolean exists(PK id);

	/**
	 * Creates the.
	 * 
	 * @param object the object
	 * @return the int
	 */
	int create(T object);

	/**
	 * Modify.
	 * 
	 * @param object the object
	 */
	void modify(T object);

	/**
	 * Removes the.
	 * 
	 * @param id the id
	 */
	void remove(PK id);
}
