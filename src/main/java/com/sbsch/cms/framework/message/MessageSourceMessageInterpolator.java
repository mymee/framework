/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.message;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;

/**
 * The Class MessageSourceMessageInterpolator.
 */
public class MessageSourceMessageInterpolator  extends ResourceBundleMessageInterpolator  implements MessageInterpolator,MessageSourceAware, InitializingBean{
	
	/** The message source. */
	@Autowired
    private MessageSource messageSource;
    
    /**
	 * Gets the message source.
	 * 
	 * @return the message source
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator#interpolate(java.lang.String, javax.validation.MessageInterpolator.Context)
	 */
	@Override
    public String interpolate(String messageTemplate, Context context) {
        try {
            return messageSource.getMessage(messageTemplate, new Object[]{}, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            return super.interpolate(messageTemplate, context);
        }
    }

    /* (non-Javadoc)
     * @see org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator#interpolate(java.lang.String, javax.validation.MessageInterpolator.Context, java.util.Locale)
     */
    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        try {
            return messageSource.getMessage(messageTemplate, new Object[]{}, locale);
        } catch (NoSuchMessageException e) {
            return super.interpolate(messageTemplate, context, locale);
        }
    }
    
    /* (non-Javadoc)
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if (messageSource == null) {
            throw new IllegalStateException("MessageSource was not injected, could not initialize " 
                    + this.getClass().getSimpleName());
        }
    }

}
