/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */

package com.bluedigm.framework.dao.impl;

import java.io.Serializable;
import java.util.List;



import com.bluedigm.framework.dao.GenericDao;
import com.bluedigm.framework.model.AbstractObject;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericDaoImpl.
 * 
 * @param <T> the generic type
 * @param <PK> the generic type
 */
public class GenericDaoImpl<T,PK extends Serializable> extends SqlSessionDaoSupport implements GenericDao<T, PK> {
	
	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
//	/** The namespace. */
//	private String namespace;
//	
//	@SuppressWarnings("unused")
//	private Class<T> modelClass;
//	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public GenericDaoImpl(){
//		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
//        Type type = genericSuperclass.getActualTypeArguments()[0];
//        if (type instanceof ParameterizedType) {
//            this.modelClass = (Class) ((ParameterizedType) type).getRawType();
//        } else {
//            this.modelClass = (Class) type;
//        }
//	}
	
//	/**
//	 * Statement.
//	 *
//	 * @param name the name
//	 * @return the string
//	 */
//	public String statement(String name) {
//		return this.getNamespace() + "." + name;
//	}
//	
//	/**
//	 * @param namespace the namespace to set
//	 */
//	public void setNamespace(String namespace) {
//		this.namespace = namespace;
//	}
//
//	/**
//	 * Gets the namespace.
//	 *
//	 * @return the namespace
//	 */
//	public String getNamespace() {
//		if(StringUtils.isEmpty(namespace)) {
//			for(Class<?> clazz : this.getClass().getInterfaces()) {
//				if(StringUtils.equals("GenericDao", clazz.getSimpleName())) {
//					continue;
//				} else {
//					namespace = clazz.getName();
//					break;
//				}
//			}
//		}
//		return this.namespace; 
//	}

	/* (non-Javadoc)
 * @see com.bluedigm.framework.dao.GenericDao#selectOne(java.lang.String, java.lang.Object)
 */
public Object selectOne(String statement, Object keys) {
//		if (id instanceof Map) {
//			Map map = (Map) id;
//			
//			return getSq lSession().selectOne(statement, map);
//		}else{
//			
//		}
		
		return getSqlSession().selectOne(statement, keys);

	}

	
	/* (non-Javadoc)
	 * @see com.bluedigm.framework.dao.GenericDao#selectList(java.lang.String)
	 */
	public List<T> selectList(String statement) {
		
		return getSqlSession().selectList(statement);
	}	

	/* (non-Javadoc)
	 * @see com.bluedigm.framework.dao.GenericDao#selectList(java.lang.String, java.lang.Object)
	 */
	public List<T> selectList(String statement, Object keys) {
		return getSqlSession().selectList(statement, keys);
	}

	/* (non-Javadoc)
	 * @see com.bluedigm.framework.dao.GenericDao#exists(java.lang.String, java.io.Serializable)
	 */
	public boolean exists(String statement, PK id) {
		
		Integer count = (Integer)getSqlSession().selectOne(statement, id);
		
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.bluedigm.framework.dao.GenericDao#insert(java.lang.String, java.lang.Object)
	 */
	public int insert(String statement, T object) {
		if(object instanceof AbstractObject){
			//((AbstractObject)object).setCreateID("Unknow");
			//((AbstractObject)object).setCreateSqlID(statement);
			//((AbstractObject)object).setCreateDateTime("");
			//((AbstractObject)object).setModifyID("Unknow");
			//((AbstractObject)object).setModifySqlID(statement);
			//((AbstractObject)object).setModifyDateTime("");
		}else{
		}
		return getSqlSession().insert(statement, object);
	}

	/* (non-Javadoc)
	 * @see com.bluedigm.framework.dao.GenericDao#update(java.lang.String, java.lang.Object)
	 */
	public int update(String statement, T object) {
		if(object instanceof AbstractObject){
//			((AbstractObject)object).setCreateID("");
//			((AbstractObject)object).setCreateSqlID("");
//			((AbstractObject)object).setCreateDateTime("");
			//((AbstractObject)object).setModifyID("Unknow");
			//((AbstractObject)object).setModifySqlID(statement);
			//((AbstractObject)object).setModifyDateTime("");
		}else{
		}
		return getSqlSession().update(statement, object);
	}

	/* (non-Javadoc)
	 * @see com.bluedigm.framework.dao.GenericDao#delete(java.lang.String, java.io.Serializable)
	 */
	public int delete(String statement, PK id) {
		return getSqlSession().delete(statement, id);
	}

	/* (non-Javadoc)
	 * @see com.bluedigm.framework.dao.GenericDao#delete(java.lang.String, java.lang.Object)
	 */
	public int delete(String statement, T object) {
		return getSqlSession().delete(statement, object);
	}
	

	
	
}
