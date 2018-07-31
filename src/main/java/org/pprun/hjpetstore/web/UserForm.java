package org.pprun.hjpetstore.web;

import java.io.Serializable;
import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.User;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class UserForm implements Serializable {

    private User user;
    private boolean newUser;
    private String repeatedPassword;
    private boolean generateKey = true;

    public UserForm(User user) {
        this.user = user;
        this.newUser = false;
    }

    public UserForm() {
        this.user = new User();

        // create a new address and associate to user
        Address newAddress = new Address();
        user.addAddress(newAddress);

        this.newUser = true;
    }

    public User getUser() {
        return user;
    }

    public boolean isNewUser() {
        return newUser;
    }

//    /**
//     * @return the addresses
//     */
//    public List<Address> getAddresses() {
//        return user.getAddresses();
//    }
    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setGenerateKey(boolean generateKey) {
        this.generateKey = generateKey;
    }

    public boolean isGenerateKey() {
        return generateKey;
    }
}
