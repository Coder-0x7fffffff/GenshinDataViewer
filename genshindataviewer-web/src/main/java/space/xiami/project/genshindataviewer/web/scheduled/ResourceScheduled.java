package space.xiami.project.genshindataviewer.web.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import space.xiami.project.genshindataviewer.service.factory.AbstractFileBaseFactory;
import space.xiami.project.genshindataviewer.service.util.FileUtil;
import space.xiami.project.genshindataviewer.service.util.PathUtil;
import space.xiami.project.genshindataviewer.web.util.SpringContextUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xiami
 */
@Component
public class ResourceScheduled {

    private static final String REFRESH = "Refresh";
    private static final String EXISTS = "Exists";
    private static final String MISSED = "Missed";

    private static final Integer maxDownloadTimes = 16;

    private Logger log = LoggerFactory.getLogger(ResourceScheduled.class);

    private Map<String, String> fileMd5 = new HashMap<>();

    @Scheduled(cron = "${schedule.resource.refresh.cron}")
    public Map<String, List<String>> refreshResource() {
        log.info("ResourceScheduled task time: "+System.currentTimeMillis());
        Map<String, List<String>> result = new HashMap<>(3);
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        Map<String, AbstractFileBaseFactory> factories = applicationContext.getBeansOfType(AbstractFileBaseFactory.class);
        factories.forEach((name, factory) ->{
            Set<String> relatedFilePath = factory.relatedFilePathSet();
            log.info("{} related file: {}", name, relatedFilePath);
            relatedFilePath.forEach(path -> {
                // 保证文件存在 检查文件是否刷新
                if(checkAndDownload(path)){
                    if(checkRefresh(path)){
                        // 更新数据
                        log.info("{} loaded {}", name, path);
                        factory.reload(path);
                        result.computeIfAbsent(REFRESH, v -> new ArrayList<>()).add(path);
                    }else{
                        result.computeIfAbsent(EXISTS, v -> new ArrayList<>()).add(path);
                    }
                }else{
                    result.computeIfAbsent(MISSED, v -> new ArrayList<>()).add(path);
                }
            });
        });
        for (List<String> value : result.values()) {
            value.sort(String::compareTo);
        }
        return result;
    }

    private boolean checkRefresh(String path) {
        try{
            String md5 = DigestUtils.md5DigestAsHex(new FileInputStream(path));
            boolean refresh = true;
            if(fileMd5.containsKey(path)){
                refresh = !md5.equals(fileMd5.get(path));
            }
            if(refresh){
                fileMd5.put(path, md5);
            }
            log.info("path:{} md5:{} refresh:{}", path, md5, refresh);
            return refresh;
        } catch (IOException e) {
            log.error("Get md5 error", e);
        }
        return true;
    }

    private boolean checkAndDownload(String path){
        int tryTimes = 0;
        while(!FileUtil.isExists(path) && tryTimes < maxDownloadTimes){
            tryTimes++;
            log.info("{} not exists, try to download {} ......", tryTimes,path);
            // TODO 通过MD5检查文件是否需要更新，需要更新就从网络下载文件
        }
        return FileUtil.isExists(path);
    }

    public List<String> getRelatedFiles(){
        Set<String> files = new HashSet<>();
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        Map<String, AbstractFileBaseFactory> factories = applicationContext.getBeansOfType(AbstractFileBaseFactory.class);
        factories.forEach((name, factory) ->{
            files.addAll(factory.relatedFilePathSet().stream().map(PathUtil::localPath2ResourcePath).collect(Collectors.toList()));
        });
        List<String> fileList = new ArrayList<>(files);
        fileList.sort(String::compareTo);
        return fileList;
    }
}
