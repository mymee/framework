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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import com.bluedigm.framework.util.email.exception.MailConnectionException;


/**
 * The Class MailContext.
 */
public class MailContext {
    //사용자
    /** The user. */
    private String user;
    //사용자계정
    /** The account. */
    private String account;
    //사용자패스워드
    /** The password. */
    private String password;
    //smtp 서버
    /** The smtp host. */
    private String smtpHost;
    //smtp 포트
    /** The smtp port. */
    private int smtpPort;
    //pop3 서버
    /** The pop3 host. */
    private String pop3Host;
    //pop3 포트
    /** The pop3 port. */
    private int pop3Port;
    //재설정 여부
    /** The reset. */
    private boolean reset = false;
    //이메일 스토어
    /** The store. */
    private Store store;
    
    /** The session. */
    private Session session;
    //메일 루트 디렉토리
    /** The root. */
    private String root;
    
    /**
	 * Gets the user.
	 * 
	 * @return the user
	 */
    public String getUser() {
            return user;
    }
    
    /**
	 * Sets the user.
	 * 
	 * @param user the new user
	 */
    public void setUser(String user) {
            this.user = user;
    }
    
    /**
	 * Gets the account.
	 * 
	 * @return the account
	 */
    public String getAccount() {
            return account;
    }
    
    /**
	 * Sets the account.
	 * 
	 * @param account the new account
	 */
    public void setAccount(String account) {
            this.account = account;
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
	 * Gets the smtp host.
	 * 
	 * @return the smtp host
	 */
    public String getSmtpHost() {
            return smtpHost;
    }
    
    /**
	 * Sets the smtp host.
	 * 
	 * @param smtpHost the new smtp host
	 */
    public void setSmtpHost(String smtpHost) {
            this.smtpHost = smtpHost;
    }
    
    /**
	 * Gets the smtp port.
	 * 
	 * @return the smtp port
	 */
    public int getSmtpPort() {
            return smtpPort;
    }
    
    /**
	 * Sets the smtp port.
	 * 
	 * @param smtpPort the new smtp port
	 */
    public void setSmtpPort(int smtpPort) {
            this.smtpPort = smtpPort;
    }
    
    /**
	 * Gets the pop3 host.
	 * 
	 * @return the pop3 host
	 */
    public String getPop3Host() {
            return pop3Host;
    }
    
    /**
	 * Sets the pop3 host.
	 * 
	 * @param pop3Host the new pop3 host
	 */
    public void setPop3Host(String pop3Host) {
            this.pop3Host = pop3Host;
    }
    
    /**
	 * Gets the pop3 port.
	 * 
	 * @return the pop3 port
	 */
    public int getPop3Port() {
            return pop3Port;
    }
    
    /**
	 * Sets the pop3 port.
	 * 
	 * @param pop3Port the new pop3 port
	 */
    public void setPop3Port(int pop3Port) {
            this.pop3Port = pop3Port;
    }
    
    /**
	 * Checks if is reset.
	 * 
	 * @return true, if is reset
	 */
    public boolean isReset() {
            return reset;
    }
    
    /**
	 * Sets the reset.
	 * 
	 * @param reset the new reset
	 */
    public void setReset(boolean reset) {
            this.reset = reset;
    }
    
    /**
	 * Gets the root.
	 * 
	 * @return the root
	 */
    public String getRoot() {
            return root;
    }
    
    /**
	 * Sets the root.
	 * 
	 * @param root the new root
	 */
    public void setRoot(String root) {
            this.root = root;
    }
    //返回Store对象
    /**
	 * Gets the store.
	 * 
	 * @return the store
	 * @throws MailConnectionException the mail connection exception
	 */
    public Store getStore()throws MailConnectionException{
            //重置了信息, 设置Store为null
            if (this.reset) {
                    this.store = null;
                    this.session = null;
                    this.reset = false;
            }
            if (this.store == null || !this.store.isConnected()) {
                    try {
                            Properties props = new Properties();
                            //创建mail的Session
                            Session session = Session.getDefaultInstance(props,null);
                            //使用pop3协议接收邮件
//                          URLName url = new URLName("pop3", getPop3Host(), getPop3Port(), null,   
//                                  getAccount(), getPassword());
                            //得到邮箱的存储对象
                            Store store = session.getStore("pop3");
//                          store.connect();
                            store.connect(getPop3Host(), getAccount(), getPassword());
                            this.store = store;
                    } catch (Exception e) {
                            e.printStackTrace();
                            throw new MailConnectionException("连接邮箱异常，请检查配置");
                    }
            }
            return this.store;
    }

    //将账号与密码封装成一个JavaMail的Authenticator对象，表示需要经过身份验证
    /**
	 * Gets the authenticator.
	 * 
	 * @return the authenticator
	 */
    @SuppressWarnings("unused")
	private Authenticator getAuthenticator() {
            return new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(getAccount(), getPassword());
                    }
            };
    }
    //返回Session对象
    /**
	 * Gets the session.
	 * 
	 * @return the session
	 */
    public Session getSession() {
            //重置了信息, 设置session为null
            if (this.reset) {
                    this.session = null;
                    this.store = null;
                    this.reset = false;
            }
            if (this.session == null) {
//                  Properties props = new Properties();
//                  //设置SMTP服务器和端口
//                  props.put("mail.smtp.host", this.getSmtpHost());  
//                  if(this.getSmtpPort() != 25){
//                          props.put("mail.smtp.port", this.getSmtpPort());
//                  }                       
//                  props.put("mail.smtp.auth", true);
//                  //创建Session对象
//                  Session sendMailSession = Session.getDefaultInstance(props, getAuthenticator());
                    // 构造mail session
                    Properties props = System.getProperties();
                    props.put("mail.smtp.host",this.getSmtpHost());
                    if(this.getSmtpPort() != 25){
                            props.put("mail.smtp.port", this.getSmtpPort());
                    }
                    props.put("mail.smtp.auth", "true");

                    Session sendMailSession = Session.getDefaultInstance(props,
                                    new Authenticator() {
                                            public PasswordAuthentication getPasswordAuthentication() {
                                                    return new PasswordAuthentication(getAccount(), getPassword());
                                            }
                                    });
                    this.session = sendMailSession;
            }
            return this.session;
    }
}
