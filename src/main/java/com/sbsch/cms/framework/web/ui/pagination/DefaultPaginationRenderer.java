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
 * The Class DefaultPaginationRenderer.
 */
public class DefaultPaginationRenderer extends AbstractPaginationRenderer {
	
	/**
	 * Instantiates a new default pagination renderer.
	 */
	public DefaultPaginationRenderer() {
		firstPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}... </a>&#160;"; 
        previousPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"><<</a>&#160;";
        currentPageLabel = "<strong>{0}</strong>&#160;";
        otherPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>&#160;";
        nextPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">>></a>&#160;";
        lastPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"> ...{2}</a>&#160;";
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.web.ui.pagination.AbstractPaginationRenderer#renderPagination(com.sbsch.cms.framework.web.ui.pagination.PaginationInfo, java.lang.String)
	 */
	@Override
	public String renderPagination(PaginationInfo paginationInfo,
			String jsFunction) {
		
		return super.renderPagination(paginationInfo, jsFunction);
	}

}