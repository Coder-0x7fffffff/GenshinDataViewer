package space.xiami.project.genshindataviewer.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import space.xiami.project.genshindataviewer.web.scheduled.ResourceScheduled;

import javax.annotation.Resource;

@Service
public class SpringContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ResourceScheduled resourceScheduled;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            doAfterSpringLoaded();
        }
    }

    private void doAfterSpringLoaded(){
        // 完成加载后执行资源刷新任务
        resourceScheduled.refreshResource();
    }
}
