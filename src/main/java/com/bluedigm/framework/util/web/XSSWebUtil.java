/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.util.web;

import java.util.regex.Pattern;

/**
 * <pre>
 * com.sbsch.cms.framework.util.web
 * 		|_XSSWebUtil.java
 * XSS 공격 취약성 방지(파라미터 문자열 교체)
 * </pre>
 *
 * @author Hanwoong Lee, Bluedigm
 * @date 2013. 4. 24. 오전 11:45:14
 * @version $Id$
 */
public class XSSWebUtil {
	public static String clearXSSMinimum(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}
		
		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		return returnValue;
	}

	public static String clearXSSMaximum(String value) {
		String returnValue = value;
		returnValue = clearXSSMinimum(returnValue);

		returnValue = returnValue.replaceAll("%00", null);

		returnValue = returnValue.replaceAll("%", "&#37;");

		// \\. => .

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); // ./
		returnValue = returnValue.replaceAll("%2F", "");

		return returnValue;
	}

	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	/**
	 * 행안부 보안취약점 점검 조치 방안.
	 *
	 * @param value
	 * @return
	 */
	public static String filePathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\", "");
		returnValue = returnValue.replaceAll("\\.\\.", ""); // ..
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}

	public static String filePathWhiteList(String value) {
		return value; // TODO
	}
	
	 public static boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		
		return ipPattern.matcher(str).matches();
    }
	 
	 public static String removeCRLF(String parameter) {
		 return parameter.replaceAll("\r", "").replaceAll("\n", "");
	 }
	 
	 public static String removeSQLInjectionRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("%", "").replaceAll(";", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll(",", "");
	 }
	 
	 public static String removeOSCmdRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll(";", "");
	 }
	 
	/**
	 * <pre>
	 * 1. 개요       :
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : main
	 * @date : 2013. 4. 24.
	 * @autor : Hanwoong Lee, Bluedigm
	 * @history :
	 * -----------------------------------------------------------------------
	 *  변경일                                                  작성자                                                                                              변경내용
	 * -----------------------------------------------------------------------
	 *  2013. 4. 24.        Hanwoong Lee, Bluedigm                  최초작성
	 * -----------------------------------------------------------------------
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = null;
		
		test = "<script language='javascript' encoding=\"utf-8\">q&a</script>";
		System.out.println("clearXSSMinimum() Test");
		System.out.println(test);
		System.out.println("=>");
		System.out.println(clearXSSMinimum(test));
		System.out.println();
		
		test = "/a/b/c../..\\";
		System.out.println("clearXSSMaximum() Test");
		System.out.println(test);
		System.out.println(" =>");
		System.out.println(clearXSSMaximum(test));
		System.out.println();
		
		test = "/a/b/c/../../../..\\..\\";
		System.out.println("filePathBlackList() Test");
		System.out.println(test);
		System.out.println("=>");
		System.out.println(filePathBlackList(test));
		System.out.println();
		
		test = "192.168.0.1";
		System.out.println("isIPAddress() test");
		System.out.println("IP : " + test + " => " + isIPAddress(test));
		
		test = "abc def*%;-+,ghi";
		System.out.println("removeSQLInjectionRisk() test");
		System.out.println(test + " => " + removeSQLInjectionRisk(test));
	}

}
