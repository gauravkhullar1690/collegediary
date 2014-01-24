/**
 * 
 */
package com.collegediary.platform.hbm;

import java.util.Properties;

/**
 * @author gaurav.khullar
 * 
 */
public class CDLocalSessionFactoryBeanHelper {
	private String[] mappingResourcePatterns;
	private Properties hibernateProperties;
	private String[] excludingResourcePatterns;

	public String[] getMappingResourcePatterns() {
		return mappingResourcePatterns;
	}

	public void setMappingResourcePatterns(String[] mappingResourcePatterns) {
		this.mappingResourcePatterns = mappingResourcePatterns;
	}

	public Properties getHibernateProperties() {
		return hibernateProperties;
	}

	public void setHibernateProperties(Properties hibernateProperties) {
		this.hibernateProperties = hibernateProperties;
	}

	public String[] getExcludingResourcePatterns() {
		return this.excludingResourcePatterns;
	}

	public void setExcludingResourcePatterns(String[] excludingResourcePatterns) {
		this.excludingResourcePatterns = excludingResourcePatterns;
	}
}
