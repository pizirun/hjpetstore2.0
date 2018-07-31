/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.rest;

import java.security.PrivateKey;
import java.security.PublicKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.RSAUtil;
import org.pprun.hjpetstore.dao.PaymentPartnerDao;
import org.pprun.hjpetstore.dao.RSAKeyDao;
import org.pprun.hjpetstore.domain.PaymentPartner;
import org.pprun.hjpetstore.domain.RSAKey;
import org.pprun.hjpetstore.service.ServiceException;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SecurityServiceImpl implements SecurityService {
    private static final Log log = LogFactory.getLog(SecurityServiceImpl.class);

    private PaymentPartnerDao paymentPartnerDao;
    private RSAKeyDao rsaKeyDao;

    @Required
    public void setPaymentPartnerDao(PaymentPartnerDao paymentPartnerDao) {
        this.paymentPartnerDao = paymentPartnerDao;
    }

    @Required
    public void setRsaKeyDao(RSAKeyDao rsaKeyDao) {
        this.rsaKeyDao = rsaKeyDao;
    }


    @Override
    public PaymentPartner getPaymentPartner(String name) {
        return paymentPartnerDao.getPaymentPartner(name);
    }

    @Override
    public RSAKey getEnabledRSAKey() {
        RSAKey rsaKey = rsaKeyDao.getEnabledRSAKey();
        return rsaKey;
    }

    @Override
    public String encryptCardNumber(String plainCardNumber) throws ServiceException {
        try {
            RSAKey rsaKey = getEnabledRSAKey();
            String publicKeyString = rsaKey.getPublicKey();

            if (log.isDebugEnabled()) {
                log.debug("publicKey=" + publicKeyString);
            }

            PublicKey publicKey = RSAUtil.getPublicKeyFromString(publicKeyString);
            String encryptCardNumber = RSAUtil.encrypt(plainCardNumber, publicKey);

            if (log.isDebugEnabled()) {
                log.debug("encryptCardNumber=" + encryptCardNumber);
            }
            return encryptCardNumber;
        } catch (Exception ex) {
            throw new SecurityServiceException("SecurityServiceImpl- encryptCardNumber failure ", ex);
        }
    }

    @Override
    public String decryptCardNumber(String encryptCardNumber) throws ServiceException {
         try {
            RSAKey rsaKey = getEnabledRSAKey();
            String privateKeyString = rsaKey.getPrivateKey();

            if (log.isDebugEnabled()) {
                log.debug("privateKey=" + privateKeyString);
            }

            PrivateKey privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);
            String plainCardNumber = RSAUtil.decrypt(encryptCardNumber, privateKey);
            if (log.isDebugEnabled()) {
                log.debug("plainCardNumber=" + plainCardNumber);
            }
            return plainCardNumber;
        } catch (Exception ex) {
            throw new SecurityServiceException("SecurityServiceImpl- decryptCardNumber failure ", ex);
        }
    }

    @Override
    public String encryptCardNumberForPartner(String partnerName, String plainCardNumber) throws ServiceException {
        try {
            PaymentPartner paymentPartner = getPaymentPartner(partnerName);
            String publicKeyString = paymentPartner.getPublicKey();

            if (log.isDebugEnabled()) {
                log.debug(paymentPartner.getName() + "'s publicKey=" + publicKeyString);
            }

            PublicKey publicKey = RSAUtil.getPublicKeyFromString(publicKeyString);
            String encryptCardNumber = RSAUtil.encrypt(plainCardNumber, publicKey);

            if (log.isDebugEnabled()) {
                log.debug("encryptCardNumber=" + encryptCardNumber);
            }
            return encryptCardNumber;
        } catch (Exception ex) {
            throw new SecurityServiceException("SecurityServiceImpl- encryptCardNumberForPartner failure ", ex);
        }
    }
}

