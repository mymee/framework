/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.testing;

import javax.servlet.RequestDispatcher;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * The Class GenericWebXmlContextLoader.
 */
public class GenericWebXmlContextLoader extends AbstractContextLoader {

	/** The servlet context. */
	private final MockServletContext servletContext;

	/**
	 * Instantiates a new generic web xml context loader.
	 * 
	 * @param warRootDir the war root dir
	 * @param isClasspathRelative the is classpath relative
	 */
	public GenericWebXmlContextLoader(String warRootDir, boolean isClasspathRelative) {
		ResourceLoader resourceLoader = isClasspathRelative ? new DefaultResourceLoader() : new FileSystemResourceLoader();
		this.servletContext = initServletContext(warRootDir, resourceLoader);
	}

	/**
	 * Inits the servlet context.
	 * 
	 * @param warRootDir the war root dir
	 * @param resourceLoader the resource loader
	 * @return the mock servlet context
	 */
	private MockServletContext initServletContext(String warRootDir, ResourceLoader resourceLoader) {
		return new MockServletContext(warRootDir, resourceLoader) {
			// Required for DefaultServletHttpRequestHandler...
			public RequestDispatcher getNamedDispatcher(String path) {
				return (path.equals("default")) ? new MockRequestDispatcher(path) : super.getNamedDispatcher(path); 
			}			
		};
	}

	/* (non-Javadoc)
	 * @see org.springframework.test.context.SmartContextLoader#loadContext(org.springframework.test.context.MergedContextConfiguration)
	 */
	public ApplicationContext loadContext(MergedContextConfiguration mergedConfig) throws Exception {
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		context.getEnvironment().setActiveProfiles(mergedConfig.getActiveProfiles());
		prepareContext(context);
		new XmlBeanDefinitionReader(context).loadBeanDefinitions(mergedConfig.getLocations());
		AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
		context.refresh();
		context.registerShutdownHook();
		return context;
	}

	/* (non-Javadoc)
	 * @see org.springframework.test.context.ContextLoader#loadContext(java.lang.String[])
	 */
	public ApplicationContext loadContext(String... locations) throws Exception {
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		prepareContext(context);
		new XmlBeanDefinitionReader(context).loadBeanDefinitions(locations);
		AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
		context.refresh();
		context.registerShutdownHook();
		return context;
	}

	/**
	 * Prepare context.
	 * 
	 * @param context the context
	 */
	protected void prepareContext(GenericWebApplicationContext context) {
		this.servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
		context.setServletContext(this.servletContext);
	}

	/* (non-Javadoc)
	 * @see org.springframework.test.context.support.AbstractContextLoader#getResourceSuffix()
	 */
	@Override
	protected String getResourceSuffix() {
		return "-servlet.xml";
	}

}