package org.pprun.hjpetstore.service;

import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.User;

/**
 * Service interface for User process.
 *
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface UserService {

    User getUser(String username);

    User getUserAndFetch(String username);

    User getUser(String username, String password) throws ServiceException;

    void insertUser(User user, boolean generateKey) throws ServiceException;

    void updateUser(User user) throws Exception;

    Long insertAddress(Address address);

    String getUserSecretKeyForApiKey(String apiKey) throws ServiceException;
}
