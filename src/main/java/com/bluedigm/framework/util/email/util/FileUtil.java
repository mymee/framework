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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.Part;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;

import com.bluedigm.framework.util.email.exception.FileException;
import com.bluedigm.framework.util.email.javamail.Mail;
import com.bluedigm.framework.util.email.javamail.MailContext;


/**
 * The Class FileUtil.
 */
public class FileUtil {

	// private static XStream xstream = new XStream();

	/** The Constant DATE_FOLDER. */
	public static final String DATE_FOLDER = "email" + File.separator;

	/** The Constant INBOX. */
	public static final String INBOX = "inbox";

	/** The Constant OUTBOX. */
	public static final String OUTBOX = "outbox";

	/** The Constant SENT. */
	public static final String SENT = "sent";

	/** The Constant DRAFT. */
	public static final String DRAFT = "draft";

	/** The Constant DELETED. */
	public static final String DELETED = "deleted";

	/** The Constant FILE. */
	public static final String FILE = "file";

	/** The Constant FILE_SEPARATOR. */
	public static final String FILE_SEPARATOR = "hug_att_file_2011";


	/**
	 * Creates the folder.
	 * 
	 * @param ctx the ctx
	 */
	public static void createFolder(MailContext ctx) {
		String accountRoot = getAccountRoot(ctx);

		mkdir(new File(accountRoot));
		mkdir(new File(accountRoot + INBOX));
		mkdir(new File(accountRoot + OUTBOX));
		mkdir(new File(accountRoot + SENT));
		mkdir(new File(accountRoot + DRAFT));
		mkdir(new File(accountRoot + DELETED));
		mkdir(new File(accountRoot + FILE));
	}


	/**
	 * Mkdir.
	 * 
	 * @param file the file
	 */
	private static void mkdir(File file) {
		if (!file.exists()) {
			// FileBean.createDir(dir, ignoreIfExitst);
			file.mkdirs();
		}

	}

	/**
	 * Gets the account root.
	 * 
	 * @param ctx the ctx
	 * @return the account root
	 */
	public static String getAccountRoot(MailContext ctx) {
		String accountRoot = ctx.getRoot() + File.separator + DATE_FOLDER + ctx.getUser() + File.separator
				+ ctx.getAccount() + File.separator;
		return accountRoot;
	}


	/**
	 * Creates the file from part.
	 * 
	 * @param ctx the ctx
	 * @param part the part
	 * @return the file object
	 * @throws Exception the exception
	 */
	public static FileObject createFileFromPart(MailContext ctx, Part part) throws Exception {
		String fileRepository = getBoxPath(ctx, FILE); //파일 저장 디렉토리
		String fileName = part.getFileName();
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		// 메일 서버, 첨부 파일 이름 및 디코딩
		String serverFileName = MimeUtility.decodeText(fileName);
		if (StringUtils.isBlank(serverFileName)) {
			return null;
		}
		File file = new File(getFileName(fileRepository, serverFileName));

		saveFile(file, part.getInputStream());

		// 생략 BufferedOutputStream의 InputStream과 FileOutputStream Close 메서드
		return new FileObject(serverFileName, file);
	}

	/**
	 * Gets the file name.
	 * 
	 * @param fileRepository the file repository
	 * @param mailFileName the mail file name
	 * @return the file name
	 */
	public static String getFileName(String fileRepository, String mailFileName) {
		// 로컬 시스템에서 고유한 파일 식별자로 생성된 UUID
		String fileName = UUID.randomUUID().toString();
		return fileRepository + fileName + FileUtil.FILE_SEPARATOR + mailFileName;
	}

	/**
	 * Gets the file object.
	 * 
	 * @param localFile the local file
	 * @return the file object
	 */
	public static FileObject getFileObject(File localFile) {
		if (localFile == null) {
			return null;
		}
		String fileName = localFile.getName();
		String sourceFileName = StringUtils.substringAfterLast(fileName, FileUtil.FILE_SEPARATOR);
		return new FileObject(sourceFileName, localFile);
	}

