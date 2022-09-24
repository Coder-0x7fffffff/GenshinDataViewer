package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import space.xiami.project.genshindataviewer.domain.json.ReliquaryExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.ReliquarySetExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Xiami
 */
@Component
public class ReliquaryFactory extends AbstractFileBaseFactory{

    public static final Logger log = LoggerFactory.getLogger(ReliquaryFactory.class);

    public static final String reliquarySetExcelConfigDataFile = "ReliquarySetExcelConfigData.json";

    public static final String reliquaryExcelConfigDataFile = "ReliquaryExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + reliquarySetExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + reliquaryExcelConfigDataFile);
    }

    @Resource
    private TextMapFactory textMapFactory;

    /**
     * setId -> DO
     */
    private final Map<Long, ReliquarySetExcelConfigData> reliquarySetExcelConfigDataMap = new HashMap<>();

    /**
     * id -> DO
     */
    private final Map<Long, ReliquaryExcelConfigData> reliquaryExcelConfigDataMap = new HashMap<>();

    /**
     * nameTextHash -> Id
     */
    private final Map<Long, List<Long>> nameTextMapHash2Ids = new HashMap<>();

    @Override
    protected void load(String path) {
        try{
            if(path.endsWith(SPLASH + reliquarySetExcelConfigDataFile)){
                List<ReliquarySetExcelConfigData> array = readJsonArray(path, ReliquarySetExcelConfigData.class);
                for(ReliquarySetExcelConfigData data : array){
                    if(reliquarySetExcelConfigDataMap.containsKey(data.getSetId())){
                        log.warn("Ignore same reliquarySetExcelConfigData weaponId={}", data.getSetId());
                        continue;
                    }
                    reliquarySetExcelConfigDataMap.put(data.getSetId(), data);
                }
            }else if(path.endsWith(SPLASH + reliquaryExcelConfigDataFile)){
                List<ReliquaryExcelConfigData> array = readJsonArray(path, ReliquaryExcelConfigData.class);
                for(ReliquaryExcelConfigData data : array){
                    if(reliquaryExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same reliquarySetExcelConfigData weaponId={}", data.getId());
                        continue;
                    }
                    reliquaryExcelConfigDataMap.put(data.getId(), data);
                    nameTextMapHash2Ids.computeIfAbsent(data.getNameTextMapHash(), v -> new ArrayList<>()).add(data.getId());
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        if(path.endsWith(SPLASH + reliquarySetExcelConfigDataFile)){
            reliquarySetExcelConfigDataMap.clear();
        }else if(path.endsWith(SPLASH + reliquaryExcelConfigDataFile)){
            reliquaryExcelConfigDataMap.clear();
            nameTextMapHash2Ids.clear();
        }
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    public ReliquarySetExcelConfigData getReliquarySet(Long setId){
        return reliquarySetExcelConfigDataMap.get(setId);
    }

    public ReliquaryExcelConfigData getReliquary(Long id){
        return reliquaryExcelConfigDataMap.get(id);
    }


    @Cacheable(cacheNames = "WeaponFactory_name2Ids", unless = "#result.size() == 0")
    public Map<String, List<Long>> getName2Ids(Byte language){
        Map<String, List<Long>> name2Ids = new HashMap<>();
        nameTextMapHash2Ids.forEach((hash, ids) -> {
            String name = textMapFactory.getText(language, hash);
            if(StringUtils.hasLength(name)){
                name2Ids.computeIfAbsent(name, v -> new ArrayList<>()).addAll(ids);
            }else{
                name2Ids.put(String.valueOf(hash), ids);
            }
        });
        return name2Ids;
    }

    public List<Long> getIdByName(Byte language, String name){
        Map<String, List<Long>> name2ids = getName2Ids(language);
        return name2ids.get(name);
    }
}
