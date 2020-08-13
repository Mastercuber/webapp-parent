package de.avensio.common.test;

import de.avensio.common.CommonApplication;
import de.avensio.common.persistence.model.shop.Image;
import de.avensio.common.test.base.IntegrationTestBase;
import org.apache.tika.Tika;
import org.junit.Assert;
import org.junit.Test;
import org.kohsuke.rngom.util.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static de.avensio.common.persistence.setup.ProductSetup.readImage;

public class ImageTest extends IntegrationTestBase {
    @Test
    public void detectMimeType() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File file = new File(classLoader.getResource("static/img/donkey-kong.webp").getFile());
        Tika tika = new Tika();
        try {
            String detect = tika.detect(file);
            Assert.assertEquals("image/webp", detect);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    @Test
    public void imageToDataUri() throws UnsupportedEncodingException {
        Image image = readImage("static/img/donkey-kong.webp");
        byte[] data = image.getData();
        String s = "data:" + image.getMimeType() + ";base64," + Base64.getEncoder().encodeToString(data);
        System.out.println(s);
    }

    @Test
    public void writeImageToImageDir() throws IOException {
        String IMAGE_DIR = new File(".").getCanonicalPath() + "/img/test/noch/";
        String FILE_NAME = IMAGE_DIR + "donkey-kong2.webp";

        Files.createDirectories(Paths.get(IMAGE_DIR));
        Path newFilePath = Paths.get(FILE_NAME);
        Files.createFile(newFilePath);

        Image image = readImage("static/img/donkey-kong.webp");
        byte[] data = image.getData();

        File file = new File(FILE_NAME);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {}
        }

    }
}
