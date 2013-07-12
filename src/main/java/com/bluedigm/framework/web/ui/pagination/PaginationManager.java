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
