/**
 * 
 */
package com.collegediary.platform.hbm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.util.JDBCExceptionReporter;
import org.springframework.orm.hibernate3.LocalDataSourceConnectionProvider;

/**
 * @author gaurav.khullar
 * 
 */
public class CDLocalDataSourceConnectionProvider extends
		LocalDataSourceConnectionProvider {
	private static final ThreadLocal<Boolean> readTransactionHolder = new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return false;
		}

	};

	private DataSource readDataSource;

	@Override
	public void configure(Properties props) throws HibernateException {
		super.configure(props);

		this.readDataSource = CDLocalSessionFactoryBean
				.getConfigTimeReadDataSource();
	}

	@Override
	public DataSource getDataSource() {
		if (isReadTransaction() && readDataSource != null) {
			return readDataSource;
		}
		return super.getDataSource();
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (isReadTransaction() && readDataSource != null) {
			try {
				return this.readDataSource.getConnection();
			} catch (SQLException ex) {
				JDBCExceptionReporter.logExceptions(ex);
				throw ex;
			}
		}
		return super.getConnection();
	}

	public static void setReadTransaction(boolean read) {
		readTransactionHolder.set(read);
	}

	public static boolean isReadTransaction() {
		return readTransactionHolder.get();
	}
}
