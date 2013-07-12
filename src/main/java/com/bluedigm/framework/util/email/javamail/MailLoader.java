/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.util.email.javamail;

import java.util.List;

import com.bluedigm.framework.util.email.exception.LoadMailException;
import com.bluedigm.framework.util.email.exception.MailConnectionException;


/**
 * The Interface MailLoader.
 */
public interface MailLoader {
	 
 	/**
	 * Gets the messages.
	 * 
	 * @param ctx the ctx
	 * @return the messages
	 * @throws LoadMailException the load mail exception
	 * @throws MailConnectionException the mail connection exception
	 */
 	public List<Mail> getMessages(MailContext ctx)throws LoadMailException,MailConnectionException ;
}
