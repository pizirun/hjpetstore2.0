package org.pprun.hjpetstore.persistence;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.*;
import org.hibernate.type.StringType;
import org.hibernate.util.ReflectHelper;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * A generic UserType that handles String-based JDK 5.0 Enums.
 *
 * @author Gavin King
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class StringEnumUserType implements EnhancedUserType, ParameterizedType {

    private static final StringType STRING = new StringType();
    private Class<Enum> enumClass;

    @Override
    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClassname");
        try {
            enumClass = ReflectHelper.classForName(enumClassName);
        } catch (ClassNotFoundException cnfe) {
            throw new HibernateException("Enum class" + enumClassName + " not found", cnfe);
        }
    }

    @Override
    public Class returnedClass() {
        return enumClass;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{STRING.sqlType()};
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object deepCopy(Object value) {
        return value;
    }

    @Override
    public Serializable disassemble(Object value) {
        return (Enum) value;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) {
        return original;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    @Override
    public boolean equals(Object x, Object y) {
        return x == y;
    }

    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    @Override
    public Object fromXMLString(String xmlValue) {
        return Enum.valueOf(enumClass, xmlValue);
    }

    @Override
    public String objectToSQLString(Object value) {
        return '\'' + ((Enum) value).name() + '\'';
    }

    @Override
    public String toXMLString(Object value) {
        return ((Enum) value).name();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws SQLException {
        String name = rs.getString(names[0]);
        return rs.wasNull() ? null : Enum.valueOf(enumClass, name);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws SQLException {
        if (value == null) {
            st.setNull(index, STRING.sqlType());
        } else {
            st.setString(index, ((Enum) value).name());
        }
    }
}
