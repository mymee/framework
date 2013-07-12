/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.util.email.exception;

/**
 * The Class LoadMailException.
 */
public class LoadMailException extends Exception{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
	 * Instantiates a new load mail exception.
	 */
    public LoadMailException(){
            super();
    }
    
    /**
	 * Instantiates a new load mail exception.
	 * 
	 * @param message the message
	 */
    public LoadMailException(String message){
            super(message);
    }
}
