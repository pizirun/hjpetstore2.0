package org.pprun.hjpetstore.persistence.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO represents a list of ProductSummary to be fed for {@link org.springframework.web.servlet.view.xml.MarshallingView}
 * in RESTful web service.
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name="products")
public class Products {

    private List<ProductSummary> products;

    @XmlElement(name="product")
    public List<ProductSummary> getProductList() {
        if (products == null) {
            products = new ArrayList<ProductSummary>();
        }

        return products;
    }
}
