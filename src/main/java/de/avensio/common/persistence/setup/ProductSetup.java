package de.avensio.common.persistence.setup;

import de.avensio.common.CommonApplication;
import de.avensio.common.persistence.model.shop.Image;
import de.avensio.common.persistence.model.shop.Product;
import de.avensio.common.service.shop.IImageService;
import de.avensio.common.service.shop.IProductService;
import de.avensio.common.spring.util.Profiles;
import lombok.extern.java.Log;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
@Transactional
@Profile(value = { Profiles.DEVELOPMENT, Profiles.INTEGRATION_TEST })
@Log
public class ProductSetup  implements ApplicationListener<ContextRefreshedEvent> {
    private boolean setupDone = false;
    private Image image;

    @Autowired
    private IProductService productService;
    @Autowired
    private IImageService imageService;

    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!setupDone) {
            log.info("Executing Product Setup");
            createProducts();
            setupDone = true;
            log.info("Product Setup Done");
        }
    }

    private void createProducts() {
        image = createImageIfNotExists(readImage("donkey-kong.webp"));

        createProduct(
                "Donkey Kong 1",
                "Donkey Kong ist ein Klassiker der Arcarde Spiele",
                "Donkey Kong ist ein klassiker unter den Action Spielen. Zuerst erschien es für das Super Nintendo und später auch für ander Nintendo Konsolen.",
                "action"
        );
        createProduct(
                "Donkey Kong 2",
                "Donkey Kong ist ein Klassiker der Arcard Spiele",
                "Donkey Kong ist ein klassiker unter den Action Spielen. Zuerst erschien es für das Super Nintendo und später auch für ander Nintendo Konsolen.",
                "action"
        );
        createProduct(
                "Donkey Kong 3",
                "Donkey Kong ist ein Klassiker der Arcard Spiele",
                "Donkey Kong ist ein klassiker unter den Action Spielen. Zuerst erschien es für das Super Nintendo und später auch für ander Nintendo Konsolen.",
                "action"
        );
        createProduct(
                "Adventure Blaster",
                "Ein weiteres gutes Blaster Spiel",
                "Donkey Kong ist ein klassiker unter den Action Spielen. Zuerst erschien es für das Super Nintendo und später auch für ander Nintendo Konsolen.",
                "adventure"
        );
    }

    private Image createImageIfNotExists(Image image) {
        String[] splitted = image.getName().split("/");
        String name = splitted[splitted.length - 1];
        Image byName = imageService.findByName(name);
        image.setName(name);
        if (byName == null) {
            return imageService.create(image);
        }
        return byName;
    }

    private void createProduct(String name, String shortDescription, String description, String genre) {
        Product product = new Product(name, shortDescription);
        product.setGenre(genre);

        product.setCoverImage(image);
        product.setAmountAvailable(1);
        product.setDescription(description);
        product.setPrice(20.030f);
        createProductIfNotExists(product);
    }

    private void createProductIfNotExists(final Product product) {
        final Product byName = productService.findByName(product.getName());
        if (byName == null) {
            productService.create(product);
        }
    }

    public static Image readImage(String name) {

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        File file = new File(CommonApplication.IMAGE_DIR + name);

        byte[] data;
        String mimeType;
        Tika tika = new Tika();
        try {
            data = Files.readAllBytes(file.toPath());
            mimeType = tika.detect(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new Image(name, mimeType, data);
    }
}
