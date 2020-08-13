package de.avensio.common.web.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

@WebListener
public class ShopHttpSessionEvent implements HttpSessionListener {
    /**
     * Initialize a newly created HttpSession
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(HttpSessionConstants.SHOPPING_CART_ITEMS, new HashSet<Long>());
    }
}
