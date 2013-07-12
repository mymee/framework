
 /*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */


package com.sbsch.cms.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ApplicationException.
 */
public class ApplicationException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8275931131749898882L;

	/** The log. */
	public Logger log = LoggerFactory.getLogger(ApplicationException.class.getName());
	
	/** The error message. */
	private String errorMessage = "";

    /**
	 * Instantiates a new application exception.
	 */
    public ApplicationException(){
    	super();
    }
    
    /**
	 * Instantiates a new application exception.
	 * 
	 * @param errorCode the error code
	 */
    public ApplicationException(String errorCode){
    	super(errorCode);
    	this.errorMessage = errorCode;
    	log.error(this.errorMessage);
    }
    
    /**
	 * Instantiates a new application exception.
	 * 
	 * @param throwable the throwable
	 */
    public ApplicationException(Throwable throwable){
    	super(throwable);
    	this.errorMessage = throwable.getMessage();
    	log.error(this.errorMessage);
    }
    
    /**
	 * Instantiates a new application exception.
	 * 
	 * @param errorCode the error code
	 * @param throwable the throwable
	 */
	
    public ApplicationException(String errorCode, Throwable throwable) {

        super(errorCode, throwable);
        this.errorMessage = errorCode + "  " + throwable.getMessage();
        log.error(this.errorMessage);
    }
    
    /**
	 * Sets the error message.
	 * 
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error message.
	 * 
	 * @return the error message
	 */
    public String getErrorMessage() {
        return errorMessage;
    }
}
