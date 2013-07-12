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

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The Class MailThread.
 */
public class MailThread extends Thread{

	/** The message. */
	private MimeMessage message;

	/** The cnt. */
	private int cnt;

	/** The mailusers. */
	private ArrayList<String> mailusers = new ArrayList<String>();

	/**
	 * Instantiates a new mail thread.
	 * 
	 * @param mailusers the mailusers
	 * @param cnt the cnt
	 * @param message the message
	 */
	public MailThread(ArrayList<String> mailusers, int cnt, MimeMessage message) {
		System.out.println(mailusers.size());
		setDaemon(true);
		this.mailusers = mailusers;
		this.message = message;
		this.cnt = cnt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {

			//Thread.sleep(cnt * 2000);
			for (int i = 0; i < mailusers.size(); i++) {

				System.out.println("메일 받는 대상자 (" + i + ") : " + (String) mailusers.get(i));
				 InternetAddress[] tos =
				 InternetAddress.parse((String)mailusers.get(i));
				 message.setRecipients(Message.RecipientType.TO, tos);
				 Transport.send(message);
			}
			System.out.println(cnt + "번째 분할 메일 대상자들 발송 스레드 생성 완료 ");
			System.out.println("====================================");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
