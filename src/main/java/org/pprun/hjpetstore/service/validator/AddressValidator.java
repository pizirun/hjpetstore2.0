package org.pprun.hjpetstore.service.validator;

import org.pprun.hjpetstore.domain.Address;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class AddressValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Address.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object obj, Errors errors) {
        validateAddress((Address) obj, errors);
    }
    
    
    public void validateAddress(Address address, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "addr1", "required", new String[] {"address"});
        ValidationUtils.rejectIfEmpty(errors, "city", "required", new String[] {"city"});
        ValidationUtils.rejectIfEmpty(errors, "state", "required", new String[] {"state"});
        ValidationUtils.rejectIfEmpty(errors, "zipcode", "required", new String[] {"zip code"});
        ValidationUtils.rejectIfEmpty(errors, "country", "required", new String[] {"country"});
    }
}
