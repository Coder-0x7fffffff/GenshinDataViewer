package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import space.xiami.project.genshindataviewer.domain.json.AvatarExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.WeaponExcelConfigData;
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

    // id -> DO
    private final Map<Long, AvatarExcelConfigData> avatarExcelConfigDataMap = new HashMap<>();

    // nameTextHash -> Id
    private final Map<Long, Long> nameTextMapHash2Id = new HashMap<>();

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
                    if(nameTextMapHash2Id.containsKey(data.getNameTextMapHash())){
                        log.warn("Ignore same nameTextMapHash hash={}", data.getNameTextMapHash());
                        continue;
                    }
                    nameTextMapHash2Id.put(data.getNameTextMapHash(), data.getId());
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
            nameTextMapHash2Id.clear();
        }
    }


    public AvatarExcelConfigData getAvatar(Long id){
        if(!avatarExcelConfigDataMap.containsKey(id)){
            return null;
        }
        return avatarExcelConfigDataMap.get(id);
    }

    @Cacheable(cacheNames = "AvatarFactory_name2Ids", unless = "#result.size() == 0")
    public Map<String, Long> getName2Ids(Byte language){
        Map<String, Long> name2Ids = new HashMap<>();
        nameTextMapHash2Id.forEach((hash, id) -> {
            String name = textMapFactory.getText(language, hash);
            if(StringUtils.hasLength(name)){
                name2Ids.put(name, id);
            }else{
                name2Ids.put(String.valueOf(id), id);
            }
        });
        return name2Ids;
    }

    public Long getIdByName(Byte language, String name){
        Map<String, Long> name2ids = getName2Ids(language);
        return name2ids.get(name);
    }
}
