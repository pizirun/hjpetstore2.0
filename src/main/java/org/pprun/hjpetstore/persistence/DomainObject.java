package org.pprun.hjpetstore.persistence;

import java.io.Serializable;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlElement;

/**
 * This is the base class for Domain Objects defines the common contract, such as:
 * <br />
 * <ol>
 * <li>id (surrogate key) - which is treated as read-only field and is mandatory if want to make use of hibernate effective</li>
 * <li>version - which is treated as read-only and for optimistic concurrency control</li>
 * <li>{@literal implements} {@code IAuditable} to set the createTime and updateTime fields automatically</li>
 * <li>{@link Serializable} - which is mandatory by second-level cache provider </li>
 * </ol>
 * <p>The only exception case is if the domain object is using composite id, in that case,
 *    the domain object needs define its own fields.
 *
 * </p>
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public abstract class DomainObject implements IAuditable, Serializable {
    // some of REST domain needs these fields for jaxb
    @XmlElement
    protected Long id;
    @XmlElement
    protected Long version;
    @XmlElement
    protected Calendar createTime;
    @XmlElement
    protected Calendar updateTime;

    public DomainObject() {
    }

    public Long getId() {
        return id;
    }

    public Long getVerion() {
        return version;
    }

    /**
     * @return the createTime
     */
    @Override
    public Calendar getCreateTime() {
        return createTime;
    }

    private void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    @Override
    public Calendar getUpdateTime() {
        return updateTime;
    }

    private void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
}
