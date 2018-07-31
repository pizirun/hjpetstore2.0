/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.dao.hibernate;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.pprun.hjpetstore.dao.AbstractDaoTest;
import org.pprun.hjpetstore.dao.CategoryDao;
import org.pprun.hjpetstore.domain.Category;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test cases for {@link #CategoryDao}.
 * Current it is ignored because we first build there should no order (and we did not insert data in initialize script)
 * so the test case will be fail and this prevent us from successful build.
 * After place order and persisted data into db, we can simply remove the {@code @Ignore } annotation.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Ignore
@ContextConfiguration(locations = {"HibernateCategoryDaoTest-context.xml"})
public class HibernateCategoryDaoTest extends AbstractDaoTest {

    @Resource(name="testCategoryDao")
    private CategoryDao categoryDao;

    /**
     * Test of getCategoryList method, of class HibernateCategoryDao.
     */
    @Test
    public void testGetCategoryList() {
        System.out.println("getCategoryList");

        List<Category> categoryList = categoryDao.getCategoryList();

        // Use the inherited countRowsInTable() convenience method from
        // AbstractTransactionalJUnit4SpringContextTests to verify the results.
        assertEquals("Hibernate query must return the same number of vets", super.countRowsInTable("Category"), categoryList.size());

    }

    /**
     * Test of getCategory method, of class HibernateCategoryDao.
     */
    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        String categoryName = "DOGS";

        Category result = categoryDao.getCategory(categoryName);

        assertNotNull("The result set should not be null", result);

        assertEquals(categoryName, result.getCategoryName());
    }
}
