package org.pprun.hjpetstore.service.validator;

import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class UserValidator implements Validator {

    private final Validator addressValidator;

    public UserValidator(Validator addressValidator) {
        if (addressValidator == null) {
            throw new IllegalArgumentException(
                    "The supplied [Validator] is required and must not be null.");
        }
        if (!addressValidator.supports(Address.class)) {
            throw new IllegalArgumentException(
                    "The supplied [Validator] must support the validation of [Address] instances.");
        }
        this.addressValidator = addressValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstname", "required", new String[]{"firstname"});
        ValidationUtils.rejectIfEmpty(errors, "lastname", "required", new String[]{"lastname"});
        ValidationUtils.rejectIfEmpty(errors, "email", "required", new String[]{"email address"});
        ValidationUtils.rejectIfEmpty(errors, "phone", "required", new String[]{"phone number"});

        User user = (User) obj;
        if (user.getAddresses() != null) {
            int i = 0;
            for (Address address : user.getAddresses()) {
                errors.setNestedPath("user.addresses[" + i + "]");
                ValidationUtils.invokeValidator(this.addressValidator, address, errors);

                i++;
            }
        }
    }
}
