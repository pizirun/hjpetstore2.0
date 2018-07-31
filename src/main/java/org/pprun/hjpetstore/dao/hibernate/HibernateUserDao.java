package org.pprun.hjpetstore.dao.hibernate;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.pprun.hjpetstore.dao.UserDao;
import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.User;

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
public class HibernateUserDao extends HibernateDaoSupport implements UserDao {

    @Override
    public User getUser(String username) throws DataAccessException {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();

    }

    @Override
    public boolean isUserExistingForApiKey(String apiKey) throws DataAccessException {
        return getSession().createCriteria(User.class)
                .add(Restrictions.eq("apiKey", apiKey))
                .uniqueResult() != null;
    }

    @Override
    public String getUserSecretKeyForApiKey(String apiKey) throws DataAccessException {
        return (String) getSession().createCriteria(User.class)
                .add(Restrictions.eq("apiKey", apiKey))
                .setProjection(Projections.property("secretKey"))
                .uniqueResult();
    }

    @Override
    public User getUserAndFetch(String username) throws DataAccessException {

        return (User) getSession().createCriteria(User.class)
                .setFetchMode("favCategory", FetchMode.JOIN) // eagerly load
                .setFetchMode("addresses", FetchMode.JOIN) // eagerly load
                .setFetchMode("roles", FetchMode.JOIN)
                .add(Restrictions.eq("username", username))
                .uniqueResult();

    }

    @Override
    public User getUser(String username, String password) throws DataAccessException {
        return (User) getSession().createCriteria(User.class)
                .setFetchMode("favCategory", FetchMode.JOIN) // eagerly load
                .setFetchMode("addresses", FetchMode.JOIN) // eagerly load
                .setFetchMode("roles", FetchMode.JOIN)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", password))
                .uniqueResult();

    }

    @Override
    public void insertUser(User user) throws DataAccessException {
        getHibernateTemplate().save(user);
    }

    @Override
    public void updateUser(User user) throws DataAccessException {
        if (user.getPassword() != null && user.getPassword().length() > 0) {
            getHibernateTemplate().update(user);
        } // else ?
    }

    @Override
    public Long insertAddress(Address address) throws DataAccessException {
        return (Long) getHibernateTemplate().save(address);
    }
}
