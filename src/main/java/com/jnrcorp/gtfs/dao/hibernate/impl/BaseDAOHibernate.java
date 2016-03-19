package com.jnrcorp.gtfs.dao.hibernate.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jnrcorp.gtfs.dao.hibernate.BaseObjectDAO;
import com.jnrcorp.gtfs.dao.model.DAOBaseObject;
import com.jnrcorp.gtfs.exception.DatabaseException;
import com.jnrcorp.gtfs.util.CastingUtils;
import com.jnrcorp.gtfs.util.PersistentUtils;

@SuppressWarnings("unchecked")
@Repository("baseObjectDAO")
@Transactional
public class BaseDAOHibernate implements BaseObjectDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDAOHibernate.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public <T> List<T> find(String queryString, Object... values) {
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return list(queryObject);
	}

	protected <T> List<T> list(Query query) {
		return listAndCast(query.list());
	}

	protected <T> T uniqueResult(Query query) {
		return (T) query.uniqueResult();
	}

	private <T> List<T> listAndCast(List<?> lists) {
		return CastingUtils.listAndCast(lists);
	}

	@Override
	public <T> T loadProxy(Class<T> theClass, Serializable id) {
		return getSession().load(theClass, id);
	}

	@Override
	public <T> T get(Class<T> theClass, Serializable id) {
		return getSession().get(theClass, id);
	}

	@Override
	public <T> T getNullSafe(Class<T> theClass, Serializable id) {
		T object = get(theClass, id);
		if (object == null) {
			LOGGER.warn("uh oh, " + theClass + " '" + id + "' not found...");
			throw new ObjectRetrievalFailureException(theClass, id);
		}
		return object;
	}

	@Override
	public <T> List<T> getAll(Class<T> theClass) {
		Criteria criteria = createCriteria(theClass);
		return list(criteria);
	}

	public Session getSession() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			LOGGER.error("HibernateException: ", e);
			if (session == null) {
				session = getNewHibernateSession();
			}
		}
		return session;
	}

	public Query createQuery(String hql) {
		return getSession().createQuery(hql);
	}

	public Session getNewHibernateSession() {
		return sessionFactory.openSession();
	}

	protected Criteria createCriteria(Class<?> hibernateClass) {
		return getSession().createCriteria(hibernateClass);
	}

	protected Criteria createCriteria(Class<?> hibernateClass, String alias) {
		return getSession().createCriteria(hibernateClass, alias);
	}

	protected <T> List<T> list(Criteria criteria) {
		return listAndCast(criteria.list());
	}

	protected <T> T uniqueResult(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	protected void refreshObject(Object entity) {
		getSession().refresh(entity);
	}

	public void delete(Object object) {
		getSession().delete(object);
	}

	@Override
	public void flushSession() {
		getSession().flush();
	}

	@Override
	public Serializable save(Object o) {
		return getSession().save(o);
	}

	@Override
	public void saveOrUpdate(Object object) {
		getSession().saveOrUpdate(object);
	}

	@Override
	public void saveOrUpdateAll(Collection<? extends DAOBaseObject> entities) {
		int i = 0;
		BigDecimal totalCount = new BigDecimal(entities.size());
		BigDecimal oneHundred = new BigDecimal(100);
		for (Object object : entities) {
			saveOrUpdate(object);
			if (i % 1000 == 0) { // 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				getSession().flush();
				getSession().clear();
				BigDecimal currentCount = new BigDecimal(i);
				BigDecimal percentComplete = currentCount.divide(totalCount, 4, RoundingMode.HALF_UP).multiply(oneHundred).setScale(2);
				LOGGER.info("Flushing at " + i + ". " +  percentComplete + "%");
			}
			++i;
		}
	}

	@Override
	public <T> void removeObject(T object) {
		getSession().delete(object);
	}

	@Override
	public <T> void removeObject(Class<T> clazz, Serializable id) {
		getSession().delete(get(clazz, id));
	}

	@Override
	public <T> void removeAll(Collection<T> entities) {
		for (Object object : entities) {
			delete(object);
		}
	}

	protected Date getSysDate() {
		Date result = new Date();
		SQLQuery qry = getSession().createSQLQuery("select sysdate()");
		List<?> list = list(qry);
		if (list != null && !list.isEmpty()) {
			result = (Date) list.get(0);
		}
		return result;
	}

	@Override
	public SQLQuery createSQLQuery(String sql) {
		return getSession().createSQLQuery(sql);
	}

	@Override
	public Integer getSQLCount(String jndiName, String sql, List<Object> parameters) {
		Connection con = getConnection(jndiName);
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			addParamToStatement(parameters, ps);
			Integer count = 0;
			ResultSet rs = null;
			try {
				rs = ps.executeQuery();
				while (rs.next()) {
					count = rs.getInt(1);
				}
			} finally {
				try {
					ps.close();
				} catch (Exception ignore) {
					LOGGER.error(ignore.getMessage(), ignore);
				}
			}
			return count;
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new DatabaseException(ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	@Override
	public int executeSQL(String jndiName, String sql, List<Object> params, boolean returnLastId) {
		Connection con = getConnection(jndiName);
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			addParamToStatement(params, ps);
			int cnt = 0;
			try {
				cnt = ps.executeUpdate();
			} finally {
				try {
					ps.close();
				} catch (Exception ignore) {
					LOGGER.error(ignore.getMessage(), ignore);
				}
			}
			if (returnLastId) {
				return getLastId(con);
			} else {
				return cnt;
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new DatabaseException(ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	@Override
	public void executeBatchSQL(String jndiName, final String sql, final List<List<Object>> paramBatches, final Integer maxBatchSize) {
		Connection con = getConnection(jndiName);
		try {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(sql);
				int recordCount = 0;
				if (paramBatches != null) {
					for (List<Object> params : paramBatches) {
						addParamToStatement(params, ps);
						ps.addBatch();
						recordCount++;
						if (maxBatchSize != null && recordCount % maxBatchSize == 0) {
							LOGGER.debug("exec batch :" + recordCount);
							ps.executeBatch();
						}
					}
				}
				if (maxBatchSize == null || recordCount % maxBatchSize != 0) {
					LOGGER.debug("exec batch :" + recordCount);
					ps.executeBatch();
				}
				LOGGER.debug("total record:" + recordCount);
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception ignore) {
						LOGGER.error("Ignoring exception occured while saving Batch SQL" + ignore.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
	}

	private Connection getConnection(String jndiName) {
		Connection conn = null;
		InitialContext cxt = null;
		try {
			cxt = new InitialContext();
			DataSource ds = (DataSource) cxt.lookup("java:/comp/env/" + jndiName);
			if (ds == null) {
				throw new DatabaseException("Data source not found!");
			}
			conn = ds.getConnection();
		} catch (DatabaseException | SQLException | NamingException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DatabaseException(e);
		} finally {
			if (cxt != null) {
				try {
					cxt.close();
				} catch (NamingException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return conn;
	}

	private void addParamToStatement(List<Object> params, PreparedStatement ps) throws SQLException {
		for (int i = 0; i < params.size(); i++) {
			Object param = params.get(i);
			int index = i + 1;
			if (param == null) {
				ps.setNull(index, Types.INTEGER);
			} else {
				if (param instanceof Integer) {
					ps.setInt(index, ((Integer) param).intValue());
				} else if (param instanceof Long) {
					ps.setLong(index, ((Long) param).longValue());
				} else if (param instanceof Date) {
					ps.setTimestamp(index, new Timestamp(((Date) param).getTime()));
				} else {
					ps.setString(index, param.toString());
				}
			}
		}
	}

	private int getLastId(Connection con) throws SQLException {
		int id = 0;
		Statement st = con.createStatement();
		try {
			ResultSet rs = st.executeQuery("select last_insert_id()");
			if (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
		} finally {
			st.close();
		}
		return id;
	}

	@Override
	public <T> List<T> getBaseObjects(Class<T> clazz, Collection<Integer> baseObjectIds) {
		if (baseObjectIds.isEmpty()) {
			return Collections.emptyList();
		}
		Criteria criteria = createCriteria(clazz);
		criteria.add(Restrictions.in("id", baseObjectIds));
		return list(criteria);
	}

	@Override
	public <T> Map<Long, T> getBaseObjectsById(Class<T> clazz, Collection<Integer> baseObjectIds) {
		List<T> baseObjects = getBaseObjects(clazz, baseObjectIds);
		return PersistentUtils.objectsListToMapById(baseObjects);
	}

	@Override
	public void refresh(Object object) {
		getSession().refresh(object);
	}

	@Override
	public void evict(Object entity) {
		getSession().evict(entity);
	}

}
