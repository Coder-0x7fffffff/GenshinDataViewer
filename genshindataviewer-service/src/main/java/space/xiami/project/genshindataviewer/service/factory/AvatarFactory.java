package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.json.AvatarExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Component
public class AvatarFactory extends AbstractFileBaseFactory{
    
    public static final Logger log = LoggerFactory.getLogger(AvatarFactory.class);

    public static final String avatarExcelConfigDataFile = "AvatarExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarExcelConfigDataFile);
    }

    private final Map<Long, AvatarExcelConfigData> avatarExcelConfigDataMap = new HashMap<>();

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        try{
            if(path.endsWith("/"+ avatarExcelConfigDataFile)) {
                List<AvatarExcelConfigData> array = readJsonArray(path, AvatarExcelConfigData.class);
                for (AvatarExcelConfigData data : array) {
                    if(avatarExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same id={}", data.getId());
                        continue;
                    }
                    avatarExcelConfigDataMap.put(data.getId(), data);
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }
    
    @Override
    protected void clear(String path) {
        if(path.endsWith("/"+ avatarExcelConfigDataFile)) {
            avatarExcelConfigDataMap.clear();
        }
    }
}
