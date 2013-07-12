/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.web;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * The Class AbstractController.
 */
public class AbstractController {
	
	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/** The message source. */
	@Autowired
	protected MessageSource messageSource = null;

	/**
	 * Gets the message.
	 * 
	 * @param messageCode the message code
	 * @param messageParameters the message parameters
	 * @param defaultMessage the default message
	 * @return the message
	 */
	public String getMessage(String messageCode, Object[] messageParameters, String defaultMessage) {
		Locale locale = Locale.getDefault();
		return getMessage(messageCode, messageParameters, defaultMessage, locale);
	}

	/**
	 * Gets the message.
	 * 
	 * @param messageCode the message code
	 * @param messageParameters the message parameters
	 * @param defaultMessage the default message
	 * @param locale the locale
	 * @return the message
	 */
	public String getMessage(String messageCode, Object[] messageParameters, String defaultMessage, Locale locale) {
		return this.messageSource.getMessage(messageCode, messageParameters, defaultMessage, locale);
	}

	/**
	 * Gets the request attribute.
	 * 
	 * @param name the name
	 * @return the request attribute
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * Sets the request attribute.
	 * 
	 * @param name the name
	 * @param value the value
	 */
	public void setRequestAttribute(String name, Object value) {
		RequestContextHolder.currentRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * Gets the web application context.
	 * 
	 * @return the web application context
	 */
	public WebApplicationContext getWebApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}
}
