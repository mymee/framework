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


/**
 * The Interface PaginationRenderer.
 */
public interface PaginationRenderer {
	
	/**
	 * Render pagination.
	 * 
	 * @param paginationInfo the pagination info
	 * @param jsFunction the js function
	 * @return the string
	 */
	public String renderPagination(PaginationInfo paginationInfo,String jsFunction);
	
}
