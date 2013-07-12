/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */

package com.sbsch.cms.framework.core;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * A factory for creating ApplicationContext objects.
 */
public class ApplicationContextFactory implements ServletContextAware {
	
	/** The log. */
	protected static Logger log = LoggerFactory.getLogger(ApplicationContextFactory.class);

	/** The init obj. */
	private static Object initObj = null;

	/** The count. */
	private static int count = 0;

	/** The context. */
	private static ApplicationContext context;

	/**
	 * Inits the.
	 * 
	 * @param o the o
	 */
	public static void init(Object o) {
		if (count > 0) {
			log.error("Can't initialize the application context twice: THIS SHOULD ONLY HAPPEN DURING TESTING");
		}
		initObj = o;
		count++;
	}

	/**
	 * Gets the application context.
	 * 
	 * @return the application context
	 */
	public static ApplicationContext getApplicationContext() {
		if (initObj == null) {
			throw new IllegalStateException("Application context not initialized");
		} else if (initObj instanceof ServletContext) {
			ServletContext servletContext = (ServletContext) initObj;
			return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

		} else if (initObj instanceof String) {
			if (context == null) {
				String contextResourceLocation = (String) initObj;
				context = new ClassPathXmlApplicationContext(contextResourceLocation);
			}
			return context;
		} else {
			throw new IllegalStateException("You must initialize the context with a String");
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext context) {
		init(context);
	}
}
