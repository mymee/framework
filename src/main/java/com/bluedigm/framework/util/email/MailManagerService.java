/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.util.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluedigm.framework.util.email.util.MailConts;


/**
 * The Class MailManagerService.
 */
public class MailManagerService {
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(MailManagerService.class);

	/**
	 * Send mail.
	 * 
	 * @param mailInfo the mail info
	 * @param attachFiles the attach files
	 * @return true, if successful
	 */
	public static boolean sendMail(MailInfo mailInfo, String[] attachFiles) {

		if (attachFiles != null && attachFiles.length > 0) {
			for (String attachFile : attachFiles) {
				mailInfo.addAttachFile(attachFile);
			}
		}
		SendMail sendmail = new SendMail(mailInfo);
		return sendmail.sendMail();

	}

	/**
	 * Receive mail.
	 * 
	 * @param popHost the pop host
	 * @param userName the user name
	 * @param password the password
	 * @param attachDir the attach dir
	 * @return the mail box
	 */
	public static MailBox receiveMail(String popHost, String userName, String password, String attachDir) {
		List<MailInfo> mailInfoList = new ArrayList<MailInfo>();
		MailBox mailBox = new MailBox();
		mailBox.setMailList(mailInfoList);
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore(MailConts.POP_MAIL);
			store.connect(popHost, userName, password);
			Folder folder = store.getFolder(MailConts.FOLDER_TYPE);

			folder.open(Folder.READ_WRITE);
			Message message[] = folder.getMessages();
			System.out.println("Messages's length: " + message.length);
			mailBox.setMessageCount(folder.getMessageCount());
			mailBox.setNewMessageCount(folder.getNewMessageCount());
			mailBox.setUnreadMessageCount(folder.getUnreadMessageCount());

			ReceiveMail pmm = null;
			for (int i = 0; i < message.length; i++) {
				MailInfo mailInfo = new MailInfo();
				pmm = new ReceiveMail((MimeMessage) message[i]);
				mailInfo.setMessageID(pmm.getMessageId());
				mailInfo.setSubject(pmm.getSubject());
				mailInfo.setSentDate(pmm.getSentDate());
				mailInfo.setReplySign(pmm.getReplySign());
				mailInfo.setNewMail(pmm.isNew());
				mailInfo.setFrom(pmm.getFrom());
				mailInfo.setTo(new String[] { pmm.getMailAddress(MailConts.TO) });
				mailInfo.setCc(new String[] { pmm.getMailAddress(MailConts.CC) });
				mailInfo.setBcc(new String[] { pmm.getMailAddress(MailConts.BCC) });

				pmm.getMailContent((Part) message[i]);
				mailInfo.setContent(pmm.getBodyText());

				pmm.setAttachPath(attachDir);
				pmm.saveAttachMent((Part) message[i]);

				mailInfoList.add(mailInfo);
			}
			folder.close(false);
			store.close();
		} catch (Exception ex) {
			LOG.error("Incoming mail failed!");
		}
		return mailBox;
	}
}
