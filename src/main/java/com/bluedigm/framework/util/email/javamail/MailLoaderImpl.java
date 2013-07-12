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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.bluedigm.framework.util.email.exception.LoadMailException;
import com.bluedigm.framework.util.email.exception.MailConnectionException;
import com.bluedigm.framework.util.email.folder.SystemHandler;
import com.bluedigm.framework.util.email.folder.SystemHandlerImpl;
import com.bluedigm.framework.util.email.util.FileObject;
import com.bluedigm.framework.util.email.util.FileUtil;
import com.bluedigm.framework.util.email.util.MailComparator;
import com.bluedigm.framework.util.email.util.MailConts;


/**
 * The Class MailLoaderImpl.
 */
public class MailLoaderImpl implements MailLoader {
	
	/** The bodytext. */
	private StringBuffer bodytext = new StringBuffer();

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.javamail.MailLoader#getMessages(com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public List<Mail> getMessages(MailContext ctx) throws LoadMailException, MailConnectionException {
		List<Mail> mailList = new ArrayList<Mail>();
		// INBOX 받은편지함
		Store store = ctx.getStore();
		Folder inbox = null;
		try {
			inbox = store.getFolder("INBOX");
		} catch (Exception e) {
			throw new LoadMailException("Load the mailbox error, please check the configuration");
		}
		try {
			inbox.open(Folder.READ_WRITE);
			Message[] messages = inbox.getMessages();
			mailList = this.getMailList(ctx, messages);
			sort(mailList);

			deleteFromServer(messages);
			inbox.close(true);
			store.close();
			return mailList;
		} catch (Exception e) {
			throw new LoadMailException(e.getMessage());
		}
	}

	/**
	 * Sort.
	 * 
	 * @param mails the mails
	 */
	private void sort(List<Mail> mails) {
		Collections.sort(mails, new MailComparator());
	}

	/**
	 * Gets the mail list.
	 * 
	 * @param ctx the ctx
	 * @param messages the messages
	 * @return the mail list
	 * @throws LoadMailException the load mail exception
	 */
	private List<Mail> getMailList(MailContext ctx, Message[] messages) throws LoadMailException {
		List<Mail> result = new ArrayList<Mail>();
		SystemHandler handler = new SystemHandlerImpl();
		try {
			for (Message m : messages) {
				MimeMessage message = (MimeMessage) m;
				Mail mail = new Mail();
				result.add(mail);
				String xmlName = UUID.randomUUID().toString() + ".xml";
				// getMailContent((Part) message);
				// String content = this.getBodyText();
				StringBuffer sb = new StringBuffer();
				String content = this.getContent((Part) message, sb).toString();

				mail.setXmlName(xmlName);
				mail.setSender(this.getSender(message));
				mail.setReceivers(this.getMailAddress(MailConts.TO, message));
				mail.setSubject(this.getSubject(message));
				// mail.setSize(FileBean.getSize(message.getSize()));
				mail.setReceiveDate(this.getReceivedDate(message));
				mail.setContent(content);
				mail.setCcs(this.getMailAddress(MailConts.CC, message));
				mail.setBccs(this.getMailAddress(MailConts.BCC, message));
				mail.setHasRead(this.isNew(message));
				mail.setReplySign(this.getReplySign(message));
				mail.setContainAttach(this.isContainAttach((Part) message));
				mail.setFiles(this.getFiles(ctx, message));
				mail.setFrom(FileUtil.INBOX);
				handler.saveInBox(mail, ctx);
			}
		} catch (LoadMailException e) {
			throw new LoadMailException("The mail, an error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Gets the sender.
	 * 
	 * @param message the message
	 * @return the sender
	 * @throws Exception the exception
	 */
	private String getSender(MimeMessage message) throws Exception {
		InternetAddress address[] = (InternetAddress[]) message.getFrom();
		String from = address[0].getAddress();
		if (from == null)
			from = "";
		String personal = address[0].getPersonal();
		if (personal == null)
			personal = "";
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	/**
	 * Gets the addresses.
	 * 
	 * @param addresses the addresses
	 * @return the addresses
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private List<String> getAddresses(InternetAddress[] addresses) throws UnsupportedEncodingException {
		List<String> result = new ArrayList<String>();
		if (addresses == null)
			return result;
		for (InternetAddress a : addresses) {
			String email = a.getAddress();
			if (email == null)
				email = "";
			else {
				email = MimeUtility.decodeText(email);
			}
			String personal = a.getPersonal();
			if (personal == null)
				personal = "";
			else {
				personal = MimeUtility.decodeText(personal);
			}
			String compositeto = personal + "<" + email + ">";
			result.add(compositeto);
		}
		return result;
	}

	/**
	 * Gets the mail address.
	 * 
	 * @param type the type
	 * @param mimeMessage the mime message
	 * @return the mail address
	 * @throws Exception the exception
	 */
	private List<String> getMailAddress(String type, MimeMessage mimeMessage) throws Exception {
		String addtype = type.toUpperCase();
		InternetAddress[] address = null;
		if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BCC")) {
			if (addtype.equals("TO")) {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
			} else if (addtype.equals("CC")) {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
			} else {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
			}
		}
		return getAddresses(address);
	}

	/**
	 * Gets the received date.
	 * 
	 * @param m the m
	 * @return the received date
	 * @throws Exception the exception
	 */
	private Date getReceivedDate(MimeMessage m) throws Exception {
		if (m.getSentDate() != null)
			return m.getSentDate();
		if (m.getReceivedDate() != null)
			return m.getReceivedDate();
		return new Date();
	}

	/**
	 * Gets the subject.
	 * 
	 * @param mimeMessage the mime message
	 * @return the subject
	 * @throws MessagingException the messaging exception
	 */
	private String getSubject(MimeMessage mimeMessage) throws MessagingException {
		String subject = "";
		try {
			subject = MimeUtility.decodeText(mimeMessage.getSubject());
			if (subject == null)
				subject = "";
		} catch (Exception exce) {
		}
		return subject;
	}

	/**
	 * Gets the body text.
	 * 
	 * @return the body text
	 */
	@SuppressWarnings("unused")
	private String getBodyText() {
		return bodytext.toString();
	}

	/**
	 * Gets the content.
	 * 
	 * @param part the part
	 * @param result the result
	 * @return the content
	 * @throws Exception the exception
	 */
	private StringBuffer getContent(Part part, StringBuffer result) throws Exception {
		if (part.isMimeType("multipart/*")) {
			Multipart p = (Multipart) part.getContent();
			int count = p.getCount();

			if (count > 1)
				count = 1;
			for (int i = 0; i < count; i++) {
				BodyPart bp = p.getBodyPart(i);
				getContent(bp, result);
			}
		} else if (part.isMimeType("text/*")) {
			result.append(part.getContent());
		}
		return result;
	}

	/**
	 * Gets the mail content.
	 * 
	 * @param part the part
	 * @return the mail content
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unused")
	private void getMailContent(Part part) throws Exception {
		String contenttype = part.getContentType();
		int nameindex = contenttype.indexOf("name");
		boolean conname = false;
		if (nameindex != -1)
			conname = true;
		System.out.println("CONTENTTYPE: " + contenttype);
		if (part.isMimeType("text/plain") && !conname) {
			bodytext.append((String) part.getContent());
		} else if (part.isMimeType("text/html") && !conname) {
			bodytext.append((String) part.getContent());
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				getMailContent(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType("message/rfc822")) {
			getMailContent((Part) part.getContent());
		} else {
		}
	}

	/**
	 * Gets the reply sign.
	 * 
	 * @param mimeMessage the mime message
	 * @return the reply sign
	 * @throws MessagingException the messaging exception
	 */
	private boolean getReplySign(MimeMessage mimeMessage) throws MessagingException {
		boolean replysign = false;
		String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * Checks if is new.
	 * 
	 * @param mimeMessage the mime message
	 * @return true, if is new
	 * @throws MessagingException the messaging exception
	 */
	private boolean isNew(MimeMessage mimeMessage) throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) mimeMessage).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		System.out.println("flags's length: " + flag.length);
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				System.out.println("seen Message.......");
				break;
			}
		}
		return isnew;
	}

	/**
	 * Checks if is contain attach.
	 * 
	 * @param part the part
	 * @return true, if is contain attach
	 * @throws Exception the exception
	 */
	private boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		@SuppressWarnings("unused")
		String contentType = part.getContentType();
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
					attachflag = true;
				else if (mpart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) mpart);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1)
						attachflag = true;
					if (contype.toLowerCase().indexOf("name") != -1)
						attachflag = true;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	/**
	 * Gets the files.
	 * 
	 * @param ctx the ctx
	 * @param m the m
	 * @return the files
	 * @throws Exception the exception
	 */
	private List<FileObject> getFiles(MailContext ctx, Message m) throws Exception {
		List<FileObject> files = new ArrayList<FileObject>();
		Part part = (Part) m;

		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					FileObject fileObject = FileUtil.createFileFromPart(ctx, part);
					if (fileObject == null) {
						continue;
					}
					files.add(fileObject);
				} else if (mpart.isMimeType("multipart/*")) {
					getFiles(ctx, m);
				} else {
					FileObject fileObject = FileUtil.createFileFromPart(ctx, part);
					if (fileObject == null) {
						continue;
					}
					files.add(fileObject);
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			getFiles(ctx, m);
		}
		return files;
	}

	/**
	 * Delete from server.
	 * 
	 * @param messages the messages
	 * @throws Exception the exception
	 */
	private void deleteFromServer(Message[] messages) throws Exception {
		for (Message m : messages) {
			m.setFlag(Flags.Flag.DELETED, true);
		}
	}
}
