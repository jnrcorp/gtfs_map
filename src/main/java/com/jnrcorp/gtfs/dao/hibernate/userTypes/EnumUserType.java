package com.jnrcorp.gtfs.dao.hibernate.userTypes;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class EnumUserType implements UserType, ParameterizedType {

	private static final String DEFAULT_IDENTIFIER_METHOD_NAME = "name";
	private static final String DEFAULT_VALUE_OF_METHOD_NAME = "valueOf";

	protected static final String IDENTIFIER_METHOD = "identifierMethod";
	protected static final String VALUEOF_METHOD = "valueOfMethod";

	private Class<?> enumClass;
	private Method identifierMethod;
	private Method valueOfMethod;

	public EnumUserType() {
		super();
	}

	@Override
	public void setParameterValues(final Properties parameters) {
		final String enumClassName = parameters.getProperty("enumClass");
		try {
			enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
		} catch (final ClassNotFoundException cfne) {
			throw new HibernateException("Enum class not found", cfne);
		}

		final String identifierMethodName = parameters.getProperty(IDENTIFIER_METHOD, DEFAULT_IDENTIFIER_METHOD_NAME);
		Class<?> identifierType;
		try {
			identifierMethod = enumClass.getMethod(identifierMethodName, new Class[0]);
			identifierType = identifierMethod.getReturnType();
		} catch (final Exception e) {
			throw new HibernateException("Failed to obtain identifier method", e);
		}

		final String valueOfMethodName = parameters.getProperty(VALUEOF_METHOD, DEFAULT_VALUE_OF_METHOD_NAME);
		try {
			valueOfMethod = enumClass.getMethod(valueOfMethodName, new Class[] { identifierType });
		} catch (final Exception e) {
			throw new HibernateException("Failed to obtain valueOf method", e);
		}
	}

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.INTEGER};
	}

	@Override
	public Class<?> returnedClass() {
		return enumClass;
	}

	@Override
	public boolean equals(Object x, Object y) {
		return x == y;
	}

	@Override
	public int hashCode(Object x) {
		return x == null ? 0 : x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws SQLException {
		Integer id = rs.getInt(names[0]);
		if(rs.wasNull()) {
			return null;
		}
		try {
			return valueOfMethod.invoke(enumClass, id.toString());
		} catch (final Exception e) {
			throw new HibernateException("Exception while invoking valueOf method '" + valueOfMethod.getName() + "' of " + "enumeration class '" + enumClass + "'", e);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws SQLException {
		try {
			if (value == null) {
				st.setNull(index, Types.INTEGER);
			} else {
				final Object identifier = identifierMethod.invoke(value, new Object[0]);
				st.setObject(index, identifier);
			}
		} catch(Exception ex) {
			throw new HibernateException("Exception while invoking identifierMethod '" + identifierMethod.getName() + "' of " + "enumeration class '" + enumClass + "'", ex);
		}
	}

	@Override
	public Object deepCopy(Object value) {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) {
		return original;
	}

}
