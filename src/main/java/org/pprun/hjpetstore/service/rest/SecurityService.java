package org.pprun.hjpetstore.service.rest;

import org.pprun.hjpetstore.domain.PaymentPartner;
import org.pprun.hjpetstore.domain.RSAKey;
import org.pprun.hjpetstore.service.ServiceException;

/**
 * Service interface for service method.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface SecurityService {

    PaymentPartner getPaymentPartner(String name);

    RSAKey getEnabledRSAKey();

    /**
     * User system wide public key encrypt the card number.
     *
     * @param plainCardNumber the plain text of card number
     * @return the encrypt card number
     */
    String encryptCardNumber(String plainCardNumber) throws SecurityServiceException;

    /**
     * User system wide public key decrypt the card number.
     *
     * @param encryptCardNumber the encrypt card number
     * @return the decrypt card number
     */
    String decryptCardNumber(String encryptCardNumber) throws SecurityServiceException;

    /**
     * Encrypt the cardnumber for the specify the payment partner by using its publicKey assigned to us.
     *
     * @param partnerName the payment partner name
     * @param plainCardNumber the card number to encrypted
     * @return the encrypt card number
     * @throws ServiceException
     */
    String encryptCardNumberForPartner(String partnerName, String plainCardNumber) throws SecurityServiceException;
}
