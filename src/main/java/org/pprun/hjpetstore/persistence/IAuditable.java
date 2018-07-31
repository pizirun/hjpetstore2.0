package org.pprun.hjpetstore.persistence;

import java.util.Calendar;

/**
 * Interface indicates that the domain entity has timestamp field to be set automatically.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface IAuditable {

    public Calendar getCreateTime();

    public Calendar getUpdateTime();
}
