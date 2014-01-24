/**
 * 
 */
package com.collegediary.platform.hbm;

import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * @author gaurav.khullar
 * 
 */
public class CDHibernateTransactionManager extends HibernateTransactionManager {
	private static final long serialVersionUID = 8662235148429367656L;

	public CDHibernateTransactionManager() {
		super();

		logger = LogFactory.getLog(HibernateTransactionManager.class);
	}

	public CDHibernateTransactionManager(SessionFactory sessionFactory) {
		super(sessionFactory);

		logger = LogFactory.getLog(HibernateTransactionManager.class);
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		CDLocalDataSourceConnectionProvider.setReadTransaction(definition
				.isReadOnly());

		super.doBegin(transaction, definition);
	}

}
