package mutuku.coke.bankingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class BankingappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingappApplication.class, args);
    }

}
