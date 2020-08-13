package de.avensio.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class CommonApplication {
    public static String IMAGE_DIR = "/img/";

    public static void main(String[] args) throws Exception {
        IMAGE_DIR = System.getProperty("user.dir") + "/img/";
        Files.createDirectories(Paths.get(IMAGE_DIR));
        SpringApplication.run(CommonApplication.class, args);
    }

}
