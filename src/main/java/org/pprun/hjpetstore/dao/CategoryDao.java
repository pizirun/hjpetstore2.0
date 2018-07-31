package org.pprun.hjpetstore.dao;

import java.util.List;
import org.pprun.hjpetstore.domain.Category;

import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface CategoryDao {
  
  List<Category> getCategoryList() throws DataAccessException;
  
  Category getCategory(String categoryName) throws DataAccessException;
  
}
