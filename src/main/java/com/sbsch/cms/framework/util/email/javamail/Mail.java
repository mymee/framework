/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.util.email.javamail;

import java.util.Date;
import java.util.List;

import com.sbsch.cms.framework.util.email.util.FileObject;

/**
 * The Class Mail.
 */
public class Mail {
	//파일이름으로 UUID를 사용하는 로컬 XML 파일명 : uuid.xml
    /** The xml name. */
	private String xmlName;
    //메시지 ID
    /** The message id. */
    private String messageID;
    //보낸 사람
    /** The sender. */
    private String sender;
    //받는 사람
    /** The receivers. */
    private List<String> receivers;
    //메세지 제목
    /** The subject. */
    private String subject;
    //날짜
    /** The receive date. */
    private Date receiveDate;
    //메시지 크기
    /** The size. */
    private String size;    
    //메시지 내용
    /** The content. */
    private String content;
    //참조
    /** The ccs. */
    private List<String> ccs;
    //숨은참조
    /** The bccs. */
    private List<String> bccs;
    //읽음여부
    /** The has read. */
    private boolean hasRead;
    //답메일 여부
    /** The reply sign. */
    private boolean replySign;
    //첨부파일포함여부
    /** The contain attach. */
    private boolean containAttach;
    //첨부파일
    /** The files. */
    private List<FileObject> files;
    //보낸사람
    /** The from. */
    private String from;
    
    /**
	 * Gets the xml name.
	 * 
	 * @return the xml name
	 */
    public String getXmlName() {
            return xmlName;
    }
    
    /**
	 * Sets the xml name.
	 * 
	 * @param xmlName the new xml name
	 */
    public void setXmlName(String xmlName) {
            this.xmlName = xmlName;
    }
    
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
	 * Gets the sender.
	 * 
	 * @return the sender
	 */
    public String getSender() {
            return sender;
    }
    
    /**
	 * Sets the sender.
	 * 
	 * @param sender the new sender
	 */
    public void setSender(String sender) {
            this.sender = sender;
    }
    
    /**
	 * Gets the receivers.
	 * 
	 * @return the receivers
	 */
    public List<String> getReceivers() {
            return receivers;
    }
    
    /**
	 * Sets the receivers.
	 * 
	 * @param receivers the new receivers
	 */
    public void setReceivers(List<String> receivers) {
            this.receivers = receivers;
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
	 * Gets the receive date.
	 * 
	 * @return the receive date
	 */
    public Date getReceiveDate() {
            return receiveDate;
    }
    
    /**
	 * Sets the receive date.
	 * 
	 * @param receiveDate the new receive date
	 */
    public void setReceiveDate(Date receiveDate) {
            this.receiveDate = receiveDate;
    }
    
    /**
	 * Gets the size.
	 * 
	 * @return the size
	 */
    public String getSize() {
            return size;
    }
    
    /**
	 * Sets the size.
	 * 
	 * @param size the new size
	 */
    public void setSize(String size) {
            this.size = size;
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
	 * Gets the ccs.
	 * 
	 * @return the ccs
	 */
    public List<String> getCcs() {
            return ccs;
    }
    
    /**
	 * Sets the ccs.
	 * 
	 * @param ccs the new ccs
	 */
    public void setCcs(List<String> ccs) {
            this.ccs = ccs;
    }
    
    /**
	 * Gets the bccs.
	 * 
	 * @return the bccs
	 */
    public List<String> getBccs() {
            return bccs;
    }
    
    /**
	 * Sets the bccs.
	 * 
	 * @param bccs the new bccs
	 */
    public void setBccs(List<String> bccs) {
            this.bccs = bccs;
    }
    
    /**
	 * Checks if is checks for read.
	 * 
	 * @return true, if is checks for read
	 */
    public boolean isHasRead() {
            return hasRead;
    }
    
    /**
	 * Sets the checks for read.
	 * 
	 * @param hasRead the new checks for read
	 */
    public void setHasRead(boolean hasRead) {
            this.hasRead = hasRead;
    }
    
    /**
	 * Checks if is reply sign.
	 * 
	 * @return true, if is reply sign
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
	 * Checks if is contain attach.
	 * 
	 * @return true, if is contain attach
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
	 * Gets the files.
	 * 
	 * @return the files
	 */
    public List<FileObject> getFiles() {
            return files;
    }
    
    /**
	 * Sets the files.
	 * 
	 * @param files the new files
	 */
    public void setFiles(List<FileObject> files) {
            this.files = files;
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
}
