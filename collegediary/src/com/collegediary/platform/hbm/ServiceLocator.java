
package com.collegediary.platform.hbm;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author gaurav.khullar
 *
 * This class is used to locate services whose life cycle and dependency injection 
 * is managed by the spring framework.
 */
 
public class ServiceLocator implements ApplicationContextAware{
	    private transient ApplicationContext _applicationContext;
	    private static ServiceLocator _instance = new ServiceLocator();
	    
	    public void setApplicationContext(ApplicationContext applicationContext) 
	    						throws BeansException {
	        _instance._applicationContext = applicationContext;
	    }
	    
	    public static ApplicationContext getApplicationContext() {
	    	return _instance._applicationContext;
	    }
	    
	    public static Object findService(String serviceName) {
	        return _instance._applicationContext.getBean(serviceName);
	    }

	    public static boolean containsService(String serviceName) {
	    	return _instance._applicationContext.containsBean(serviceName);
	    }
}

