package org.pprun.hjpetstore.dao.hibernate;

import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.pprun.hjpetstore.dao.ProductDao;
import org.pprun.hjpetstore.domain.Product;
import org.pprun.hjpetstore.persistence.jaxb.ProductSummary;

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
public class HibernateProductDao extends HibernateDaoSupport implements ProductDao {

    private final static int MAX_PER_PAGE = 100;

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getProductListByCategory(String categoryName, int page, int max) throws DataAccessException {
        if (page < 1) {
            page = 1;
        }
        if (max < 1 || max > MAX_PER_PAGE) {
            max = MAX_PER_PAGE;
        }

        return getSession().createCriteria(Product.class)
                .createAlias("category", "c")
                .add(Restrictions.eq("c.categoryName", categoryName))
                .setFirstResult((page-1) * max)
                .setMaxResults(max)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Product getProduct(String productNumber) throws DataAccessException {
        return (Product) getSession().createCriteria(Product.class)
                .setFetchMode("category", FetchMode.JOIN)
                .add(Restrictions.eq("productNumber", productNumber))
                .uniqueResult();
    }

    /**
     * MatchMode.ANYWHERE means ilike '%keyword%'
     * @param keywords
     * @return
     * @throws DataAccessException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> searchProductList(String keywords, int page, int max) throws DataAccessException {
        if (page < 1) {
            page = 1;
        }
        if (max < 1 || max > MAX_PER_PAGE) {
            max = MAX_PER_PAGE;
        }

        return getSession().createCriteria(Product.class)
                .createAlias("category", "c")
                .add(Restrictions.or(Restrictions.ilike("productName", keywords, MatchMode.ANYWHERE),
                    Restrictions.or(Restrictions.ilike("productDesc", keywords, MatchMode.ANYWHERE),
                    Restrictions.ilike("c.categoryName", keywords, MatchMode.ANYWHERE))))
                .setFirstResult((page-1) * max)
                .setMaxResults(max)
                .list();
    }

    /**
     * This is the lightweight comparing to fetch all associations eagerly,
     * this method only projection on product name and product description and with pagination.
     *
     * MatchMode.ANYWHERE means ilike '%keyword%'
     * @param keywords
     * @return
     * @throws DataAccessException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ProductSummary> searchProductLightweightList(String keywords, int page, int max) throws DataAccessException {
        if (page < 1) {
            page = 1;
        }
        if (max < 1 || max > MAX_PER_PAGE) {
            max = MAX_PER_PAGE;
        }

        return getSession().createCriteria(Product.class)
                .createAlias("this.category", "c")
                .add(Restrictions.or(Restrictions.ilike("this.productName", keywords, MatchMode.ANYWHERE),
                    Restrictions.or(Restrictions.ilike("this.productDesc", keywords, MatchMode.ANYWHERE),
                    Restrictions.ilike("c.categoryName", keywords, MatchMode.ANYWHERE))))
                .setProjection(Projections.projectionList()
                .add(Projections.property("this.productName").as("productName"))
                .add( Projections.property("this.productDesc").as("productDesc"))
                .add( Projections.property("this.image").as("image")))
                .setResultTransformer(new AliasToBeanResultTransformer(ProductSummary.class))
                .setFirstResult((page-1) * max)
                .setMaxResults(max)
                .list();
    }
}
