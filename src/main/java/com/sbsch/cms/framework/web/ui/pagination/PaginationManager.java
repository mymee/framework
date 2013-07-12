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
 * The Interface PaginationManager.
 */

public interface PaginationManager {
	
	/**
	 * Gets the renderer type.
	 * 
	 * @param type the type
	 * @return the renderer type
	 */
	public PaginationRenderer getRendererType(String type);	
}
