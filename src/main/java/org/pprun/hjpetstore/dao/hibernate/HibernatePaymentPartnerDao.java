package org.pprun.hjpetstore.dao.hibernate;

import org.hibernate.criterion.Restrictions;
import org.pprun.hjpetstore.dao.PaymentPartnerDao;
import org.pprun.hjpetstore.domain.PaymentPartner;

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
public class HibernatePaymentPartnerDao extends HibernateDaoSupport implements PaymentPartnerDao {

    @Override
    public PaymentPartner getPaymentPartner(String name) throws DataAccessException {
        return (PaymentPartner) getSession().createCriteria(PaymentPartner.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }
}
