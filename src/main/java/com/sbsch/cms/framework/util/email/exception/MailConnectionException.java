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
 * The Class MailConnectionException.
 */
public class MailConnectionException extends Exception {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
	 * Instantiates a new mail connection exception.
	 */
    public MailConnectionException(){
            super();
    }
    
    /**
	 * Instantiates a new mail connection exception.
	 * 
	 * @param message the message
	 */
    public MailConnectionException(String message){
            super(message);
    }
}
