package space.xiami.project.genshindataviewer.web.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.service.factory.AbstractFileBaseFactory;
import space.xiami.project.genshindataviewer.service.util.FileUtil;
import space.xiami.project.genshindataviewer.web.util.SpringContextUtil;

import java.util.Map;
import java.util.Set;

@Component
public class ResourceScheduled {

    Logger log = LoggerFactory.getLogger(ResourceScheduled.class);

    @Scheduled(cron = "${schedule.resource.refresh.cron}")
    public void refreshResource() {
        log.info("ResourceScheduled task time: "+System.currentTimeMillis());
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        Map<String, AbstractFileBaseFactory> factories = applicationContext.getBeansOfType(AbstractFileBaseFactory.class);
        factories.forEach((name, factory) ->{
            Set<String> relatedFilePath = factory.relatedFilePathSet();
            log.info("{} related file: {}", name, relatedFilePath);
            relatedFilePath.forEach(path -> {
                if(!FileUtil.isExists(path)){
                    log.info("{} related {} not exists", name, path);
                    //TODO load file from oss or github?
                }
                if(FileUtil.isExists(path)){
                    log.info("{} loaded {}", name, path);
                    factory.reload(path);
                }
            });
        });
    }
}
