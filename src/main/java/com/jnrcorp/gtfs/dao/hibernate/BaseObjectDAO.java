package com.jnrcorp.gtfs.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.SQLQuery;

import com.jnrcorp.gtfs.dao.model.DAOBaseObject;

public interface BaseObjectDAO {

	<T> T loadProxy(Class<T> theClass, Serializable id);

	<T> T get(Class<T> theClass, Serializable id);

	<T> T getNullSafe(Class<T> theClass, Serializable id);

	<T> List<T> getAll(Class<T> theClass);

	void flushSession();

	Serializable save(Object o);

	void saveOrUpdate(Object object);

	void saveAll(Collection<? extends DAOBaseObject> entities);

	void saveStateless(Collection<? extends DAOBaseObject> entities);

	<T> void removeObject(T object);

	<T> void removeObject(Class<T> clazz, Serializable id);

	<T> void removeAll(Collection<T> entities);

	<T> void removeAllWithoutLoading(Class<T> clazz);

	SQLQuery createSQLQuery(String sql);

	Integer getSQLCount(String jndiName, String sql, List<Object> parameters);

	int executeSQL(String jndiName, String sql, List<Object> params, boolean returnLastId);

	void executeBatchSQL(String jndiName, String sql, List<List<Object>> paramBatches, Integer maxBatchSize);

	<T> List<T> getBaseObjects(Class<T> clazz, Collection<Integer> ids);

	void refresh(Object o);

	void evict(Object entity);

}
