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

import java.util.List;


/**
 * The Class MailBox.
 */
public class MailBox {
	
	/** The message count. */
	private int messageCount;

	/** The new message count. */
	private int newMessageCount;

	/** The unread message count. */
	private int unreadMessageCount;

	/** The mail list. */
	private List<MailInfo> mailList;

	/**
	 * Gets the 사서함에있는 메시지의 총 수.
	 * 
	 * @return the 사서함에있는 메시지의 총 수
	 */
	public int getMessageCount() {
		return messageCount;
	}

	/**
	 * Sets the 사서함에있는 메시지의 총 수.
	 * 
	 * @param messageCount the new 사서함에있는 메시지의 총 수
	 */
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	/**
	 * Gets the 사서함에있는 새로운 메시지의 총 수.
	 * 
	 * @return the 사서함에있는 새로운 메시지의 총 수
	 */
	public int getNewMessageCount() {
		return newMessageCount;
	}

	/**
	 * Sets the 사서함에있는 새로운 메시지의 총 수.
	 * 
	 * @param newMessageCount the new 사서함에있는 새로운 메시지의 총 수
	 */
	public void setNewMessageCount(int newMessageCount) {
		this.newMessageCount = newMessageCount;
	}

	/**
	 * Gets the 사서함에있는 읽지않은 메시지의 총 수.
	 * 
	 * @return the 사서함에있는 읽지않은 메시지의 총 수
	 */
	public int getUnreadMessageCount() {
		return unreadMessageCount;
	}

	/**
	 * Sets the 사서함에있는 읽지않은 메시지의 총 수.
	 * 
	 * @param unreadMessageCount the new 사서함에있는 읽지않은 메시지의 총 수
	 */
	public void setUnreadMessageCount(int unreadMessageCount) {
		this.unreadMessageCount = unreadMessageCount;
	}

	/**
	 * Gets the 메일링리스트.
	 * 
	 * @return the 메일링리스트
	 */
	public List<MailInfo> getMailList() {
		return mailList;
	}

	/**
	 * Sets the 메일링리스트.
	 * 
	 * @param mailList the new 메일링리스트
	 */
	public void setMailList(List<MailInfo> mailList) {
		this.mailList = mailList;
	}
}
