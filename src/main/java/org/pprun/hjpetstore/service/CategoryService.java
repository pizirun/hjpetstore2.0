package org.pprun.hjpetstore.service;

import java.util.List;
import org.pprun.hjpetstore.domain.Category;

/**
 * Service interface for Category process.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface CategoryService {

    List<Category> getCategoryList();

    Category getCategory(String categoryName);
}
