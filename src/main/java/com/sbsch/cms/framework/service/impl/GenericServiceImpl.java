/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.service.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.sbsch.cms.framework.dao.GenericDao;
import com.sbsch.cms.framework.service.GenericService;


/**
 * The Class GenericServiceImpl.
 * 
 * @param <T> the generic type
 * @param <PK> the generic type
 */
@Transactional
public class GenericServiceImpl<T, PK extends Serializable> implements GenericService<T, PK> {
	
	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/** The dao. */
	protected GenericDao<T, PK> dao;

	/**
	 * Instantiates a new generic service impl.
	 */
	public GenericServiceImpl() {}

	/**
	 * Instantiates a new generic service impl.
	 * 
	 * @param genericDao the generic dao
	 */
	public GenericServiceImpl(GenericDao<T, PK> genericDao) {
		this.dao = genericDao;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.service.GenericService#get(java.io.Serializable)
	 */
	public T get(PK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(PK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.service.GenericService#create(java.lang.Object)
	 */
	public int create(T object) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.service.GenericService#modify(java.lang.Object)
	 */
	public void modify(T object) {

		
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.service.GenericService#remove(java.io.Serializable)
	 */
	public void remove(PK id) {

		
	}

	
	
}
