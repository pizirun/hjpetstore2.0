package org.pprun.hjpetstore.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.pprun.hjpetstore.dao.CategoryDao;
import org.pprun.hjpetstore.domain.Category;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * {@literal DAO} annotated {@code @Repository}is eligible for Spring DataAccessException translation.
 * @see Repository
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Repository
public class HibernateCategoryDao extends HibernateDaoSupport implements CategoryDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> getCategoryList() throws DataAccessException {
        return getSession().createCriteria(Category.class).list();
    }

    @Override
    public Category getCategory(String categoryName) throws DataAccessException {
        return (Category) getSession().createCriteria(Category.class)
                .add(Restrictions.eq("categoryName", categoryName))
                .uniqueResult();
    }
}
