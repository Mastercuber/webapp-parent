package de.avensio.common.persistence.model;

import de.avensio.common.persistence.INameableDto;
import de.avensio.common.persistence.model.shop.Image;

public interface IProduct extends INameableEntity, INameableDto {

    String getShortDescription();
    void setShortDescription(String shortDescription);

    String getDescription();
    void setDescription(String description);

    float getPrice();
    void setPrice(float price);

    Image getCoverImage();
    void setCoverImage(Image coverImage);

    Integer getAmountAvailable();
    void setAmountAvailable(Integer amountAvailable);

    String getGenre();
    void setGenre(String genre);
}
