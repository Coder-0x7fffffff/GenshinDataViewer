package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource
    private EquipAffixFactory equipAffixFactory;

    /**
     * setId -> DO
     */
    private final Map<Long, ReliquarySetExcelConfigData> reliquarySetExcelConfigDataMap = new HashMap<>();

    /**
     * id -> DO
     */
    private final Map<Long, ReliquaryExcelConfigData> reliquaryExcelConfigDataMap = new HashMap<>();

    /**
     * nameTextHash -> id
     */
    private final Map<Long, Long> nameTextMapHash2Id = new HashMap<>();

    /**
     * EquipAffix-nameTextHash -> setId
     */
    private final Map<Long, Long> nameTextMapHash2SetId = new HashMap<>();

    @Override
    protected void load(String path) {
        try{
            if(path.endsWith(SPLASH + reliquarySetExcelConfigDataFile)){
                List<ReliquarySetExcelConfigData> array = readJsonArray(path, ReliquarySetExcelConfigData.class);
                for(ReliquarySetExcelConfigData data : array){
                    if(reliquarySetExcelConfigDataMap.containsKey(data.getSetId())){
                        log.warn("Ignore same reliquarySetExcelConfigData id={}", data.getSetId());
                        continue;
                    }
                    reliquarySetExcelConfigDataMap.put(data.getSetId(), data);
                }
            }else if(path.endsWith(SPLASH + reliquaryExcelConfigDataFile)){
                List<ReliquaryExcelConfigData> array = readJsonArray(path, ReliquaryExcelConfigData.class);
                for(ReliquaryExcelConfigData data : array){
                    if(reliquaryExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same reliquarySetExcelConfigData id={}", data.getId());
                        continue;
                    }
                    reliquaryExcelConfigDataMap.put(data.getId(), data);
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
            nameTextMapHash2SetId.clear();
            nameTextMapHash2Id.clear();
        }else if(path.endsWith(SPLASH + reliquaryExcelConfigDataFile)){
            reliquaryExcelConfigDataMap.clear();
            nameTextMapHash2Id.clear();
        }
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    public ReliquarySetExcelConfigData getReliquarySet(Long setId){
        readLock();
        ReliquarySetExcelConfigData result = reliquarySetExcelConfigDataMap.get(setId);
        readUnlock();
        return result;
    }

    public ReliquaryExcelConfigData getReliquary(Long id){
        readLock();
        ReliquaryExcelConfigData result = reliquaryExcelConfigDataMap.get(id);
        readUnlock();
        return result;
    }

    public Map<String, Long> getName2Id(Byte language){
        readLock();
        Map<String, Long> name2Id = new HashMap<>();
        if(nameTextMapHash2Id.isEmpty()){
            // lazy init
            reliquarySetExcelConfigDataMap.values().forEach(reliquarySetExcelConfigData -> {
                reliquarySetExcelConfigData.getContainsList().forEach(id -> {
                    Long nameTextHash = getReliquary(id).getNameTextMapHash();
                    if(nameTextMapHash2Id.containsKey(nameTextHash)){
                        log.warn("Same nameTextMapHash: {}", nameTextHash);
                        return;
                    }
                    nameTextMapHash2Id.put(nameTextHash, id);
                });
            });
            // 补充套装之外的圣遗物
            reliquaryExcelConfigDataMap.values().forEach(reliquaryExcelConfigData -> {
                if(reliquaryExcelConfigData.getSetId() == null){
                    nameTextMapHash2Id.put(reliquaryExcelConfigData.getNameTextMapHash(), reliquaryExcelConfigData.getId());
                }
            });
        }
        nameTextMapHash2Id.forEach((hash, id) -> {
            String name = textMapFactory.getText(language, hash);
            name2Id.put(name != null ? name : String.valueOf(id), id);
        });
        readUnlock();
        return name2Id;
    }

    public Long getIdByName(Byte language, String name){
        return getName2Id(language).get(name);
    }

    public Map<String, Long> getName2SetId(Byte language){
        readLock();
        Map<String, Long> name2id = new HashMap<>();
        if(nameTextMapHash2SetId.isEmpty()){
            // lazy init
            reliquarySetExcelConfigDataMap.values().forEach(data -> {
                if(data.getEquipAffixId() == null){
                    return;
                }
                equipAffixFactory.getById(data.getEquipAffixId()).values().forEach(equipAffixExcelConfigData -> {
                    Long hash = equipAffixExcelConfigData.getNameTextMapHash();
                    if(nameTextMapHash2SetId.containsKey(hash)){
                        log.warn("Ignore same nameTextMapHash2SetId hash={}", hash);
                        return;
                    }
                    nameTextMapHash2SetId.put(hash, data.getSetId());
                });
            });
        }
        nameTextMapHash2SetId.forEach((hash, setId) -> {
            String name = textMapFactory.getText(language, hash);
            if(name2id.containsKey(name) && !name2id.get(name).equals(setId)){
                log.warn("Same reliquarySet name with different setId, name={}", name);
                return;
            }
            name2id.put(name, setId);
        });
        readUnlock();
        return name2id;
    }

    public Long getSetIdByName(Byte language, String name){
        return getName2SetId(language).get(name);
    }
}
