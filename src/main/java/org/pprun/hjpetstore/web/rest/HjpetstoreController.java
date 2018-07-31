/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.persistence.jaxb.Products;
import org.pprun.hjpetstore.service.rest.HjpetstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * A RESTful controller supplies search products by keyword which will be exposed to public access.
 * The access authenticated by api-key and secret key via OAuth as Amazon Web Service.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Controller
public class HjpetstoreController extends BaseController {

    private static final Log log = LogFactory.getLog(HjpetstoreController.class);
    private HjpetstoreService hjpetstoreService;

    @Required
    @Autowired
    public void setHjpetstoreService(HjpetstoreService hjpetstoreService) {
        this.hjpetstoreService = hjpetstoreService;
    }

    /**
     * RESTful match path '/products/{keyword}' with apikey as request parameter.
     *
     * <p>
     * For example: user pprun <br />
     * {@code 
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' -H 'Date: Fri 2011-02-11 19:10:46 UTC' 'http://localhost:8080/hjpetstore/rest/products/{keyword}?apiKey=e4fae4f09fd3b2e6201b7b213d4deae7&signature=zVe5WbWJuJuPQpFLJVpJ4XMYTThdROf5iaU76zdWLweeKvGSLBBJTAce4BayNH07x3poa8gHsIxLkIpFXsd5OQ%3D%3D&page=1&max=100'
     * }
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/products/{keyword}", method = RequestMethod.GET)
    public ModelAndView getProductsByKeyword(
            @RequestParam("page") int page,
            @RequestParam("max") int max,
            @PathVariable("keyword") String keyword) {

        if (log.isDebugEnabled()) {
            log.debug("HjpetstoreController is processing request for keyword: " + keyword);
        }
        Products products = hjpetstoreService.searchProductList(keyword, page, max);

        ModelAndView mav = new ModelAndView("products");
        mav.addObject(products);
        return mav;
    }
}
