package com.mg.util;

import com.mg.constant.Constant;

/**
 * 提提对类名/属性名与数据库表/列的映射方法
 * 
 * @author HuQingmiao
 */
public class MappingUtil {

	/**
	 * 根据驼峰规则将表名转为POJO类名
	 * 
	 * @param tableName
	 *            表名称
	 * @param suffix
	 * 		     后缀
	 * @param upPrefix
	 * 		 通用前缀截取
	 * 
	 * @return
	 */
	public static String getEntityName(String tableName, String suffix,int upPrefix) {
		
		tableName=tableName.substring(upPrefix,tableName.length())+suffix;
		
		StringBuffer buff = new StringBuffer(tableName.toLowerCase());

		// 类名的第一个字符是大写的
		buff.replace(0, 1, String.valueOf(Character.toUpperCase(tableName.charAt(0))));

		// 删除字符'_'，并将下一个字符转换为大写
		for (int i = 1, length = buff.length(); i < length;) {

			char lastCh = buff.charAt(i - 1);// 最后一个字符
			char ch = buff.charAt(i); // 当前的字符

			// 如果这个字符是一个字母，并且前一个字符为“_” 见这个字符替换为大写
			if (Character.isLetter(ch) && lastCh == '_') {
				buff.replace(i - 1, i, String.valueOf(Character.toUpperCase(ch)));

				buff.deleteCharAt(i);
				length--;
			} else {
				i++;
			}
		}

		return buff.toString();
	}

	/**
	 * 根据驼峰规则将列名转为POJO类的属性名
	 * 
	 * @param columnName
	 *            列名称
	 * @return
	 */
	public static String getFieldName(String columnName) {

		StringBuffer buff = new StringBuffer(columnName.toLowerCase());// 将说有字符串转换为小写
		// 删除字符'_'，并将下一个字符转换为大写。
		for (int i = 1, length = buff.length(); i < length;) {
			// 最后一个字符
			char lastCh = buff.charAt(i - 1);
			// 当前的字符
			char ch = buff.charAt(i);
			// 如果这个字符是一个字母，并且前一个字符为“_” 见这个字符替换为大写
			if (Character.isLetter(ch) && lastCh == '_') {
				buff.replace(i - 1, i, String.valueOf(Character.toUpperCase(ch)));
				buff.deleteCharAt(i);
				length--;
			} else {
				i++;
			}
		}

		return buff.toString();
	}

	// 测试处理结果
	public static void main(String[] args) {
		String columnName1 = "abc_d";
		String columnName2 = "abcd";
		String columnName3 = "a_bcd";
		System.out.println(getEntityName(columnName1,Constant.CLASS_SUFFIX,Constant.CLASS_UP_PREFIX));
		System.out.println(getEntityName(columnName2,Constant.DAO_SUFFIX,Constant.DAO_UP_PREFIX));
		System.out.println(getEntityName(columnName3,Constant.MAPPER_SUFFIX,Constant.MAPPER_UP_PREFIX));
	}
}