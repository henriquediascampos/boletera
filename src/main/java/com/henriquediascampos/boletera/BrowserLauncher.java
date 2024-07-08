package com.henriquediascampos.boletera;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class BrowserLauncher implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(BrowserLauncher.class.getName());

    @Value("${app.browser.url}")
    private String url;


    @Override
    public void run(String... args) {
        logger.info("Attempting to open browser using Runtime at URL: " + url);
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                logger.info("Detected Windows OS.");
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                logger.info("Detected macOS.");
                Runtime.getRuntime().exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                logger.info("Detected Unix or Linux OS.");
                Runtime.getRuntime().exec("xdg-open " + url);
            } else {
                logger.warning("Unsupported OS.");
            }
            logger.info("Browser opened successfully.");
        } catch (Exception e) {
            logger.severe("Failed to open browser: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
