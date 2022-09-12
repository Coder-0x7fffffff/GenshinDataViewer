package space.xiami.project.genshindataviewer.web;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import space.xiami.project.genshindataviewer.client.manager.EquipAffixManager;
import space.xiami.project.genshindataviewer.client.util.PathUtil;

import javax.annotation.Resource;

@SpringBootApplication
@Configuration
@EnableScheduling
@ComponentScan(value = "space.xiami.project.genshindataviewer")
public class Application {

    @Resource
    private EquipAffixManager equipAffixManager;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
