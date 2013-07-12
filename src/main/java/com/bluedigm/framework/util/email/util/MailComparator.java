/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.util.email.util;
import java.util.Comparator;
import java.util.Date;

import com.bluedigm.framework.util.email.javamail.Mail;

/**
 * The Class MailComparator.
 */
public class MailComparator implements Comparator<Object>{
    
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Object o1, Object o2) {
            Mail mailOne = (Mail)o1;
            Mail mailTwo = (Mail)o2;
            Date date1 = mailOne.getReceiveDate();
            Date date2 = mailTwo.getReceiveDate();
            return date2.compareTo(date1);
    }
}
