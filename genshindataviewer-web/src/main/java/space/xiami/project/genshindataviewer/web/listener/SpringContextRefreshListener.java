package space.xiami.project.genshindataviewer.web.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import space.xiami.project.genshindataviewer.web.scheduled.ResourceScheduled;

import javax.annotation.Resource;

/**
 * @author Xiami
 */
@Service
public class SpringContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(SpringContextRefreshListener.class);

    @Resource
    private ResourceScheduled resourceScheduled;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            doAfterSpringLoaded();
        }
    }

    private void doAfterSpringLoaded(){
        // 所有相关文件
        log.info("All related file list:\n{}", JSON.toJSONString(resourceScheduled.getRelatedFiles(), SerializerFeature.PrettyFormat));
        // 完成加载后执行资源刷新任务
        resourceScheduled.refreshResource();
    }
}
