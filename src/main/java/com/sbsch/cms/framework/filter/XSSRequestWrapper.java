/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

/**
 * The Class XSSRequestWrapper.
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
	
	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/** The patterns. */
	@SuppressWarnings("unused")
	private static Pattern[] patterns = new Pattern[] {
			// Script fragments
			Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			// src='...'
			Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL),
			// lonely script tags
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// eval(...)
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// expression(...)
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// javascript:...
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			// vbscript:...
			Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
			// query % ...
			//Pattern.compile("^%|[\\%]$", Pattern.CASE_INSENSITIVE),			
			// onload(...)=...
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

	/**
	 * Instantiates a new xSS request wrapper.
	 * 
	 * @param servletRequest the servlet request
	 */
	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}

		return encodedValues;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);

		return stripXSS(value);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletRequestWrapper#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return stripXSS(value);
	}

	/**
	 * Strip xss.
	 * 
	 * @param value the value
	 * @return the string
	 */
	private String stripXSS(String value) {
		if (value != null) {
//			// NOTE: It's highly recommended to use the ESAPI library and
//			// uncomment the following line to
//			// avoid encoded attacks.
//			//value = ESAPI.encoder().canonicalize(value);
//			value = HtmlUtils.htmlEscape(value);
//			//value = escapeHtml4(value);
//			// Avoid null characters
//			value = value.replaceAll("\0", "");
//			value = Pattern.compile("^%|[\\%]$", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("&#37;");
//			
//			//log.debug(value);
//			
//			// Remove all sections that match a pattern
//			for (Pattern scriptPattern : patterns) {
//				value = scriptPattern.matcher(value).replaceAll("");
//			}
		}
		return value;
	}
}
