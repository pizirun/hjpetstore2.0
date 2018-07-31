/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.persistence.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name = "PartnerDecyptCardNumber")
public class PartnerDecyptCardNumber extends DecryptCardNumber {

    private String partnerName;

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }
}
