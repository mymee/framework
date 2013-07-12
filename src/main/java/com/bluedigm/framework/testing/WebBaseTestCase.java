/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.testing;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.context.WebApplicationContext;


/**
 * The Class WebBaseTestCase.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestGenericWebXmlContextLoader.class, locations = {
		"classpath*:/conf/spring/applicationContext-common.xml",
		"classpath*:/conf/spring/applicationContext-database.xml",
		"classpath*:/conf/spring/applicationContext-message.xml",
		"classpath*:/conf/spring/applicationContext-rest.xml", "classpath*:/conf/spring/applicationContext-web.xml",
		"classpath*:/conf/spring/applicationContext-Config.xml", "classpath:/conf/spring/webApplication-servlet.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class WebBaseTestCase {
	
	// Initialize Log4J configuration.
	{
		try {
			Log4jConfigurer.initLogging("classpath:conf/log/log4j.xml");
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException("Unable to initialize Log4J configuration.", fnfe);
		}
	}
	
	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/** The wac. */
	@Autowired
	protected WebApplicationContext wac;

	/** The mock mvc. */
	protected MockMvc mockMvc;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
}
