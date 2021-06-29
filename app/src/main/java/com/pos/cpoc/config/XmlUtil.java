package com.pos.cpoc.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class XmlUtil<T> {
	// 创建一个默认的配置文件,需重写
	abstract T setDefaultConfig();

	private String file;
	private Class<T> clazz;

	public XmlUtil(Class<T> clazz, String file) {
		this.clazz = clazz;
		this.file = file;
	}

	/**
	 * 保存xml文件
	 * 
	 * @param entity
	 *            xml文件对应的对象
	 */
	public void saveConfig(T entity) {
		System.out.println("===保存配置====");
		XStream stream = new XStream(new DomDriver("utf-8"));// xml文件使用utf-8格式
		stream.autodetectAnnotations(true);// 设置自动匹配annotation.
		try {
			FileOutputStream out = new FileOutputStream(file);
			stream.toXML(entity, out);// 将实体类转为xml并输出到文件.
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从配置文件中读取配置,并自动转换为对应的对象.
	 * 
	 * @return T
	 */
	public T getConfig() {
		XStream stream = new XStream(new DomDriver("utf-8"));// xml文件使用utf-8格式
		stream.autodetectAnnotations(true);// 设置自动匹配annotation.
		FileInputStream input;
		T entity;
		try {
			input = new FileInputStream(file);
			String alias = "";
			if (clazz.isAnnotationPresent(XStreamAlias.class)) {
				alias = clazz.getAnnotation(XStreamAlias.class).value();
			}
			stream.alias(alias, clazz);// 由于xstream-1.3.1.jar中有个bug,从xml文件中读出的内容autodetectAnnotations(true)不生效,必须用alias后才正常.
			entity = (T) stream.fromXML(input); // 从配置文件中读取配置,并自动转换为对应的对象
		} catch (FileNotFoundException e1) {
			entity = setDefaultConfig();// 文件不存在时创建一个默认的配置文件.
			if (entity != null) {
				saveConfig(entity);
			}
		}
		return entity;
	}

}
