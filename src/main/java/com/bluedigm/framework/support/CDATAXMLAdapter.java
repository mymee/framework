/*
 * Copyright (c) Bluedigm.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Bluedigm.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Bluedigm.
 */
package com.bluedigm.framework.support;

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
