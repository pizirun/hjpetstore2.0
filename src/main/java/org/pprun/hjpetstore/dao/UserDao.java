package org.pprun.hjpetstore.dao;

import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.User;

import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface UserDao {

    User getUser(String username) throws DataAccessException;

    boolean isUserExistingForApiKey(String apiKey) throws DataAccessException;

    String getUserSecretKeyForApiKey(String apiKey) throws DataAccessException;

    User getUserAndFetch(String username) throws DataAccessException;

    User getUser(String username, String password) throws DataAccessException;

    void insertUser(User user) throws DataAccessException;

    void updateUser(User user) throws DataAccessException;

    Long insertAddress(Address address) throws DataAccessException;
}
