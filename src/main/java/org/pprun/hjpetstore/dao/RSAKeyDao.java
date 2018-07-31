package org.pprun.hjpetstore.dao;

import org.pprun.hjpetstore.domain.RSAKey;

import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface RSAKeyDao {

    /**
     * There should be only one enabled key.
     * @return
     * @throws DataAccessException
     */
    RSAKey getEnabledRSAKey() throws DataAccessException;

}
