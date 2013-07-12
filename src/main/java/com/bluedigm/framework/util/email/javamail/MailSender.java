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

import com.bluedigm.framework.util.email.exception.SendMailException;

/**
 * The Interface MailSender.
 */
public interface MailSender {
	
	/**
	 * Send.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 * @return the mail
	 * @throws SendMailException the send mail exception
	 */
	public Mail send(Mail mail, MailContext ctx) throws SendMailException;
}
