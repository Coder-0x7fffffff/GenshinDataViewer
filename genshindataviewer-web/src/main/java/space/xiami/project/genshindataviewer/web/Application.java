package space.xiami.project.genshindataviewer.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(value = "space.xiami.project.genshindataviewer")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
