package org.pprun.hjpetstore.persistence.jaxb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO represents a list of order for Business Intelligence (BI).
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name = "orderList")
public class BiViewOrderList {

    private BigDecimal totalAmount;
    private List<BiViewOrder> biViewOrders;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @XmlElement(name = "order")
    public List<BiViewOrder> getBiViewOrders() {
        if (biViewOrders == null) {
            biViewOrders = new ArrayList<BiViewOrder>();
        }

        return biViewOrders;
    }
}
