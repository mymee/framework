/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.dao;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface GenericDao.
 * 
 * @param <T> the generic type
 * @param <PK> the generic type
 */
public interface GenericDao<T, PK extends Serializable> {
	
	/**
	 * Select one.
	 * 
	 * @param statement the statement
	 * @param keys the keys
	 * @return the object
	 */
	Object selectOne(String statement, Object keys);

	/**
	 * Select list.
	 * 
	 * @param statement the statement
	 * @param keys the keys
	 * @return the list
	 */

	List<T> selectList(String statement, Object keys);

	/**
	 * Select list.
	 * 
	 * @param statement the statement
	 * @return the list
	 */
	List<T> selectList(String statement);

	/**
	 * Exists.
	 * 
	 * @param statement the statement
	 * @param primaryKey the primary key
	 * @return true, if successful
	 */
	boolean exists(String statement, PK primaryKey);

	/**
	 * Insert.
	 * 
	 * @param statement the statement
	 * @param object the object
	 * @return the int
	 */
	int insert(String statement, T object);

	/**
	 * Update.
	 * 
	 * @param statement the statement
	 * @param object the object
	 * @return the int
	 */
	int update(String statement, T object);

	/**
	 * Delete.
	 * 
	 * @param statement the statement
	 * @param primaryKey the primary key
	 * @return the int
	 */
	int delete(String statement, PK primaryKey);
	
	
	/**
	 * Delete.
	 * 
	 * @param statement the statement
	 * @param object the object
	 * @return the int
	 */
	int delete(String statement, T object);

}
