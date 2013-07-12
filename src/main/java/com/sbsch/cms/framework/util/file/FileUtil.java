/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;
import org.apache.regexp.RE;

import com.sbsch.cms.framework.util.string.StringUtil;


/**
 * The Class FileUtil.
 */
public class FileUtil {

    /** The Constant log. */

    private static final Log log = LogFactory.getLog(FileUtil.class);

    /** The basefile. */
    private static FileObject basefile;
    
    /** The manager. */
    private static FileSystemManager manager;

    static {
        try {
            manager = VFS.getManager();
            basefile = manager.resolveFile(System.getProperty("user.dir"));
        } catch (FileSystemException e) {
            log.error("EgovFileUtil : " + e.getMessage());
        }
    }

    /**
	 * Rm.
	 * 
	 * @param cmd the cmd
	 * @return the int
	 * @throws FileSystemException the file system exception
	 */
    public static int rm(final String cmd) throws FileSystemException {
        int result = -1;

        try {
            final FileObject file = manager.resolveFile(basefile, cmd);

            result = file.delete(Selectors.SELECT_SELF_AND_CHILDREN);

            log.debug("result is " + result);

        } catch (FileSystemException e) {
            log.error(e.toString());
            throw new FileSystemException(e);
        }

        return result;
    }

    /**
	 * Cp.
	 * 
	 * @param source the source
	 * @param target the target
	 * @throws Exception the exception
	 */
    public static void cp(String source, String target) throws Exception {

        try {
            final FileObject src = manager.resolveFile(basefile, source);
            FileObject dest = manager.resolveFile(basefile, target);

            if (dest.exists() && dest.getType() == FileType.FOLDER) {
                dest = dest.resolveFile(src.getName().getBaseName());
            }

            dest.copyFrom(src, Selectors.SELECT_ALL);
        } catch (FileSystemException fse) {
            log.error(fse.toString());
            ;
            throw new FileSystemException(fse);
        }
    }

    /**
	 * Mv.
	 * 
	 * @param source the source
	 * @param target the target
	 * @throws Exception the exception
	 */
    public static void mv(String source, String target) throws Exception {

        try {
            final FileObject src = manager.resolveFile(basefile, source);
            FileObject dest = manager.resolveFile(basefile, target);

            if (dest.exists() && dest.getType() == FileType.FOLDER) {
                dest = dest.resolveFile(src.getName().getBaseName());
            }

            src.moveTo(dest);
        } catch (FileSystemException fse) {
            log.error(fse.toString());
            ;
            throw new FileSystemException(fse);
        }
    }

    /**
	 * Pwd.
	 * 
	 * @return the file name
	 */
    public static FileName pwd() {
        return basefile.getName();
    }

    /**
	 * Touch.
	 * 
	 * @param filepath the filepath
	 * @return the long
	 * @throws Exception the exception
	 */
    public static long touch(final String filepath) throws Exception {

        long currentTime = 0;
        final FileObject file = manager.resolveFile(basefile, filepath);

        if (!file.exists()) {
            file.createFile();
        }

        file.getContent().setLastModifiedTime(
            currentTime = System.currentTimeMillis());

        return currentTime;
    }

    /**
	 * Cd.
	 * 
	 * @param changDirectory the chang directory
	 * @throws Exception the exception
	 */
    public static void cd(final String changDirectory) throws Exception {
        final String path;
        if (!StringUtil.isEmpty(changDirectory)) {
            path = changDirectory;
        } else {
            path = System.getProperty("user.home");
        }

        // Locate and validate the folder
        FileObject tmp = manager.resolveFile(basefile, path);

        if (tmp.exists()) {
            basefile = tmp;
        } else {
            log.info("Folder does not exist: " + tmp.getName());
        }
        log.info("Current folder is " + basefile.getName());
    }

