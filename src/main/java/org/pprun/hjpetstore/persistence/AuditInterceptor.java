package org.pprun.hjpetstore.persistence;

import java.io.Serializable;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * An interceptor that sets audit/timestamp fields for implementation of {@link IAuditable}.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@SuppressWarnings("serial")
public class AuditInterceptor extends EmptyInterceptor {

    private static Log log = LogFactory.getLog(AuditInterceptor.class);
    public static final String CREATE_TIME_FIELD = "createTime";
    public static final String UPDATE_TIME_FIELD = "updateTime";

    /**
     * set updateTime.
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof IAuditable) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (UPDATE_TIME_FIELD.equals(propertyNames[i])) {
                    if (log.isDebugEnabled()) {
                        log.debug("AuditInterceptor:onFlushDirty:" + entity + ".set" + propertyNames[i]);
                    }
                    currentState[i] = Calendar.getInstance();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * set createTime and updateTime.
     */
    @Override
    public boolean onSave(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        boolean isModified = false;
        if (entity instanceof IAuditable) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (UPDATE_TIME_FIELD.equals(propertyNames[i])) {
                    if (log.isDebugEnabled()) {
                        log.debug("AuditInterceptor:onSave:" + entity + ".set" + propertyNames[i]);
                    }
                    state[i] = Calendar.getInstance();
                    isModified = true;
                } else if (CREATE_TIME_FIELD.equals(propertyNames[i])) {
                    if (log.isDebugEnabled()) {
                        log.debug("AuditInterceptor:onSave:" + entity + ".set" + propertyNames[i]);
                    }
                    state[i] = Calendar.getInstance();
                    isModified = true;
                }
            }
        }
        return isModified;
    }
}
