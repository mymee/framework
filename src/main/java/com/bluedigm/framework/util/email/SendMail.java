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

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.util.Assert;

import com.bluedigm.framework.util.email.util.MailConts;
import com.sun.mail.imap.IMAPFolder;


/**
 * The Class SendMail.
 */
public class SendMail {

	/** The mail info. */
	private MailInfo mailInfo;

	/** The filename. */
	private String filename = "";


	/**
	 * Gets the mail info.
	 * 
	 * @return the mail info
	 */
	public MailInfo getMailInfo() {
		return mailInfo;
	}


	/**
	 * Sets the mail info.
	 * 
	 * @param mailInfo the new mail info
	 */
	public void setMailInfo(MailInfo mailInfo) {
		this.mailInfo = mailInfo;
	}


	/**
	 * Gets the filename.
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}


	/**
	 * Sets the filename.
	 * 
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}


	/**
	 * Instantiates a new send mail.
	 */
	public SendMail() {
	}


	/**
	 * Instantiates a new send mail.
	 * 
	 * @param mailInfo the mail info
	 */
	public SendMail(MailInfo mailInfo) {
		this.mailInfo = mailInfo;
	}

	/**
	 * Transfer encode.
	 * 
	 * @param strText the str text
	 * @return the string
	 */
	public String transferEncode(String strText) {
		try {
			strText = MimeUtility.encodeWord(strText);// .encodeText(new
														// String(strText.getBytes(),"UTF-8"),
														// "UTF-8", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	/**
	 * Sets the address.
	 * 
	 * @param to the to
	 * @return the internet address[]
	 * @throws MessagingException the messaging exception
	 */
	public InternetAddress[] setAddress(String[] to) throws MessagingException {
		if (to == null || to.length == 0) {
			return null;
		}
		Assert.notNull(to, "To address array must not be null");
		InternetAddress[] addresses = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			addresses[i] = new InternetAddress(to[i]);
		}
		validateAddresses(addresses);
		return addresses;

	}

	/**
	 * Validate addresses.
	 * 
	 * @param addresses the addresses
	 * @throws AddressException the address exception
	 */
	protected void validateAddresses(InternetAddress[] addresses)
			throws AddressException {
		for (int i = 0; i < addresses.length; i++)
			validateAddress(addresses[i]);
	}

	/**
	 * Validate address.
	 * 
	 * @param address the address
	 * @throws AddressException the address exception
	 */
	protected void validateAddress(InternetAddress address)
			throws AddressException {
		address.validate();
	}

	/**
	 * Send mail.
	 * 
	 * @return true, if successful
	 */
	public boolean sendMail() {

		// mail session
		Properties props = System.getProperties();
		props.put("mail.smtp.host",mailInfo.getHost());
		props.put("mail.smtp.auth","true");
	//	props.put("mail.smtp.port","80");  
		//props.put("mail.smtp.quitwait","false"); 
		Session session = Session.getDefaultInstance(props,
			new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailInfo
							.getUserName(), mailInfo.getPassword());
				}
			});
		
		try {

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mailInfo.getFrom()));
			InternetAddress[] address = setAddress(mailInfo.getTo());// { new
																		// InternetAddress(mailInfo.getTo())
																		// };
			msg.setRecipients(Message.RecipientType.TO, address);
			address = setAddress(mailInfo.getCc());
			if (address != null) {
				msg.setRecipients(Message.RecipientType.CC, address);
			}
			
			address = setAddress(mailInfo.getBcc());
			if (address != null) {
				msg.setRecipients(Message.RecipientType.BCC, address);
			}
			msg.setSubject(transferEncode(mailInfo.getSubject()));


			Multipart mp = new MimeMultipart();

			MimeBodyPart mbpContent = new MimeBodyPart();
			if (mailInfo.getContentType().equalsIgnoreCase(
					MailConts.CONTENT_TYPE_HTML)) {
				// 7.29 메일 한글 인코딩 수정
				mbpContent.setContent(mailInfo.getContent(), "text/html; charset=UTF-8");
			} else if (mailInfo.getContentType().equalsIgnoreCase(
					MailConts.CONTENT_TYPE_TEXT)) {
				mbpContent.setText(mailInfo.getContent());
			}


			mp.addBodyPart(mbpContent);


			@SuppressWarnings("rawtypes")
			Enumeration efile = mailInfo.getAttachFile().elements();
			
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				mbpFile.setFileName(fds.getName());

				mp.addBodyPart(mbpFile);
			}

			mailInfo.getAttachFile().removeAllElements();

			msg.setContent(mp);
			msg.setSentDate(new Date());

			Transport.send(msg);

		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}


	/**
	 * Gets the sent mail folder.
	 * 
	 * @param message the message
	 * @param store the store
	 * @return the sent mail folder
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws MessagingException the messaging exception
	 */
	private static Folder getSentMailFolder(Message message, Store store)
			throws IOException, MessagingException {

		Properties props = new Properties();
		props.put("mail.smtp.auth ", "true ");
		props.put("MAIL.IMAP.AUTH.PLAIN.DISABLE", "TRUE");
		props.setProperty("mail.imap.auth.login.disable", "true");
		Folder folder = null;

		Session sess = Session.getDefaultInstance(props, null);

		store = sess.getStore("imap");

		store.connect("pop.sina.com", "hugservice@sina.com", "abc123");

		folder = (IMAPFolder) store.getFolder("Sent Items");

		folder.open(Folder.READ_WRITE);
		return folder;
	}


	/**
	 * Save email to sent mail folder.
	 * 
	 * @param message the message
	 */
	@SuppressWarnings("unused")
	private static void saveEmailToSentMailFolder(Message message) {

		Store store = null;
		Folder sentFolder = null;
		try {
			sentFolder = getSentMailFolder(message, store);
			sentFolder.appendMessages(new Message[] { message });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {


			if (sentFolder != null && sentFolder.isOpen()) {
				try {
					sentFolder.close(true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}

			if (store != null && store.isConnected()) {
				try {
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		MailInfo mailInfo = new MailInfo();
		mailInfo.setHost("192.168.10.52");
		mailInfo.setUserName("administrator");
		mailInfo.setPassword("administrator");
		mailInfo.setFrom("administrator@mail.co.kr");
		String[] to = new String[] {"test@test.com"};
		mailInfo.setTo(to);
		mailInfo.setSubject("Mail Test Subject !!");
		mailInfo.setContent("Hello World !!!");
		//mailInfo.addAttachFile("D:/fruit.jpg");
		mailInfo.setContentType(MailConts.CONTENT_TYPE_TEXT);
	//	mailInfo.addAttachFile("D:/HUG.C/SQL/Oracle10g/40.Create_Procedure/9A.SPSP/jsys.spsp_gc.sql");
		SendMail sendmail = new SendMail(mailInfo);
		sendmail.sendMail();
	}
	
// 쓰레드를 통한 대량 발송 예제
//	public static void main(String[] args) {
//		
//	SendMailUtil email = new SendMailUtil("192.168.10.52", "testmail@mail.test.co.kr", "administrator@mail.test.co.kr", "Mail Test!!", "Mail Test !!");
//	//email.setCc("test@mail.test.co.kr ");
//	email.sendMail();
//	
//	Thread.sleep(1000);
//	}
	
	
}// end
