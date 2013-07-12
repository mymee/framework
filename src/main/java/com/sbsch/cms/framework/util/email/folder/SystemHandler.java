/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.util.email.folder;

import java.util.List;

import com.sbsch.cms.framework.util.email.javamail.Mail;
import com.sbsch.cms.framework.util.email.javamail.MailContext;

/**
 * The Interface SystemHandler.
 */
public interface SystemHandler {
    
    /**
	 * Save in box.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void saveInBox(Mail mail, MailContext ctx);
    
    /**
	 * Gets the in box mails.
	 * 
	 * @param ctx the ctx
	 * @return the in box mails
	 */
    public List<Mail> getInBoxMails(MailContext ctx);
    
    /**
	 * Gets the deleted box mails.
	 * 
	 * @param ctx the ctx
	 * @return the deleted box mails
	 */
    public List<Mail> getDeletedBoxMails(MailContext ctx);
    
    /**
	 * Gets the draft box mails.
	 * 
	 * @param ctx the ctx
	 * @return the draft box mails
	 */
    public List<Mail> getDraftBoxMails(MailContext ctx);
    
    /**
	 * Gets the out box mails.
	 * 
	 * @param ctx the ctx
	 * @return the out box mails
	 */
    public List<Mail> getOutBoxMails(MailContext ctx);
    
    /**
	 * Gets the sent box mails.
	 * 
	 * @param ctx the ctx
	 * @return the sent box mails
	 */
    public List<Mail> getSentBoxMails(MailContext ctx);     
    
    /**
	 * Delete.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void delete(Mail mail, MailContext ctx);
    
    /**
	 * Save mail.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void saveMail(Mail mail, MailContext ctx) ;
    
    /**
	 * Real delete.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void realDelete(Mail mail, MailContext ctx);
    
    /**
	 * Revert.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void revert(Mail mail, MailContext ctx);
    
    /**
	 * Save sent.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void saveSent(Mail mail, MailContext ctx);
    
    /**
	 * Save out box.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void saveOutBox(Mail mail, MailContext ctx);
    
    /**
	 * Save draft box.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
    public void saveDraftBox(Mail mail,MailContext ctx);
}