	/**
	 * Save file.
	 * 
	 * @param outFile the out file
	 * @param in the in
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/**
	 * <pre>
	 * 1. 개요 : 파일을 저장합니다.
	 * 2. 기능설명 : 파일을 저장합니다.
	 * </pre>
	 * 
	 * Save file.
	 *
	 * @param outFile the out file
	 * @param in the in
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @date 2012. 5. 22 오후 3:07:40
	 * @author Han-Woong Lee, Bluedigm
	 * @history
	 * -----------------------------------------------------------------
	 * 변경일             작성자                    변경내용
	 * -----------------------------------------------------------------
	 * 2012. 5. 22  Han-Woong Lee, Bluedigm    최초작성
	 * -----------------------------------------------------------------
	 */
	public static void saveFile(File outFile, InputStream in) throws IOException {

		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(outFile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
			throw new IOException("文件保存失败!");
		} finally {
			bos.close();
			bis.close();
		}
	}

	/**
	 * Gets the box path.
	 * 
	 * @param ctx the ctx
	 * @param folderName the folder name
	 * @return the box path
	 */
	public static String getBoxPath(MailContext ctx, String folderName) {
		return getAccountRoot(ctx) + folderName + File.separator;
	}

	/**
	 * Gets the file sufix.
	 * 
	 * @param fileName the file name
	 * @return the file sufix
	 */
	public static String getFileSufix(String fileName) {
		String suffixName = "";
		if (StringUtils.isNotBlank(fileName)) {
			suffixName = "." + StringUtils.substringAfterLast(fileName, ".");
		}
		return suffixName;
	}


	/**
	 * Write to xml.
	 * 
	 * @param ctx the ctx
	 * @param mail the mail
	 * @param boxFolder the box folder
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeToXML(MailContext ctx, Mail mail, String boxFolder) throws IOException {
		String xmlName = mail.getXmlName();// 메일 대응하는 XML 파일의 파일 이름
		String boxPath = getAccountRoot(ctx) + boxFolder + File.separator; // 디렉토리 경로에 해당
		File xmlFile = new File(boxPath + xmlName);
		writeToXML(xmlFile, mail);
	}


	/**
	 * Write to xml.
	 * 
	 * @param xmlFile the xml file
	 * @param mail the mail
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeToXML(File xmlFile, Mail mail) throws IOException {
		if (!xmlFile.exists())
			xmlFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(xmlFile);
		OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF8");
		// XML 파일에 XStream toXML 방법 메일 오브젝트 변환 및 출력
		// xstream.toXML(mail, writer);
		writer.close();
		fos.close();
	}


	/**
	 * From xml.
	 * 
	 * @param ctx the ctx
	 * @param xmlFile the xml file
	 * @return the mail
	 * @throws FileException the file exception
	 */
	public static Mail fromXML(MailContext ctx, File xmlFile) throws FileException {
		try {
			FileInputStream fis = new FileInputStream(xmlFile);
			// Mail mail = (Mail)xstream.fromXML(fis);
			fis.close();
			return null;
		} catch (Exception e) {
			throw new FileException("The converted data is abnormal: " + xmlFile.getAbsolutePath());
		}
	}


	/**
	 * Gets the xML files.
	 * 
	 * @param ctx the ctx
	 * @param box the box
	 * @return the xML files
	 */
	public static List<File> getXMLFiles(MailContext ctx, String box) {
		String rootPath = getAccountRoot(ctx);
		String boxPath = rootPath + box;
		File boxFolder = new File(boxPath);// 得到某个box的目录
		List<File> files = filterFiles(boxFolder, ".xml");// 对文件进行后缀过滤
		return files;
	}


	/**
	 * Filter files.
	 * 
	 * @param folder the folder
	 * @param sufix the sufix
	 * @return the list
	 */
	private static List<File> filterFiles(File folder, String sufix) {
		List<File> result = new ArrayList<File>();
		File[] files = folder.listFiles();
		if (files == null)
			return new ArrayList<File>();
		for (File f : files) {
			if (f.getName().endsWith(sufix)) {
				result.add(f);
			}
		}
		return result;
	}


	/**
	 * Copy.
	 * 
	 * @param sourceFile the source file
	 * @param targetFile the target file
	 */
	public static void copy(File sourceFile, File targetFile) {
		/*
		 * try { //FileBean.copyFileToDirectory(sourceFile, targetFile); } catch
		 * (IOException e1) { e1.printStackTrace(); }
		 */
	}

}