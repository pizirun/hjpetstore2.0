package org.pprun.hjpetstore.dao;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Base class for hjpetstore DAO tests.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@ContextConfiguration(locations = {"hibernate-dao-local-test.xml"})
public abstract class AbstractDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    // common fields and method can put here
    @Resource(name = "sessionFactory")
    protected SessionFactory sessionFactory;

    /**
     * Manual flush is required to avoid false positive in test when using ORM framework
     */
    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }
}
