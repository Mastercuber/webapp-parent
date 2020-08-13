package de.avensio.common.web.controller;

import de.avensio.common.persistence.model.shop.Product;
import de.avensio.common.service.shop.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

@Controller
class PathController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/")
    public ModelAndView home(@Param("genre") String genre, HttpServletResponse response) {
        response.setLocale(Locale.GERMANY); // Bugfix for rendering fragments
        ModelAndView mv = new ModelAndView("layout");
        List<Product> products;
        if (genre == null || genre.equals("all")) {
            products = productService.findAll();
        } else {
            products = productService.findProductsByGenre(genre);
        }
        mv.addObject("products", products);
        return mv;
    }
}
