/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.support;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class CDATAXMLAdapter.
 */
public final class CDATAXMLAdapter extends XmlAdapter<String, String>{
	  
  	/* (non-Javadoc)
  	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
  	 */
  	@Override  
	  public String marshal( String v ) throws Exception  
	  {  
	   return "<![CDATA[" + v + "]]>";  
	  }  
	  
	  /* (non-Javadoc)
  	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
  	 */
  	@Override  
	  public String unmarshal( String v ) throws Exception  
	  {  
	   return v;  
	  }
}
