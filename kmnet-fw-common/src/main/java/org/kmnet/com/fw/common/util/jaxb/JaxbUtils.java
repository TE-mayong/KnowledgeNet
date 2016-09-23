/*
 * Copyright (C) 2016 KnowledgeNet.
* All rights reserved.
*/
package org.kmnet.com.fw.common.util.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

/**
 * 
 * JaxbUtils <br>
 * XMLデータとJavaBeanのデータ変換Utilクラス。 <br>
 *
 * @author Ma Yong
 * @version 1.0 2015/08/20 新規作成
 */
public class JaxbUtils {
	/**
	 * XML文字列からJavaBeanに変換する。
	 * 
	 * @param xml String
	 * @param clazz Class type
	 * @param <T> Class type
	 * @return JavaBeanインスタンス
	 */
	public static <T> T converyToJavaBean(String xml, Class<T> clazz) {
		T t = (T) JAXB.unmarshal(new StringReader(xml), clazz);
		return t;
	}
	
	/**
	 * JavaBeanからXML文字列に変換する。
	 * @param obj Object JavaBeanインスタンス
	 * @return XML文字列
	 */
	public static String converyToXMLStr(Object  obj){
		StringWriter writer = new StringWriter();
		JAXB.marshal(obj, writer);
		return writer.toString();
	}
}
