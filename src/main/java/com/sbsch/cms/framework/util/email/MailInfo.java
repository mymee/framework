/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.util.email;

import java.util.Vector;


/**
 * The Class MailInfo.
 */
public class MailInfo {
	
	/** The message id. */
	private String messageID;

	/** The to. */
	private String[] to = new String[] {};

	/** The cc. */
	private String[] cc = new String[] {};

	/** The bcc. */
	private String[] bcc = new String[] {};

	/** The from. */
	private String from = "";

	/** The host. */
	private String host = "";

	/** The user name. */
	private String userName = "";

	/** The password. */
	private String password = "";

	/** The subject. */
	private String subject = "";

	/** The content. */
	private String content = "";

	/** The attach file. */
	@SuppressWarnings("rawtypes")
	private Vector attachFile = new Vector();

	/** The content type. */
	String contentType = ""; // 메시지의 콘텐츠 형식 (TEXT 또는 HTML)

	/** The sent date. */
	private String sentDate = "";

	/** The reply sign. */
	private boolean replySign;

	/** The new mail. */
	private boolean newMail;

	/** The contain attach. */
	private boolean containAttach;

	/**
	 * Gets the message id.
	 * 
	 * @return the message id
	 */
	public String getMessageID() {
		return messageID;
	}

	/**
	 * Sets the message id.
	 * 
	 * @param messageID the new message id
	 */
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	/**
	 * Gets the to.
	 * 
	 * @return the to
	 */
	public String[] getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 * 
	 * @param to the new to
	 */
	public void setTo(String[] to) {
		this.to = to;
	}

	/**
	 * Gets the cc.
	 * 
	 * @return the cc
	 */
	public String[] getCc() {
		return cc;
	}

	/**
	 * Sets the cc.
	 * 
	 * @param cc the new cc
	 */
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/**
	 * Gets the bcc.
	 * 
	 * @return the bcc
	 */
	public String[] getBcc() {
		return bcc;
	}

	/**
	 * Sets the bcc.
	 * 
	 * @param bcc the new bcc
	 */
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	/**
	 * Gets the from.
	 * 
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}


	/**
	 * Sets the from.
	 * 
	 * @param from the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Gets the host.
	 * 
	 * @return the host
	 */
	public String getHost() {
		return host;
	}


	/**
	 * Sets the host.
	 * 
	 * @param host the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * Sets the user name.
	 * 
	 * @param username the new user name
	 */
	public void setUserName(String username) {
		this.userName = username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Sets the password.
	 * 
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the subject.
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 * 
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the attach file.
	 * 
	 * @return the attach file
	 */
	@SuppressWarnings("rawtypes")
	public Vector getAttachFile() {
		return attachFile;
	}


	/**
	 * Adds the attach file.
	 * 
	 * @param fileName the file name
	 */
	@SuppressWarnings("unchecked")
	public void addAttachFile(String fileName) {
		attachFile.add(fileName);
	}

	/**
	 * Gets the sent date.
	 * 
	 * @return the sent date
	 */
	public String getSentDate() {
		return sentDate;
	}

	/**
	 * Sets the sent date.
	 * 
	 * @param sentDate the new sent date
	 */
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	/**
	 * Checks if is the reply sign.
	 * 
	 * @return the reply sign
	 */
	public boolean isReplySign() {
		return replySign;
	}

	/**
	 * Sets the reply sign.
	 * 
	 * @param replySign the new reply sign
	 */
	public void setReplySign(boolean replySign) {
		this.replySign = replySign;
	}

	/**
	 * Checks if is the new mail.
	 * 
	 * @return the new mail
	 */
	public boolean isNewMail() {
		return newMail;
	}

	/**
	 * Sets the new mail.
	 * 
	 * @param newMail the new new mail
	 */
	public void setNewMail(boolean newMail) {
		this.newMail = newMail;
	}

	/**
	 * Checks if is the contain attach.
	 * 
	 * @return the contain attach
	 */
	public boolean isContainAttach() {
		return containAttach;
	}

	/**
	 * Sets the contain attach.
	 * 
	 * @param containAttach the new contain attach
	 */
	public void setContainAttach(boolean containAttach) {
		this.containAttach = containAttach;
	}

	/**
	 * Gets the content type.
	 * 
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 * 
	 * @param contentType the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
