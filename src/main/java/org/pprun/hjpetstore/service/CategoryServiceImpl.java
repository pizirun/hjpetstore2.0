package org.pprun.hjpetstore.service;

import java.util.List;
import org.pprun.hjpetstore.dao.CategoryDao;
import org.pprun.hjpetstore.domain.Category;
import org.springframework.beans.factory.annotation.Required;

/**
 * CategoryService implementation.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;

    @Required
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getCategoryList() {
        return this.categoryDao.getCategoryList();
    }

    @Override
    public Category getCategory(String categoryName) {
        return this.categoryDao.getCategory(categoryName);
    }
}
