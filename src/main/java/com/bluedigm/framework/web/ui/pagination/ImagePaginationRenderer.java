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
 * The Class ImagePaginationRenderer.
 */
public class ImagePaginationRenderer extends AbstractPaginationRenderer {
 
	/**
	 * Instantiates a new image pagination renderer.
	 */
	public ImagePaginationRenderer() {
		firstPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}... </a>&#160;"; 
		previousPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"><img src=\"{2}/resources/images/common/btn_paging_pre.gif\" border=\"0\" alt=\"Prev\"/></a>&#160;";
		currentPageLabel = "<strong>{0}</strong>&#160;";
		otherPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>&#160;";
		nextPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"><img src=\"{2}/resources/images/common/btn_paging_next.gif\" border=\"0\" alt=\"Next\"/></a>&#160;";
		lastPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"> ...{2}</a>&#160;";
	}
}