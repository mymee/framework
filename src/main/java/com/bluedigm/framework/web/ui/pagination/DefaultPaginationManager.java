/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.web.ui.pagination;

import java.util.Map;

/**
 * The Class DefaultPaginationManager.
 */

public class DefaultPaginationManager implements PaginationManager{
	
	/** The renderer type. */
	private Map<String, PaginationRenderer> rendererType;
	
	/**
	 * Sets the renderer type.
	 * 
	 * @param rendererType the new renderer type
	 */
	public void setRendererType(Map<String, PaginationRenderer> rendererType) {
		this.rendererType = rendererType;
	}
	
	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.web.ui.pagination.PaginationManager#getRendererType(java.lang.String)
	 */
	public PaginationRenderer getRendererType(String type) {
		
		return (rendererType!=null && rendererType.containsKey(type)) ? (PaginationRenderer) rendererType.get(type):new DefaultPaginationRenderer();		
	}	
	
}
