/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.support;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.VfsResource;
import org.springframework.util.Assert;
import org.springframework.web.context.support.ServletContextResource;


/**
 * The Class ReloadableResource.
 */
public class ReloadableResource extends ReloadableResourceBundleMessageSource {
	
	/** The wild basenames. */
	private Resource[] wildBasenames;

	/** The Constant LOCALE_DELIMETER. */
	private static final String LOCALE_DELIMETER = "_";

	/** The Constant PROPOERTEY_SUBFIX. */
	private static final String PROPOERTEY_SUBFIX = ".properties";

	/** The Constant RES_DELIMETER. */
	private static final String RES_DELIMETER = "i18n/";

	/** The Constant CLASSPATH_URL_PREFIX. */
	private static final String CLASSPATH_URL_PREFIX = "classpath:";

	/**
	 * Sets the wild basenames.
	 * 
	 * @param wildBasenames the new wild basenames
	 */
	public void setWildBasenames(Resource[] wildBasenames) {
		if (wildBasenames != null) {
			this.wildBasenames = new Resource[wildBasenames.length];
			for (int i = 0; i < wildBasenames.length; i++) {
				Resource wildBasename = wildBasenames[i];
				Assert.notNull(wildBasename, "wildBasename must not be null");
				this.wildBasenames[i] = wildBasename;
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("wildBasenames is NOT setting");
			}
			return;
		}

		Set<String> basenames = new HashSet<String>();

		for (Resource wildBasename : this.wildBasenames) {

			String fullPath = "";
			if (wildBasename instanceof ServletContextResource) {
				ServletContextResource res = (ServletContextResource) wildBasename;
				fullPath = res.getPath();

			} else if (wildBasename instanceof FileSystemResource) {
				FileSystemResource res = (FileSystemResource) wildBasename;
				fullPath = res.getPath();
				// i18n 경로만 남긴다.
				fullPath = StringUtils.substringAfterLast(fullPath, RES_DELIMETER);
				// classpath:i18n/...
				fullPath = CLASSPATH_URL_PREFIX + RES_DELIMETER + fullPath;

			} else if (wildBasename instanceof ClassPathResource) {
				ClassPathResource res = (ClassPathResource) wildBasename;
				// classpath:i18n/...
				fullPath = CLASSPATH_URL_PREFIX + res.getPath();

			} else if (wildBasename instanceof UrlResource) {
				UrlResource res = (UrlResource) wildBasename;
				try {
					fullPath = res.getURL().getPath();
				} catch (IOException e) {
					throw new IllegalArgumentException(
							"ReloadableResource loaded UrlResource, but Failed to getURL cause IOException="
									+ e.getMessage());
				}
				// i18n 경로만 남긴다.
				fullPath = StringUtils.substringAfterLast(fullPath, RES_DELIMETER);
				// classpath:i18n/...
				fullPath = CLASSPATH_URL_PREFIX + RES_DELIMETER + fullPath;

			} else if (wildBasename instanceof VfsResource) {
				try {
					fullPath = wildBasename.getURL().getPath();
				} catch (IOException e) {
					throw new IllegalArgumentException(
							"ReloadableResource loaded UrlResource, but Failed to getURL cause IOException="
									+ e.getMessage());
				}

				// i18n 경로만 남긴다.
				fullPath = StringUtils.substringAfterLast(fullPath, RES_DELIMETER);
				// classpath:i18n/...
				fullPath = CLASSPATH_URL_PREFIX + RES_DELIMETER + fullPath;
			} else {
				throw new IllegalArgumentException(
						"ReloadableResource ONLY support for ServletContextResource, FileSystemResource, ClassPathResource, UrlResource: wildBasename is instanceof "
								+ wildBasename.getClass());
			}

			// 파일 이름만 가져오기
			String filename = org.springframework.util.StringUtils.getFilename(fullPath);
			// .properties 확장자 제거
			filename = StringUtils.substringBefore(filename, PROPOERTEY_SUBFIX);

			// 파일이름을 제외한 경로만 가져오기
			String purePath = StringUtils.substringBeforeLast(fullPath, filename);
			// _ko, _en 등을 제거하기
			String resFilename = StringUtils.substringBefore(filename, LOCALE_DELIMETER);

			// resource 경로 획득
			String resPath = purePath + resFilename;

			basenames.add(resPath);
		}

		// 중복된 스트링 제거
		this.setBasenames(basenames.toArray(new String[basenames.size()]));
	}
}
