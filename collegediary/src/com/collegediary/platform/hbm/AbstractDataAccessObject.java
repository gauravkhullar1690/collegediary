/**
 * 
 */
package com.collegediary.platform.hbm;

/**
 * @author gaurav.khullar
 *
 */
public class AbstractDataAccessObject {
	protected IPersistenceService persistenceService;
	
	public AbstractDataAccessObject() {
		persistenceService = (IPersistenceService)ServiceLocator.findService("persistenceService");
	}

	public AbstractDataAccessObject(IPersistenceService persistenceService) {
		this.persistenceService = persistenceService;	
	}
	
	public Object save(Object object) {
		return persistenceService.save(object);
	}
	
	public void remove(Object object) {
		persistenceService.remove(object);
	}

	public void attachSession(Object object) {
		persistenceService.lock(object);
	}
	public void flush() {
		persistenceService.flush();
	}

	public void clear() {
		persistenceService.clear();
	}
}
