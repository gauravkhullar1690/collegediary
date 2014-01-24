/**
 * 
 */
package com.collegediary.platform.hbm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.core.io.Resource;

import com.collegediary.platform.logging.CollegeDiaryLogger;

/**
 * @author gaurav.khullar
 *
 */
public class CDLocalSessionFactoryBean extends LocalSessionFactoryBean implements ApplicationContextAware{
	private ApplicationContext applicationContext;
    private String[] mappingResourcePatterns;
    private CDLocalSessionFactoryBeanHelper sessionFactoryHelper;

    private DataSource readDataSource;
    
    private static final ThreadLocal<DataSource> configTimeReadDataSourceHolder =
			new ThreadLocal<DataSource>();

    
    /**
     * @throws IllegalArgumentException in case of invalid pattern
     */
    protected void postProcessConfiguration(Configuration config) {
        if (this.mappingResourcePatterns == null) {
            return;
        }
        try {
        	List<String> excludingMapResList = new ArrayList<String>();
            for (int i=0;i< mappingResourcePatterns.length;i++) {
                Resource[] resources = applicationContext.getResources("classpath*:" + mappingResourcePatterns[i]);
                for (int j=0;j<resources.length;j++) {
                	if(excludingMapResList.contains(resources[j].getURL().getPath()))
                		continue;
                    config.addInputStream(resources[j].getInputStream());
                    CollegeDiaryLogger.info(this.getClass().getName(),"Found resource mapping file: " + resources[j].getFilename(),null);
                }
            }
            config.setProperty(Environment.CONNECTION_PROVIDER, CDLocalDataSourceConnectionProvider.class.getName());
        } catch(IOException e) {
        	CollegeDiaryLogger.error(this.getClass().getName(),"Mapping ResorcePattern Load error",e,true);
        }
    }

    @Override
	protected SessionFactory buildSessionFactory() throws Exception {
    	DataSource dataSource = getReadDataSource();
		if (dataSource != null) {
			// Make given DataSource available for SessionFactory configuration.
			configTimeReadDataSourceHolder.set(dataSource);
		}
		
		return super.buildSessionFactory();
	}



	/**
     * @see ApplicationContextAware#setApplicationContext(ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

	public CDLocalSessionFactoryBeanHelper getSessionFactoryHelper() {
		return sessionFactoryHelper;
	}

	public void setSessionFactoryHelper(
			CDLocalSessionFactoryBeanHelper sessionFactoryHelper) {
		this.sessionFactoryHelper = sessionFactoryHelper;
		this.mappingResourcePatterns = sessionFactoryHelper.getMappingResourcePatterns();
		setHibernateProperties(sessionFactoryHelper.getHibernateProperties());
	}

	public DataSource getReadDataSource() {
		return readDataSource;
	}

	public void setReadDataSource(DataSource readDataSource) {
		this.readDataSource = readDataSource;
	}

	public static DataSource getConfigTimeReadDataSource() {
		return configTimeReadDataSourceHolder.get();
	}
}
