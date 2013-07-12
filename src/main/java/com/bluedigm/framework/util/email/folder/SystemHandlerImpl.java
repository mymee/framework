/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.util.email.folder;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bluedigm.framework.util.email.exception.FileException;
import com.bluedigm.framework.util.email.javamail.Mail;
import com.bluedigm.framework.util.email.javamail.MailContext;
import com.bluedigm.framework.util.email.util.FileObject;
import com.bluedigm.framework.util.email.util.FileUtil;
import com.bluedigm.framework.util.email.util.MailComparator;


/**
 * The Class SystemHandlerImpl.
 */
public class SystemHandlerImpl implements SystemHandler {

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#saveInBox(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void saveInBox(Mail mail, MailContext ctx) {
		try {
			FileUtil.writeToXML(ctx, mail, FileUtil.INBOX);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#getInBoxMails(com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public List<Mail> getInBoxMails(MailContext ctx) {

		List<File> xmlFiles = FileUtil.getXMLFiles(ctx, FileUtil.INBOX);

		List<Mail> result = null;
		try {
			result = convert(xmlFiles, ctx);
		} catch (FileException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Convert.
	 * 
	 * @param xmlFiles the xml files
	 * @param ctx the ctx
	 * @return the list
	 * @throws FileException the file exception
	 */
	private List<Mail> convert(List<File> xmlFiles, MailContext ctx) throws FileException {
		List<Mail> result = new ArrayList<Mail>();
		for (File file : xmlFiles) {
			Mail mail = FileUtil.fromXML(ctx, file);// XML은 메일 객체로 변환
			// mail.setSize(FileBean.getSize((int)FileUtils.sizeOf(file)));
			result.add(mail);
		}
		sort(result);
		return result;
	}

	/**
	 * Sort.
	 * 
	 * @param mails the mails
	 */
	private void sort(List<Mail> mails) {
		Collections.sort(mails, new MailComparator());
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#getDeletedBoxMails(com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public List<Mail> getDeletedBoxMails(MailContext ctx) {
		try {
			return getMails(ctx, FileUtil.DELETED);
		} catch (FileException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#getDraftBoxMails(com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public List<Mail> getDraftBoxMails(MailContext ctx) {
		try {
			return getMails(ctx, FileUtil.DRAFT);
		} catch (FileException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#getOutBoxMails(com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public List<Mail> getOutBoxMails(MailContext ctx) {
		try {
			return getMails(ctx, FileUtil.OUTBOX);
		} catch (FileException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#getSentBoxMails(com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public List<Mail> getSentBoxMails(MailContext ctx) {
		try {
			return getMails(ctx, FileUtil.SENT);
		} catch (FileException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the mails.
	 * 
	 * @param ctx the ctx
	 * @param box the box
	 * @return the mails
	 * @throws FileException the file exception
	 */
	private List<Mail> getMails(MailContext ctx, String box) throws FileException {
		List<File> xmlFiles = FileUtil.getXMLFiles(ctx, box);
		List<Mail> result = convert(xmlFiles, ctx);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#delete(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void delete(Mail mail, MailContext ctx) {

		File file = getMailXmlFile(mail.getXmlName(), ctx);
		file.delete();
		try {
			FileUtil.writeToXML(ctx, mail, FileUtil.DELETED);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#saveMail(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void saveMail(Mail mail, MailContext ctx) {
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);

		try {
			FileUtil.writeToXML(xmlFile, mail);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the mail xml file.
	 * 
	 * @param xmlName the xml name
	 * @param ctx the ctx
	 * @return the mail xml file
	 */
	private File getMailXmlFile(String xmlName, MailContext ctx) {
		List<File> allXMLFiles = getAllFiles(ctx);
		for (File f : allXMLFiles) {
			if (f.getName().equals(xmlName)) {
				return f;
			}
		}
		return null;
	}

	/**
	 * Gets the all files.
	 * 
	 * @param ctx the ctx
	 * @return the all files
	 */
	private List<File> getAllFiles(MailContext ctx) {
		List<File> fileList = new ArrayList<File>();
		String accountRoot = FileUtil.getAccountRoot(ctx);
		File file = new File(accountRoot);

		File[] fileArray = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				boolean filter = false;
				if (pathname.isDirectory() && !pathname.getName().equalsIgnoreCase(FileUtil.FILE)) {
					filter = true;
				}
				return filter;
			}
		});
		for (File f : fileArray) {
			File[] files = f.listFiles();
			for (File fs : files) {
				if (FileUtil.getFileSufix(fs.getName()).equalsIgnoreCase(".xml")) {
					fileList.add(fs);
				}
			}
		}

		return fileList;
	}

	/**
	 * Gets the dir files.
	 * 
	 * @param file the file
	 * @param fileList the file list
	 * @return the dir files
	 */
	public void getDirFiles(File file, List<File> fileList) {
		File[] fileArray = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				boolean filter = false;
				if (pathname.isDirectory() && !pathname.getName().equalsIgnoreCase(FileUtil.FILE)) {
					filter = true;
				}
				return filter;
			}
		});
		for (File f : fileArray) {
			if (f.isDirectory()) {
				this.getDirFiles(f, fileList);
			} else {
				if (FileUtil.getFileSufix(f.getName()).equalsIgnoreCase(".xml")) {
					fileList.add(f);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#realDelete(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void realDelete(Mail mail, MailContext ctx) {
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		List<FileObject> files = mail.getFiles();
		for (FileObject f : files)
			f.getFile().delete();
		if (xmlFile.exists())
			xmlFile.delete();
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#revert(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void revert(Mail mail, MailContext ctx) {
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		xmlFile.delete();
		try {
			FileUtil.writeToXML(ctx, mail, mail.getFrom());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#saveSent(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void saveSent(Mail mail, MailContext ctx) {
		saveFiles(mail, ctx);
		try {
			mail.setFrom(FileUtil.SENT);
			FileUtil.writeToXML(ctx, mail, FileUtil.SENT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#saveOutBox(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void saveOutBox(Mail mail, MailContext ctx) {
		saveFiles(mail, ctx);
		try {
			mail.setFrom(FileUtil.OUTBOX);
			FileUtil.writeToXML(ctx, mail, FileUtil.OUTBOX);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save files.
	 * 
	 * @param mail the mail
	 * @param ctx the ctx
	 */
	private void saveFiles(Mail mail, MailContext ctx) {
		String accountRoot = FileUtil.getAccountRoot(ctx) + FileUtil.FILE + File.separator;

		List<FileObject> fileList = mail.getFiles();
		if (fileList == null || fileList.size() == 0) {
			return;
		}
		for (FileObject file : fileList) {
			File outFile = new File(FileUtil.getFileName(accountRoot, file.getSourceName()));
			try {
				FileUtil.saveFile(outFile, new FileInputStream(file.getFile()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.util.email.folder.SystemHandler#saveDraftBox(com.sbsch.cms.framework.util.email.javamail.Mail, com.sbsch.cms.framework.util.email.javamail.MailContext)
	 */
	public void saveDraftBox(Mail mail, MailContext ctx) {
		this.saveFiles(mail, ctx);
		try {
			mail.setFrom(FileUtil.DRAFT);
			FileUtil.writeToXML(ctx, mail, FileUtil.DRAFT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