    /**
	 * Ls.
	 * 
	 * @param cmd the cmd
	 * @return the list
	 * @throws FileSystemException the file system exception
	 */
    @SuppressWarnings("rawtypes")
	public List ls(final String[] cmd) throws FileSystemException {
        List list = new ArrayList();

        int pos = 1;
        final boolean recursive;
        if (cmd.length > pos && cmd[pos].equals("-R")) {
            recursive = true;
            pos++;
        } else {
            recursive = false;
        }

        final FileObject file;
        if (cmd.length > pos) {
            file = manager.resolveFile(basefile, cmd[pos]);
        } else {
            file = basefile;
        }

        if (file.getType() == FileType.FOLDER) {
            // List the contents
            log.info("Contents of " + file.getName());
            log.info(listChildren(file, recursive, ""));
            // list.add(file.getName());
        } else {
            // Stat the file
            log.info(file.getName());
            final FileContent content = file.getContent();
            log.info("Size: " + content.getSize() + " bytes.");
            final DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                    DateFormat.MEDIUM);
            final String lastMod =
                dateFormat.format(new Date(content.getLastModifiedTime()));
            log.info("Last modified: " + lastMod);
        }

        return list;
    }

    /**
	 * List children.
	 * 
	 * @param dir the dir
	 * @param recursive the recursive
	 * @param prefix the prefix
	 * @return the string buffer
	 * @throws FileSystemException the file system exception
	 */
    private StringBuffer listChildren(final FileObject dir,
            final boolean recursive, final String prefix)
            throws FileSystemException {
        StringBuffer line = new StringBuffer();
        final FileObject[] children = dir.getChildren();

        for (int i = 0; i < children.length; i++) {
            final FileObject child = children[i];
            // log.info(prefix +
            // child.getName().getBaseName());
            line.append(prefix).append(child.getName().getBaseName());

            if (child.getType() == FileType.FOLDER) {
                // log.info("/");
                line.append("/");
                if (recursive) {
                    line
                        .append(listChildren(child, recursive, prefix + "    "));
                }
            } else {
                line.append("");
            }
        }

        return line;
    }

    /**
	 * Read file.
	 * 
	 * @param file the file
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static String readFile(File file) throws IOException {
        BufferedInputStream in =
            new BufferedInputStream(new FileInputStream(file));
        return readFileContent(in);
    }

    /**
	 * Read file content.
	 * 
	 * @param in the in
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static String readFileContent(InputStream in) throws IOException {
        StringBuffer buf = new StringBuffer();

        for (int i = in.read(); i != -1; i = in.read()) {
            buf.append((char) i);
        }

        return buf.toString();
    }

    /**
	 * Read file.
	 * 
	 * @param file the file
	 * @param encoding the encoding
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static String readFile(File file, String encoding)
            throws IOException {
        StringBuffer sb = new StringBuffer();

        List<String> lines = FileUtils.readLines(file, encoding);

        for (Iterator<String> it = lines.iterator();;) {
            sb.append(it.next());

            if (it.hasNext()) {
                sb.append("");
            } else {
                break;
            }
        }

        return sb.toString();
    }

    /**
	 * Write file.
	 * 
	 * @param file the file
	 * @param text the text
	 */
    public static void writeFile(File file, String text) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(file);
            writer.write(text);
        } catch (Exception e) {
            log.error("Error creating File: " + file.getName() + ":" + e);
            return;
        } finally {
            try {
                writer.close();
            } catch (Exception e) {

            }
        }
    }

    /**
	 * Write file.
	 * 
	 * @param fileName the file name
	 * @param text the text
	 */
    public static void writeFile(String fileName, String text) {
        writeFile(new File(fileName), text);
    }

    /**
	 * Write file.
	 * 
	 * @param fileName the file name
	 * @param data the data
	 * @param encoding the encoding
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static void writeFile(String fileName, String data, String encoding)
            throws IOException {
        FileUtils.writeStringToFile(new File(fileName), data, encoding);
    }

    /*
     * Saves the content to the file. 한글 저장시 오류...점검
     * public static void saveFile(String filename,
     * String content) throws IOException {
     * FileOutputStream fos = null;
     * BufferedOutputStream bos = null; try { fos = new
     * FileOutputStream(filename); bos = new
     * BufferedOutputStream(fos);
     * bos.write(content.getBytes(), 0,
     * content.length()); } finally { if (bos != null)
     * bos.close(); if (fos != null) fos.close(); } }
     */

    /**
	 * Gets the content.
	 * 
	 * @param file the file
	 * @return the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static byte[] getContent(final FileObject file) throws IOException {
        final FileContent content = file.getContent();
        final int size = (int) content.getSize();
        final byte[] buf = new byte[size];

        final InputStream in = content.getInputStream();
        try {
            int read = 0;
            for (int pos = 0; pos < size && read >= 0; pos += read) {
                read = in.read(buf, pos, size - pos);
            }
        } finally {
            in.close();
        }

        return buf;
    }

    /**
	 * Write content.
	 * 
	 * @param file the file
	 * @param outstr the outstr
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static void writeContent(final FileObject file,
            final OutputStream outstr) throws IOException {
        final InputStream instr = file.getContent().getInputStream();
        try {
            final byte[] buffer = new byte[1024];
            while (true) {
                final int nread = instr.read(buffer);
                if (nread < 0) {
                    break;
                }
                outstr.write(buffer, 0, nread);
            }
        } finally {
            instr.close();
        }
    }

    // Create the output stream via getContent(),
    // to pick up the validation it does
    /**
	 * Copy content.
	 * 
	 * @param srcFile the src file
	 * @param destFile the dest file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static void copyContent(final FileObject srcFile,
            final FileObject destFile) throws IOException {
        final OutputStream outstr = destFile.getContent().getOutputStream();
        try {
            writeContent(srcFile, outstr);
        } finally {
            outstr.close();
        }
    }

    /**
	 * Gets the file extension.
	 * 
	 * @param filename the filename
	 * @return the file extension
	 */
    public static String getFileExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
	 * Checks if is exists file.
	 * 
	 * @param filename the filename
	 * @return true, if is exists file
	 */
    public static boolean isExistsFile(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    /**
	 * Strip filename.
	 * 
	 * @param filename the filename
	 * @return the string
	 */
    public static String stripFilename(String filename) {
        return FilenameUtils.getBaseName(filename);
    }

    /**
	 * Delete.
	 * 
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            // it's a folder, list children first
            File[] children = file.listFiles();
            for (int i = 0; i < children.length; i++) {
                delete(children[i]);
            }
        }
        if (!file.delete()) {
            throw new IOException("Unable to delete " + file.getPath());
        }
    }

    /**
	 * Read text file.
	 * 
	 * @param fileName the file name
	 * @param newline the newline
	 * @return the string buffer
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static StringBuffer readTextFile(String fileName, boolean newline)
            throws FileNotFoundException, IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        StringBuffer buf = new StringBuffer();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));

            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
                if (newline) {
                    buf.append(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            // log.error(e, module);
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // log.error(e, module);
                }
            }
        }

        return buf;
    }

    /**
	 * Gets the file object.
	 * 
	 * @param filepath the filepath
	 * @return the file object
	 * @throws Exception the exception
	 */
    public static FileObject getFileObject(final String filepath)
            throws Exception {
        FileSystemManager mgr = VFS.getManager();

        return mgr.resolveFile(mgr.resolveFile(System.getProperty("user.dir")),
            filepath);
    }

    /**
	 * Grep.
	 * 
	 * @param search the search
	 * @param pattern the pattern
	 * @return the list
	 * @throws Exception the exception
	 */
    public static List<String> grep(final Object[] search, final String pattern)
            throws Exception {
        RE searchPattern = new RE(pattern);

        String[] strings = searchPattern.grep(search);
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }

        return list;
    }

    /**
	 * Grep.
	 * 
	 * @param file the file
	 * @param pattern the pattern
	 * @return the list
	 * @throws Exception the exception
	 */
    public static List<String> grep(final File file, final String pattern)
            throws Exception {
        RE searchPattern = new RE(pattern);

        List<String> lists = FileUtils.readLines(file);
        Object[] search = lists.toArray();

        String[] strings = searchPattern.grep(search);
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }

        return list;
    }

	/**
	 * Inits the folder.
	 * 
	 * @param fullPath the full path
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String initFolder(String fullPath) throws Exception {
		File file = new File(fullPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return fullPath;
	}

	   
	/**
	 * Gets the exist dir.
	 * 
	 * @param dir the dir
	 * @return the exist dir
	 */
	public static boolean getExistDir(File dir) {
		boolean existDir = false;
		if (!dir.exists()) {
			dir.setReadable(true);
			dir.setWritable(true);
			existDir = dir.mkdirs();
			// log.debug(" ==================== 디렉토리 유무 " + dir.exists() +
			// " : ======================");
		} else {
			existDir = true;
		}
		return existDir;
	}

	/**
	 * Gets the file size.
	 * 
	 * @param filename the filename
	 * @return the file size
	 * @throws Exception the exception
	 */
	public static long getFileSize(String filename) throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			return Long.valueOf("0");
		}
		return file.length();
	}
}
