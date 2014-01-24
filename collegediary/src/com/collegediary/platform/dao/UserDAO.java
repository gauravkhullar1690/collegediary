/**
 * 
 */
package com.collegediary.platform.dao;

import java.util.List;
import com.collegediary.model.user.MasterUser;
import com.collegediary.platform.hbm.AbstractDataAccessObject;

/**
 * @author gaurav.khullar
 *
 */
public class UserDAO extends AbstractDataAccessObject {
	public MasterUser createNewUser(MasterUser masterUser) {
		return (MasterUser)persistenceService.save(masterUser);
	}
	
	public void deleteUser(MasterUser masterUser) {
		persistenceService.remove(masterUser);
		
	}
	
	public void updateUser(MasterUser masterUser) {
		persistenceService.saveOrUpdate(masterUser);
	}

	public List<MasterUser> findUsers() {
		//return (List<MasterUser>)persistenceService.executeQuery("from com.collegediary.model.user.MasterUser mu where mu.username=?",new Object[] {"goravdhima"});
		return persistenceService.findUsers();
	}
	
	public List<MasterUser> findUserByName(String username) {
		return (List<MasterUser>)persistenceService.executeQuery("from com.collegediary.model.user.MasterUser mu where mu.username=?",new Object[] {username});
	}
}
