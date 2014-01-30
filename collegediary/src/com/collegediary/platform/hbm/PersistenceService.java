/**
 * 
 */
package com.collegediary.platform.hbm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.collegediary.model.user.MasterUser;
import com.collegediary.platform.logging.CollegeDiaryLogger;

/**
 * @author gaurav.khullar
 * 
 */
@Transactional(propagation = Propagation.REQUIRED)
public class PersistenceService implements IPersistenceService {
	private CDEntityManager entityManager;

	public CDEntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(CDEntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Object save(Object obj) {
		return entityManager.merge(obj);
	}

	public void persist(Object obj) {
		entityManager.persist(obj);
	}

	public void saveOrUpdate(final Object obj) {
		entityManager.saveOrUpdate(obj);
	}

	public List save(List objs) {
		List updatedObjs = new ArrayList();
		if (null != objs) {
			Iterator iterator = objs.iterator();
			while (iterator.hasNext()) {
				Object updatedObj = entityManager.merge(iterator.next());
				updatedObjs.add(updatedObj);
			}
		}
		objs.clear();
		objs.addAll(updatedObjs);
		return objs;
	}

	public Object find(Class entityClass, Serializable primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	public void remove(Object obj) {
		entityManager.remove(obj);
	}

	public void evict(Object object) {
		entityManager.evict(object);
	}

	public void remove(List objs) {
		if (null != objs) {
			Iterator iterator = objs.iterator();
			while (iterator.hasNext()) {
				entityManager.remove(iterator.next());
			}
		}
	}

	public void lock(Object object) {
		entityManager.lock(object, LockMode.NONE);
	}

	public void flush() {
		entityManager.flush();
	}

	public void clear() {
		entityManager.clear();
	}
	
	public List<Object> selectAll(Class entityName)
	{
		return entityManager.selectAll(entityName);
	}

	public List executeQuery(String sql, Object[] params) {
    	CollegeDiaryLogger.trace(this.getClass().getName(), "executeQuery", "query :"+sql);
        Query query = entityManager.createQuery(sql);
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
               	query.setParameter(index, params[index]);
               	CollegeDiaryLogger.trace(this.getClass().getName(), "executeQuery", "params["+index+"] :"+ params[index]);
            }
        }
        List listResults = query.list();
        CollegeDiaryLogger.trace(this.getClass().getName(), "executeQuery", "results size :"+ (listResults != null ? listResults.size():"null"));
        return listResults;
    }
	
	public void updateQuery(String sqlQuery) {
    	entityManager.createQuery(sqlQuery).executeUpdate();
    }
    
    public int updateQuery(String sql, Object[] params) {
    	CollegeDiaryLogger.trace(this.getClass().getName(), "updateQuery", "query :"+sql);
        Query query = entityManager.createQuery(sql);
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
                //Object param = params[index];
                query.setParameter(index, params[index]);
            }
        }
        int numUpdate = query.executeUpdate();
        CollegeDiaryLogger.trace(this.getClass().getName(), "updateQuery", "numUpdate : " + numUpdate);
        return numUpdate;
    }
    
    public int updateQuery(String sql, Map<String, Object> namedParams) {
    	CollegeDiaryLogger.trace(this.getClass().getName(), "updateQuery", "query :"+sql);
    	Query query = entityManager.createQuery(sql);
        if (namedParams != null) {
	        for (String name: namedParams.keySet()) {
	            Object param = namedParams.get(name);
	            CollegeDiaryLogger.trace(this.getClass().getName(), "updateQuery", "params["+name+"] :"+ param);
	            if (param instanceof Collection) {
	            	query.setParameterList(name, (Collection)param);
	            } else {
	            	query.setParameter(name, param);
	            }
	        }
        }
        int numUpdate = query.executeUpdate();
        CollegeDiaryLogger.trace(this.getClass().getName(), "updateQuery", "numUpdate : " + numUpdate);
        return numUpdate;
    }
    
    public int updateEntity(Class entityClassName,String uniquePropertyName, Object uniquePropertyValue,Map<String, Object> properties) {
    	CollegeDiaryLogger.trace(this.getClass().getName(), "updateEntity", "updating "+entityClassName.getName()+" properties"+properties.keySet().toArray().toString());
    	Map<String,Object> tempMap = new HashMap<String, Object>();
    	tempMap = properties;
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE "+entityClassName.getName()).append(" alisaName SET ");
    	List<Object> params = new ArrayList<Object>();
    	if(tempMap != null){
    		for(String parmName : tempMap.keySet())
    		{
        		if(tempMap.size() >1){
        			sql.append(parmName).append(" = ?").append(" ,");
        			tempMap.remove(parmName);
        		} else {
        			sql.append(parmName).append(" = ?");
        		}
        		params.add(properties.get(parmName));
    		}
    	}
    	sql.append(" WHERE alisaName.").append(uniquePropertyName).append(" = ?");
    	params.add(uniquePropertyValue);
    	int numUpdate = updateQuery(sql.toString(), params.toArray());
        CollegeDiaryLogger.trace(this.getClass().getName(), "updateQuery", "numUpdate : " + numUpdate);
        return numUpdate;
    }
}
