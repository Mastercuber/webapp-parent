package de.avensio.common.web.controller.shop.view;

import de.avensio.common.persistence.model.shop.Product;
import de.avensio.common.service.shop.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

@Controller
class MainController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/")
    public ModelAndView home(@Param("genre") String genre) {
        ModelAndView mv = new ModelAndView("shop/home");
        List<Product> products;

        if (genre == null || genre.equals("all")) {
            products = productService.findAll();
        } else {
            products = productService.findProductsByGenre(genre);
        }

        mv.addObject("products", products);
        return mv;
    }

    @RequestMapping("/product/{id}")
    public ModelAndView productDetails(@PathVariable("id") final Long id) {
        ModelAndView mv = new ModelAndView("shop/product-details");
        mv.addObject("product", productService.findById(id));

        return mv;
    }
}
