package masterspringsecurity;

import masterspringsecurity.persistence.ProductRepository;
import masterspringsecurity.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MasterspringsecurityApplication implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(MasterspringsecurityApplication.class,
                              args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("run");
    }
}
