package org.pprun.hjpetstore.dao.hibernate;

import org.hibernate.criterion.Restrictions;
import org.pprun.hjpetstore.dao.RSAKeyDao;
import org.pprun.hjpetstore.domain.RSAKey;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * {@literal DAO} annotated {@code @Repository}is eligible for Spring DataAccessException translation.
 * @see Repository
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Repository
public class HibernateRSAKeyDao extends HibernateDaoSupport implements RSAKeyDao {

    @Override
    public RSAKey getEnabledRSAKey() throws DataAccessException {
        return (RSAKey) getSession().createCriteria(RSAKey.class)
                .add(Restrictions.eq("enabled", true))
                .uniqueResult();
    }
}
