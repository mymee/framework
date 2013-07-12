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


/**
 * The Class TestGenericWebXmlContextLoader.
 */
public class TestGenericWebXmlContextLoader extends GenericWebXmlContextLoader {

	/**
	 * Instantiates a new test generic web xml context loader.
	 * 
	 * @param warRootDir the war root dir
	 * @param isClasspathRelative the is classpath relative
	 */
	public TestGenericWebXmlContextLoader(String warRootDir, boolean isClasspathRelative) {
		super(warRootDir, isClasspathRelative);
	}

	/**
	 * Instantiates a new test generic web xml context loader.
	 */
	public TestGenericWebXmlContextLoader() {
		super("/src/main/wepapp", false);
	}
}