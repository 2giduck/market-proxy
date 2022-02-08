package topia.duck.market;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketProxyApplication.class, args);
    }
}
