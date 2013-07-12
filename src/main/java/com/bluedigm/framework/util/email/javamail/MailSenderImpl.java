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

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.bluedigm.framework.util.email.exception.SendMailException;
import com.bluedigm.framework.util.email.util.FileObject;


/**
 * The Class MailSenderImpl.
 */
public class MailSenderImpl {

	/**
	 * Send.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 * @return the mail
	 * @throws SendMailException the send mail exception
	 */
	public Mail send(Mail mail, MailContext ctx) throws SendMailException {
		Session session = ctx.getSession();
		Message mailMessage = new MimeMessage(session);
		try {

			// 보낸 사람 주소를 설정
			Address from = new InternetAddress(ctx.getUser() + " <" + ctx.getAccount() + ">");
			mailMessage.setFrom(from);
			// 모든 수신자의 주소를 설정
			Address[] to = getAddress(mail.getReceivers());
			if (to != null) {
				mailMessage.setRecipients(Message.RecipientType.TO, to);
			}
			// 참조 주소를 설정
			Address[] cc = getAddress(mail.getCcs());
			if (cc != null) {
				mailMessage.setRecipients(Message.RecipientType.CC, cc);
			}

			// 숨은 참조 주소를 설정
			Address[] bcc = getAddress(mail.getBccs());
			if (bcc != null) {
				mailMessage.setRecipients(Message.RecipientType.BCC, bcc);
			}

			// 제목설정
			mailMessage.setSubject(mail.getSubject());
			// 날짜설정
			mailMessage.setSentDate(mail.getReceiveDate());

			Multipart main = new MimeMultipart();
			// 본문
			BodyPart body = new MimeBodyPart();
			body.setContent(mail.getContent(), "text/html; charset=utf-8");
			main.addBodyPart(body);
			// 첨부 파일 처리
			for (FileObject f : mail.getFiles()) {
				MimeBodyPart fileBody = new MimeBodyPart();
				fileBody.attachFile(f.getFile());
				fileBody.setFileName(MimeUtility.encodeText(f.getSourceName()));
				main.addBodyPart(fileBody);
			}
			mailMessage.setContent(main);
			Transport.send(mailMessage);
			return mail;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SendMailException("Send a message error, check the mailbox configuration information and mail");
		}
	}

	/**
	 * Gets the address.
	 * 
	 * @param addList the add list
	 * @return the address
	 * @throws Exception the exception
	 */
	private Address[] getAddress(List<String> addList) throws Exception {
		if (addList == null || addList.size() == 0) {
			return null;
		}
		Address[] result = new Address[addList.size()];
		for (int i = 0; i < addList.size(); i++) {
			if (addList.get(i) == null || "".equals(addList.get(i)))
				continue;
			result[i] = new InternetAddress(addList.get(i));
		}
		return result;
	}
}
