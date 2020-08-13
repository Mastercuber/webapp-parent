package de.avensio.common.persistence.model.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.avensio.common.CommonApplication;
import de.avensio.common.persistence.model.IProduct;
import lombok.extern.java.Log;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Log
public class Product implements IProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String shortDescription;

    @Column
    private String description;

    @Column
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Image coverImage;

    @Column
    private String coverImagePath;

    @Column(name = "amount")
    private Integer amountAvailable;

    @Column
    private String genre;


    public Product() {
        super();
    }

    public Product(final String name) {
        super();
        this.name = name;
    }

    public Product(final String name, final String shortDescription) {
        super();
        this.name = name;
        this.shortDescription = shortDescription;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice(float price) {
        float tmp = price * 100;
        this.price = ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / 100;
    }

    @Override
    public Image getCoverImage() {
        return coverImage;
    }

    public String getCoverImagePath() {
        Image image = getCoverImage();
        String FILE_PATH = CommonApplication.IMAGE_DIR + image.getName();
        FileOutputStream fos = null;

        try {
            Path path = Paths.get(FILE_PATH);
            Files.createFile(path);

            File file = new File(FILE_PATH);
            fos = new FileOutputStream(file);
            fos.write(image.getData());
        } catch (IOException e) {
            // File probably exists
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {}
        }

        return "product/img/" + image.getName();
    }

    @Override
    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
        this.coverImagePath = getCoverImagePath();
    }

    @Override
    public Integer getAmountAvailable() {
        return amountAvailable;
    }

    @Override
    public void setAmountAvailable(Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;

        return new EqualsBuilder()
                .append(id, product.id)
                .append(name, product.name)
                .append(amountAvailable, product.amountAvailable)
                .append(shortDescription, product.shortDescription)
                .append(description, product.description)
                .append(price, product.price)
                .append(coverImage, product.coverImage)
                .append(genre, product.genre)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(2137, 647)
                .append(id)
                .append(name)
                .append(amountAvailable)
                .append(shortDescription)
                .append(description)
                .append(price)
                .append(coverImage)
                .append(genre)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("amountAvailable", amountAvailable)
                .append("shortDescription", shortDescription)
                .append("description", description)
                .append("price", price)
                .append("coverImage", coverImage)
                .append("genre", genre)
                .toString();
    }
}
