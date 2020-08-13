package de.avensio.common.service.shop.impl;

import de.avensio.common.service.shop.IShoppingCartService;
import de.avensio.common.web.session.HttpSessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    @Autowired
    HttpSession session;

    @Override
    public void deleteItem(Long id) {
        Set<Long> cartItems = (HashSet<Long>) session.getAttribute(HttpSessionConstants.SHOPPING_CART_ITEMS);
        if (cartItems.isEmpty()) return;

        cartItems.remove(id);
    }

    @Override
    public void addItem(Long id) {
        Set<Long> cartItems = (HashSet<Long>) session.getAttribute(HttpSessionConstants.SHOPPING_CART_ITEMS);
        cartItems.add(id);
    }
}
