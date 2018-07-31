package org.pprun.hjpetstore.service;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.CommonUtil;
import org.pprun.common.util.MessageDigestUtil;
import org.pprun.common.util.RSAUtil;
import org.pprun.hjpetstore.dao.CategoryDao;
import org.pprun.hjpetstore.dao.UserDao;
import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.Category;
import org.pprun.hjpetstore.domain.User;
import org.springframework.beans.factory.annotation.Required;

/**
 * UserService implementation.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class UserServiceImpl implements UserService {

    private static final Log log = LogFactory.getLog(UserServiceImpl.class);
    private UserDao userDao;
    private CategoryDao categoryDao;

    @Required
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Required
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public User getUser(String username) {
        return this.userDao.getUser(username);
    }

    @Override
    public User getUserAndFetch(String username) {
        return this.userDao.getUserAndFetch(username);
    }

    @Override
    public User getUser(String username, String password) throws ServiceException {
        String passwordMd5 = null;
        try {
            passwordMd5 = MessageDigestUtil.md5(password.getBytes(CommonUtil.UTF8), username.getBytes(CommonUtil.UTF8));
        } catch (NoSuchAlgorithmException ex) {
            final String message = "MD5 cryptographic algorithm provider is not available in the environment";
            log.fatal(message, ex);
            throw new ServiceException(message, ex);
        } catch (UnsupportedEncodingException ex) {
            final String message = "UTF8 is not available in the environment";
            log.fatal(message, ex);
            throw new ServiceException(message, ex);
        }

        return this.userDao.getUser(username, passwordMd5);
    }

    @Override
    public void insertUser(User user, boolean generateKey) throws ServiceException {
        // set up category to user assocaition, because at this time the favCategory is still not got fully initilized:
        // id is null at least, which will cause org.hibernate.TransientObjectException
        String favCategory = user.getFavCategory().getCategoryName();
        Category category = categoryDao.getCategory(favCategory);
        user.setFavCategory(category);

        String passwordMd5 = null;
        String sha512 = null;
        try {

            passwordMd5 = MessageDigestUtil.md5(user.getPassword().getBytes(CommonUtil.UTF8), user.getUsername().getBytes(CommonUtil.UTF8));
            user.setPassword(passwordMd5);

            if (generateKey) {
                KeyPair keyPair = RSAUtil.generateKeyPair();
                String publicKey = RSAUtil.getKeyAsString(keyPair.getPublic());
                String privateKey = RSAUtil.getKeyAsString(keyPair.getPrivate());
                user.setApiKey(publicKey);
                user.setSecretKey(privateKey);
                
                //sha512 = MessageDigestUtil.sha512(user.getUsername().getBytes(CommonUtil.UTF8));
                //user.setApiKey(sha512);
            }
        } catch (NoSuchAlgorithmException ex) {
            final String message = "SHA-512 cryptographic algorithm provider is not available in the environment";
            log.fatal(message, ex);
            throw new ServiceException(message, ex);
        } catch (UnsupportedEncodingException ex) {
            final String message = "UTF8 is not available in the environment";
            log.fatal(message, ex);
            throw new ServiceException(message, ex);
        }

        this.userDao.insertUser(user);
    }

    @Override
    public void updateUser(User user) throws Exception {
        String passwordMd5 = MessageDigestUtil.md5(user.getPassword().getBytes(CommonUtil.UTF8), user.getUsername().getBytes(CommonUtil.UTF8));
        user.setPassword(passwordMd5);
        this.userDao.updateUser(user);
    }

    @Override
    public Long insertAddress(Address address) {
        return this.userDao.insertAddress(address);
    }

    @Override
    public String getUserSecretKeyForApiKey(String apiKey) throws ServiceException {
        return this.userDao.getUserSecretKeyForApiKey(apiKey);
    }
}
