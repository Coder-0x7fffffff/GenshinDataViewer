package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.service.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.EquipAffixExcelConfigData;

import java.io.IOException;
import java.util.*;

/**
 * @author Xiami
 */
@Component
public class EquipAffixFactory extends AbstractFileBaseFactory {

    public static final Logger log = LoggerFactory.getLogger(EquipAffixFactory.class);

    public static final String equipAffixExcelConfigDataFile = "EquipAffixExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    /**
     * id -> affixId -> DO
     */
    private final Map<Long, Map<Long ,EquipAffixExcelConfigData>> equipAffixExcelConfigDataMap = new HashMap<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + equipAffixExcelConfigDataFile);
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        try{
            if(path.endsWith(SPLASH + equipAffixExcelConfigDataFile)) {
                List<EquipAffixExcelConfigData> array = readJsonArray(path, EquipAffixExcelConfigData.class);
                for (EquipAffixExcelConfigData data : array) {
                    Map<Long, EquipAffixExcelConfigData> innerMap = equipAffixExcelConfigDataMap.computeIfAbsent(data.getId(), v -> new HashMap<>());
                    if(innerMap.containsKey(data.getAffixId())){
                        log.warn("Ignore same affixId={}", data.getAffixId());
                        continue;
                    }
                    innerMap.put(data.getAffixId(), data);
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        if(path.endsWith(SPLASH + equipAffixExcelConfigDataFile)) {
            equipAffixExcelConfigDataMap.clear();
        }
    }

    public Map<Long ,EquipAffixExcelConfigData> getById(Long id){
        readLock();
        Map<Long, EquipAffixExcelConfigData> result = equipAffixExcelConfigDataMap.get(id);
        readUnlock();
        return result;
    }

    public EquipAffixExcelConfigData getByIdAffixId(Long id, Long affixId){
        readLock();
        EquipAffixExcelConfigData result = null;
        if(equipAffixExcelConfigDataMap.containsKey(id)){
            result = equipAffixExcelConfigDataMap.get(id).get(affixId);
        }
        readUnlock();
        return result;
    }
}
