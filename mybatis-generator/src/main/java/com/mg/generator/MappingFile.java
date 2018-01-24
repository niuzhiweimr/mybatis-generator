package com.mg.generator;

/**
 * resultMap映射类
 * 
 * @author niuzhiwei
 * 
 */
public class MappingFile {

	private String property;
	private String column;
	private String jdbcType;
	private String javaType;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	@Override
	public String toString() {
		return "MappingFile [property=" + property + ", column=" + column + ", jdbcType=" + jdbcType + ", javaType=" + javaType
				+ "]";
	}

}
