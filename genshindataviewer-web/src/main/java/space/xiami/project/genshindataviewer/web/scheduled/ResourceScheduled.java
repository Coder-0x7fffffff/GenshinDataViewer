package space.xiami.project.genshindataviewer.web.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.factory.TextMapFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceScheduled implements InitializingBean {

    Logger log = LoggerFactory.getLogger(ResourceScheduled.class);

    @Resource
    private TextMapFactory textMapManager;

    @Scheduled(cron = "${resource.schedule.cron}")
    public void doTask() {
        log.info("ResourceScheduled task time: "+System.currentTimeMillis());
        //TODO 通过OSS定时获取最新数据
        List<String> reloadFiles = new ArrayList<>();
        //刷新资源数据
        for(String path : reloadFiles){
            textMapManager.reload(path);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        doTask();
    }
}
