package org.pprun.hjpetstore.dao;

import org.pprun.hjpetstore.domain.PaymentPartner;

import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface PaymentPartnerDao {

    PaymentPartner getPaymentPartner(String name) throws DataAccessException;

}
