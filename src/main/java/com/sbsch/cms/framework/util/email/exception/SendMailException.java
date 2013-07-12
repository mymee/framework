/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.util.email.exception;

/**
 * The Class SendMailException.
 */
public class SendMailException extends Exception {
    
    /**
	 *
	 */
	private static final long serialVersionUID = 4181273453107165385L;

	/**
	 * Instantiates a new send mail exception.
	 */
    public SendMailException() {
    }

    /**
	 * Instantiates a new send mail exception.
	 * 
	 * @param message the message
	 */
    public SendMailException(String message) {
            super(message);
    }

    /**
	 * Instantiates a new send mail exception.
	 * 
	 * @param cause the cause
	 */
    public SendMailException(Throwable cause) {
            super(cause);
    }

    /**
	 * Instantiates a new send mail exception.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
    public SendMailException(String message, Throwable cause) {
            super(message, cause);
    }
}
