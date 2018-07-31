/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.persistence.jaxb.supplier;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This POJO will be fed for {@link org.springframework.web.servlet.view.xml.MarshallingView} while marshaller
 * in RESTful web service.
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name = "SupplierOrderStatusResponse")
public class SupplierOrderStatusResponse {

    public enum Result {

        SUCCESS("SUCCESS"),
        FAILED("FAILED");
        private final String result;

        private Result(String result) {
            this.result = result;
        }

        public String value() {
            return result;
        }

        @Override
        public String toString() {
            return result;
        }
    }
    private Result result;

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
    
}
