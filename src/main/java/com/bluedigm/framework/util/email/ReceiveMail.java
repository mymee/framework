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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;


/**
 * The Class ReceiveMail.
 */
public class ReceiveMail {
	
	/** The mime message. */
	private MimeMessage mimeMessage = null;

	/** The save attach path. */
	private String saveAttachPath = ""; // 첨부파일 저장위치

	/** The bodytext. */
	private StringBuffer bodytext = new StringBuffer();

	/** The dateformat. */
	private String dateformat = "yyyy-MM-dd HH:mm";

	/**
	 * Instantiates a new receive mail.
	 */
	public ReceiveMail() {}

	/**
	 * Instantiates a new receive mail.
	 * 
	 * @param mimeMessage the mime message
	 */
	public ReceiveMail(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
		System.out.println("create a ReceiveMail object........");
	}

	/**
	 * Sets the mime message.
	 * 
	 * @param mimeMessage the new mime message
	 */
	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	/**
	 * Gets the from.
	 * 
	 * @return the from
	 * @throws Exception the exception
	 */
	public String getFrom() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
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
	 * Gets the mail address.
	 * 
	 * @param type the type
	 * @return the mail address
	 * @throws Exception the exception
	 */

	public String getMailAddress(String type) throws Exception {
		String mailaddr = "";
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
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String email = address[i].getAddress();
					if (email == null)
						email = "";
					else {
						email = MimeUtility.decodeText(email);
					}
					String personal = address[i].getPersonal();
					if (personal == null)
						personal = "";
					else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + email + ">";
					mailaddr += "," + compositeto;
				}
				mailaddr = mailaddr.substring(1);
			}
		} else {
			throw new Exception("Error emailaddr type!");
		}
		return mailaddr;
	}

	/**
	 * Gets the subject.
	 * 
	 * @return the subject
	 * @throws MessagingException the messaging exception
	 */

	public String getSubject() throws MessagingException {
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
	 * Gets the sent date.
	 * 
	 * @return the sent date
	 * @throws Exception the exception
	 */

	public String getSentDate() throws Exception {
		Date sentdate = mimeMessage.getSentDate();
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.format(sentdate);
	}

	/**
	 * Gets the body text.
	 * 
	 * @return the body text
	 */

	public String getBodyText() {
		return bodytext.toString();
	}

	/**
	 * Gets the mail content.
	 * 
	 * @param part the part
	 * @return the mail content
	 * @throws Exception the exception
	 */

	public void getMailContent(Part part) throws Exception {
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
	 * @return the reply sign
	 * @throws MessagingException the messaging exception
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replysign = false;
		String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * Gets the message id.
	 * 
	 * @return the message id
	 * @throws MessagingException the messaging exception
	 */
	public String getMessageId() throws MessagingException {
		return mimeMessage.getMessageID();
	}

	/**
	 * Checks if is new.
	 * 
	 * @return true, if is new
	 * @throws MessagingException the messaging exception
	 */
	public boolean isNew() throws MessagingException {
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
	public boolean isContainAttach(Part part) throws Exception {
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
	 * Save attach ment.
	 * 
	 * @param part the part
	 * @throws Exception the exception
	 */

	public void saveAttachMent(Part part) throws Exception {
		String fileName = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					fileName = mpart.getFileName();
					if (fileName.toLowerCase().indexOf("gb2312") != -1) {
						fileName = MimeUtility.decodeText(fileName);
					}
					saveFile(fileName, mpart.getInputStream());
				} else if (mpart.isMimeType("multipart/*")) {
					saveAttachMent(mpart);
				} else {
					fileName = mpart.getFileName();
					if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
						fileName = MimeUtility.decodeText(fileName);
						saveFile(fileName, mpart.getInputStream());
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachMent((Part) part.getContent());
		}
	}

	/**
	 * Sets the attach path.
	 * 
	 * @param attachpath the new attach path
	 */

	public void setAttachPath(String attachpath) {
		this.saveAttachPath = attachpath;
	}

	/**
	 * Sets the date format.
	 * 
	 * @param format the new date format
	 * @throws Exception the exception
	 */

	public void setDateFormat(String format) throws Exception {
		this.dateformat = format;
	}

	/**
	 * Gets the attach path.
	 * 
	 * @return the attach path
	 */

	public String getAttachPath() {
		return saveAttachPath;
	}

	/**
	 * Save file.
	 * 
	 * @param fileName the file name
	 * @param in the in
	 * @throws Exception the exception
	 */

	private void saveFile(String fileName, InputStream in) throws Exception {
		String osName = System.getProperty("os.name");
		String storedir = getAttachPath();
		String separator = "";
		if (osName == null)
			osName = "";
		if (osName.toLowerCase().indexOf("win") != -1) {
			separator = "\\";
			if (storedir == null || storedir.equals(""))
				storedir = "c:\\tmp";
		} else {
			separator = "/";
			storedir = "/tmp";
		}
		File storefile = new File(storedir + separator + fileName);
		System.out.println("storefile's path: " + storefile.toString());

		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(storefile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception("Save the file failed!");
		} finally {
			bos.close();
			bis.close();
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws Exception the exception
	 */

	public static void main(String args[]) throws Exception {
		String host = "pop.sina.com";
		String username = "hugservice@sina.com";
		String password = "abc123";
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("pop3");
		store.connect(host, username, password);
		Folder folder = store.getDefaultFolder();// .getFolder("INBOX");
		@SuppressWarnings("unused")
		Folder[] folderList = folder.list();
		folder.open(Folder.READ_ONLY);
		Message message[] = folder.getMessages();
		System.out.println("Messages's length: " + message.length);
		// ReceiveMail pmm = null;
		// for (int i = 0; i < message.length; i++) {
		// pmm = new ReceiveMail((MimeMessage) message[i]);
		// System.out.println("Message " + i + " subject: " + pmm.getSubject());
		// System.out.println("Message " + i + " sentdate: " +
		// pmm.getSentDate());
		// System.out.println("Message " + i + " replysign: " +
		// pmm.getReplySign());
		// System.out.println("Message " + i + " hasRead: " + pmm.isNew());
		// System.out.println("Message " + i + "  containAttachment: " +
		// pmm.isContainAttach((Part) message[i]));
		// System.out.println("Message " + i + " form: " + pmm.getFrom());
		// System.out.println("Message " + i + " to: " +
		// pmm.getMailAddress("to"));
		// System.out.println("Message " + i + " cc: " +
		// pmm.getMailAddress("cc"));
		// System.out.println("Message " + i + " bcc: " +
		// pmm.getMailAddress("bcc"));
		// pmm.setDateFormat("yy年MM月dd日 HH:mm");
		// System.out.println("Message " + i + " sentdate: " +
		// pmm.getSentDate());
		// System.out.println("Message " + i + " Message-ID: " +
		// pmm.getMessageId());
		// pmm.getMailContent((Part) message[i]);
		// System.out.println("Message " + i + " bodycontent: \r\n" +
		// pmm.getBodyText());
		//
		// // pmm.setAttachPath("c:\\tmp\\coffeecat1124");
		// pmm.saveAttachMent((Part) message[i]);
		// }
		folder.close(false);
		store.close();
	}
}
