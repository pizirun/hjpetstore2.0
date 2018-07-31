package org.pprun.hjpetstore.domain;

import javax.xml.bind.annotation.XmlTransient;
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Address extends DomainObject {

    public enum AddressStatus {

        ACTIVE("ACTIVE"),
        NOTUSED("NOTUSED");
        private final String status;

        private AddressStatus(String status) {
            this.status = status;
        }

        public String value() {
            return status;
        }

        @Override
        public String toString() {
            return status;
        }
    }

    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private AddressStatus status = AddressStatus.ACTIVE;

    private User user;

    /** default constructor */
    public Address() {

    }


    /**
     */
    public Address(Address other) {
        this.id = other.id; 
        this.version = other.version;
        this.addr1 = other.addr1;
        this.addr2 = other.addr2;
        this.city = other.city;
        this.state = other.state;
        this.zipcode = other.zipcode;
        this.country = other.country;
        this.status = other.status;
        this.user = other.user;
        this.createTime = other.createTime;
        this.updateTime = other.updateTime;
    }

    /**
     * we need to dynamically update ship address.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the status
     */
    public AddressStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(AddressStatus status) {
        this.status = status;
    }

    /**
     * JAXB, make the association transient.
     * @return the user
     */
    @XmlTransient
    public User getUser() {
        return user;
    }

    /**
     * @param users the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void setActiveStatus() {
        this.status = AddressStatus.ACTIVE;
    }

    public void setNonActiveStatus() {
        this.status = AddressStatus.NOTUSED;
    }

    public boolean isActive() {
        return AddressStatus.ACTIVE.equals(status);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("addr1=").append(addr1);
        s.append(", ");
        s.append("addr2=").append(addr2);
        s.append(", ");
        s.append("city=").append(city);
        s.append(", ");
        s.append("state=").append(state);
        s.append(", ");
        s.append("zipcode=").append(zipcode);
        s.append(", ");
        s.append("country=").append(country);
        s.append("]");

        return s.toString();
    }
}
