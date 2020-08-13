package de.avensio.common.web.controller.shop.view;

import de.avensio.common.service.shop.IProductService;
import de.avensio.common.service.shop.IShoppingCartService;
import de.avensio.common.web.session.HttpSessionConstants;
import de.avensio.common.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;

@Controller
@RequestMapping(value = WebConstants.CART)
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView cart(HttpSession session) {
        ModelAndView mv = new ModelAndView("shop/shopping-cart");
        HashSet<Long> cartItems = (HashSet<Long>) session.getAttribute(HttpSessionConstants.SHOPPING_CART_ITEMS);
        if (cartItems == null) {
            mv.addObject("products", new ArrayList<>());
        } else {
            mv.addObject("products", productService.findProductsByIds(new ArrayList<>(cartItems)));
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToOrDeleteFromCart(@Param("productId") final Long productId, @RequestParam(value = "delete", defaultValue = "false") final Boolean delete) {

        if (delete) {
            shoppingCartService.deleteItem(productId);
            return "redirect:/cart";
        } else {
            shoppingCartService.addItem(productId);
        }

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteFromCart(@Param("productId") Long productId) {
        shoppingCartService.deleteItem(productId);
        return "redirect:/cart";
    }
}
