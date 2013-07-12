/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.util.email.util;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * The Class SendMailUtil.
 */
public class SendMailUtil {
	
	/** The smtp host. */
	private String smtpHost;

	/** The to. */
	private String to;

	/** The users. */
	private String users;

	/** The subject. */
	private String subject;

	/** The from. */
	private String from;

	/** The content. */
	private String content;

	/** The props. */
	Properties props = new Properties();

	/**
	 * Instantiates a new send mail util.
	 */
	public SendMailUtil() {

	}

	/**
	 * Instantiates a new send mail util.
	 * 
	 * @param smtpHost the smtp host
	 * @param to the to
	 * @param from the from
	 * @param subject the subject
	 * @param content the content
	 */
	public SendMailUtil(String smtpHost, String to, String from, String subject, String content) {
		this.smtpHost = smtpHost;
		this.to = to.replaceAll(";", ",");
		this.from = from;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * Sets the cc.
	 * 
	 * @param users the new cc
	 */
	public void setCc(String users) {
		this.users = users.replaceAll(";", ",");
	}

	/**
	 * Send mail.
	 * 
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendMail() throws Exception {

		boolean success = false;

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "mail.test.co.kr");
			props.put("mail.smtp.socketFactory.port", "25");
//			props.put("mail.smtp.socketFactory.class",
//					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.port", "465");
	 
			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("administrator","administrator");
					}
				});			
			//Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, "text/html;charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject, "UTF-8");
			message.setContent(multipart);

			if (smtpHost.equals("")) {
				props.put("mail.host", "mail.zz.com");
			} else {
				props.put("mail.host", smtpHost);
			}

			ArrayList<String> mailusers = new ArrayList<String>();

			if (users != null) {

				System.out.println("=============================");

				String[] token = users.split(",");
				System.out.println("대량 메일 발송 start.... 총 " + token.length + "명 발송 예정");
				int cnt = 0;
				for (int i = 0; i < token.length; i++) {

					to = token[i];
					mailusers.add(to);

					if (mailusers.size() == 100) {
						cnt = cnt + 1;
						MailThread transport = new MailThread(mailusers, cnt, message);
						transport.start();
						mailusers = new ArrayList<String>();

					}

				}

				/* 사용자의 수가 100이 넘지 못했을때 처리 */
				if (0 < mailusers.size()) {
					cnt = cnt + 1;
					MailThread transport = new MailThread(mailusers, cnt, message);
					transport.start();
					mailusers = new ArrayList<String>();
				}

				System.out.println("대량 메일 발송 end....");
				System.out.println("=============================");
			} else {
				mailusers.add(to);
				System.out.println("=============================");
				System.out.println("Thread start....");
				MailThread transport = new MailThread(mailusers, 1, message);
				transport.start();
				System.out.println("Thread end....");
				System.out.println("=============================");
			}
			success = true;

		} catch (Exception e) {
			success = false;
			System.out.println(e);
		}

		return success;
	}

}
