package com.mg.generator;

import java.io.*;
import org.apache.log4j.Logger;
import com.mg.constant.Constant;
import com.mg.util.MappingUtil;

public class CallGenerator{

	private static Logger logger = Logger.getLogger(DBResource.class);

	public static void main(String[] args) {
		try {
			generator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generator() {

		// 生成的实体类所在的包
		String entityPackage = ConfigLoader.getEntityPackage();

		// 生成的DAO类所在的包
		String daoPackage = ConfigLoader.getDaoPackage();

		// 代码生成的输出目录
		String outputDircName = ConfigLoader.getOutputDirc();

		// 列出要生成的表名
		String[] tables = ConfigLoader.getTables();

		File outputDirc = new File(outputDircName);
		if (!outputDirc.exists()) {
			outputDirc.mkdirs();
		}

		File poPackageDirc = new File(outputDirc, "model");
		poPackageDirc.mkdirs();

		File daoPackageDirc = new File(outputDirc, "dao");
		daoPackageDirc.mkdirs();

		File mapperPackageDirc = new File(outputDirc, "mappers");
		mapperPackageDirc.mkdirs();

		for (int i = 0; i < tables.length; i++) {
			try {
				CodeBuilder codeBuilder = new CodeBuilder(tables[i]);

				logger.info("实体类构造开始执行");
				// 生成实体类
				String codeStr = codeBuilder.buildEntitySource(entityPackage);
				createFile(
						codeStr,
						poPackageDirc.getCanonicalPath() + File.separator
								+ MappingUtil.getEntityName(tables[i], Constant.CLASS_SUFFIX, Constant.CLASS_UP_PREFIX) + ".java");
				
				logger.info("dao层接口构造开始执行");
				// 生成DAO类
				codeStr = codeBuilder.buildDaoSource(daoPackage);
				createFile(
						codeStr,
						daoPackageDirc.getCanonicalPath() + File.separator
								+Constant.INTERFACE_PREFIX+ MappingUtil.getEntityName(tables[i], Constant.DAO_SUFFIX, Constant.DAO_UP_PREFIX) + ".java");
				
				logger.info("mapper映射文件构造开始执行");
				// 生成MAPPER
				codeStr = codeBuilder.buildMapperSource(daoPackage,entityPackage);
				createFile(
						codeStr,
						mapperPackageDirc.getCanonicalPath() + File.separator
								+ MappingUtil.getEntityName(tables[i], Constant.MAPPER_SUFFIX, Constant.MAPPER_UP_PREFIX)
								+ ".xml");
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@SuppressWarnings("unused")
	private void writeToFile(InputStream is, File file) throws Exception {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			byte[] bytes = new byte[5 * 1024];
			int len = 0;
			while ((len = is.read(bytes)) > 0) {
				os.write(bytes, 0, len);
			}
			os.flush();
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	/**
	 * 将文本内容写入指定的文件
	 * 
	 * @param fileContent
	 * @param fileName
	 */
	private static void createFile(String fileContent, String fileName) throws IOException {
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			osw.write(fileContent, 0, fileContent.length());
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (osw != null) {
				osw.close();
			}
		}
	}

}
