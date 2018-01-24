package com.mg.generator;

import org.apache.log4j.Logger;
import java.sql.*;

/**
 * 数据库资源类 主要用于后去连接 资源 
 * 
 * @author niuzhiwei
 * 
 */
public class DBResource {

	private static Logger logger = Logger.getLogger(DBResource.class);

	protected static Connection getConnection() throws Exception {

		String driverStr = ConfigLoader.getJdbcDriver();
		String dataSource = ConfigLoader.getJdbcUrl();
		String username = ConfigLoader.getUsername();
		String password = ConfigLoader.getPassword();
		Class.forName(driverStr);
		Connection conn = null;
		try {
			conn = java.sql.DriverManager.getConnection(dataSource, username, password);
			logger.info("Databases connection successful");
		} catch (Exception e) {
			logger.info("Databases connection failure error{" + e.getMessage() + "}");
		}
		return conn;
	}

	protected static void freeConnection(Connection conn) throws Exception {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
