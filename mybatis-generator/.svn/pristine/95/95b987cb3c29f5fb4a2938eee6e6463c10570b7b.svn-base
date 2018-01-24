package com.mg.generator;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.mg.util.MappingUtil;

//源数据映射
public class MetaMapping {

	private static Logger logger = Logger.getLogger(ConfigLoader.class);

	// 表名称
	private String tableName;

	// 表的各列及元数据
	private Map<String, MetaDataDescr> colNameMetaMap = new LinkedHashMap<String, MetaDataDescr>();

	protected MetaMapping(String tableName) {
		try {
			this.tableName = tableName;
			this.parseMetaData();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Map<String, MetaDataDescr> getColNameMetaMap() {
		return this.colNameMetaMap;
	}

	protected void parseMetaData() throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBResource.getConnection();
			// 定位主键字段
			Set<String> keySet = new HashSet<String>();
			rs = conn.getMetaData().getPrimaryKeys(conn.getCatalog(), null, tableName);
			while (rs.next()) {
				String pk = rs.getString("COLUMN_NAME").toLowerCase();
				keySet.add(pk);
			}
			rs.close();
			// 获取列元数据
			String sql = "SELECT * FROM " + tableName + " WHERE 1=1";
			stmt = conn.prepareStatement(sql);
			// 执行sql
			rs = stmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			// rsmd.getColumnCount() 获取sql 列字段数
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String colName = rsmd.getColumnName(i).toLowerCase();
				logger.debug(colName + ": " + rsmd.getColumnType(i) + "(" + rsmd.getColumnTypeName(i) + "), "
						+ rsmd.getPrecision(i) + "(精确度), " + rsmd.getScale(i) + "(小数点后位数)");
				MetaDataDescr md = new MetaDataDescr();
				// 设置列名称
				md.setColName(colName);
				// 设置列类型
				md.setColType(rsmd.getColumnType(i));
				// 设置精确度
				md.setPrecision(rsmd.getPrecision(i));
				// 设置小数点后长度
				md.setScale(rsmd.getScale(i));

				if (keySet.contains(colName)) {
					md.setPk(true);
				} else {
					md.setPk(false);
				}

				String fileldName = MappingUtil.getFieldName(colName);
				md.setFieldName(fileldName);

				// 把列类型映射为类属性类型
				md.setFieldType(reflectToFieldType(md.getColType(), md.getScale()));
				colNameMetaMap.put(colName, md);

			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				DBResource.freeConnection(conn);
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 把列类型映射为类属性类型
	 * 
	 * @param colType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private Class reflectToFieldType(int colType, int scale) throws Exception {

		switch (colType) {
		case Types.BIT:
			return Boolean.class;
		case Types.TINYINT:
			return Byte.class;
		case Types.SMALLINT:
			return Short.class;
		case Types.INTEGER:
			return Integer.class;
		case Types.BIGINT:
			return Long.class;

		case Types.FLOAT:
			return Float.class;
		case Types.REAL:
			return Double.class;
		case Types.DOUBLE:
			return Double.class;
		case Types.NUMERIC:
			if (scale == 0) {
				return Long.class;
			} else {
				return java.math.BigDecimal.class;
			}
		case Types.DECIMAL:
			if (scale == 0) {
				return Long.class;
			} else {
				return java.math.BigDecimal.class;
			}
		case Types.CHAR:
			return String.class;
		case Types.VARCHAR:
			return String.class;
		case Types.LONGVARCHAR:
			return String.class;

		case Types.DATE:
			return java.sql.Date.class;
		case Types.TIME:
			return java.sql.Time.class;
		case Types.TIMESTAMP:
			return java.sql.Timestamp.class;

		case Types.BINARY:
			return byte[].class;
		case Types.VARBINARY:
			return byte[].class;
		case Types.LONGVARBINARY:
			return byte[].class;
		case Types.BLOB:
			return byte[].class;
		case Types.CLOB:
			return byte[].class;
		}
		throw new Exception("不能识别的列类型:" + colType);
	}

	// 获取数据库表单个字段类型
	public static String DBFieldType(String tableName, String coulumn) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String ColumnType = "";
		try {
			conn = DBResource.getConnection();
			rs = conn.getMetaData().getPrimaryKeys(conn.getCatalog(), null, tableName);
			rs.close();
			// 获取列元数据
			String sql = "SELECT " + coulumn + "  FROM   " + tableName + "  WHERE 1=1 ";
			stmt = conn.prepareStatement(sql);
			// 执行sql
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			// rsmd.getColumnCount() 获取sql 列字段数
			ColumnType = rsmd.getColumnTypeName(1);
		
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return ColumnType;
	}
	
	public static void main(String[] args) {
		System.out.println(Byte[].class.getName());
		System.out.println(Byte[].class.getSimpleName());
		System.out.println(byte[].class.getName());
		System.out.println(byte[].class.getSimpleName());
	}

}
