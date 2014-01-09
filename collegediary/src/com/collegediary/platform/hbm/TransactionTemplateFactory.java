/**
 * 
 */
package com.collegediary.platform.hbm;

/**
 * @author gaurav.khullar
 *  Factory class for creating Hibernate TransactionTemplate. This is intended to be
 *  called from Spring initialization in applicationContext-CDplatform.xml. 
 * 
 */
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.collegediary.platform.logging.CollegeDiaryLogger;

public class TransactionTemplateFactory {
	/**
	 * Create instance of Hibernate TransactionTemplate with custom isolation level 
	 * as defined by Dialect class regex patterns.
	 * 
	 * @param trxManager
	 * @param defaultIsolationLevel
	 * @param isoLevelToDialectPattProps
	 * @return
	 */
	public static TransactionTemplate createInstance(HibernateTransactionManager trxManager, 
			int defaultIsolationLevel, Properties isoLevelToDialectPattProps)
	{
		SessionFactory sessionFactory = trxManager.getSessionFactory();
		int isolationLevel = defaultIsolationLevel;
		String dialectClass = null;
		if (sessionFactory instanceof SessionFactoryImplementor) {
			try {
				SessionFactoryImplementor imp = (SessionFactoryImplementor) sessionFactory;
				dialectClass = imp.getDialect().getClass().getName();
				if (isoLevelToDialectPattProps != null) {
					for (Map.Entry<Object, Object> entry : isoLevelToDialectPattProps.entrySet()) {
						String curIsoLevel = (String) entry.getKey();
						String dialectPatt = (String) entry.getValue();
						Matcher matcher = Pattern.compile(dialectPatt).matcher(dialectClass);
						if (matcher.matches()) {
							isolationLevel = Integer.parseInt(curIsoLevel);
							break;
						}
					}
				}
			} catch (Throwable t) {
				CollegeDiaryLogger.error(TransactionTemplateFactory.class.getName(), "createInstance: " +
						"Failed to match Hibernate dialect pattern to isolation level, fallback to default", t,true);
			}
		} else {
			CollegeDiaryLogger.warn(TransactionTemplateFactory.class.getName(), "createInstance", 
					"Can't determine Hibernate dialect, fallback to default");
		}
		TransactionTemplate trxTemp = new TransactionTemplate(); 
		trxTemp.setIsolationLevel(isolationLevel);
		CollegeDiaryLogger.info(TransactionTemplateFactory.class.getName(), "createInstance", 
				"Created TransactionTemplate, isolation level=" + isolationLevel + 
				(dialectClass == null ? "" : (", dialect=" + dialectClass)));
		trxTemp.setTransactionManager(trxManager);
		return trxTemp;
	}
	
}
