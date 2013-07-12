/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.util.email.util;

import java.io.File;


/**
 * The Class FileObject.
 */
public class FileObject {
	// 원본 파일 이름
	/** The source name. */
	private String sourceName;

	// 해당 파일
	/** The file. */
	private File file;

	/**
	 * Instantiates a new file object.
	 * 
	 * @param sourceName the source name
	 * @param file the file
	 */
	public FileObject(String sourceName, File file) {
		this.sourceName = sourceName;
		this.file = file;
	}

	/**
	 * Gets the source name.
	 * 
	 * @return the source name
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * Sets the source name.
	 * 
	 * @param sourceName the new source name
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 * 
	 * @param file the new file
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
