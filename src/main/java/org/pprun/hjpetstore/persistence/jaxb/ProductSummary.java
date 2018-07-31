package org.pprun.hjpetstore.persistence.jaxb;

/**
 * The ProductSummary is a simple Java bean acted as {@literal DTO} which represents a narrow view of {@link Product}.
 * The intention to be used in {@code HQL select NEW} statement or Criteria AliasToBeanResultTransformer}.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ProductSummary {

    private String productName;
    private String productDesc;
    private String image;

    public ProductSummary() {
    }

    public ProductSummary(String productName, String productDesc) {
        this.productName = productName;
        this.productDesc = productDesc;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
