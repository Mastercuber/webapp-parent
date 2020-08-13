package de.avensio.common.service.shop;

public interface IShoppingCartService {

    void deleteItem(Long id);
    void addItem(Long id);

}
