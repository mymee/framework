/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.web.ui.pagination;


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
