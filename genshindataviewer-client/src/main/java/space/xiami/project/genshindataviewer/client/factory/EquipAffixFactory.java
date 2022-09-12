package space.xiami.project.genshindataviewer.client.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.EquipAffixExcelConfigData;

import java.io.IOException;
import java.util.*;

@Component
public class EquipAffixFactory extends AbstractFileBaseFactory {

    public static final Logger log = LoggerFactory.getLogger(EquipAffixFactory.class);

    public static final String equipAffixExcelConfigDataFile = "EquipAffixExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    // id -> affixId -> DO
    private final Map<Long, Map<Long ,EquipAffixExcelConfigData>> equipAffixExcelConfigDataMap = new HashMap<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + equipAffixExcelConfigDataFile);
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    protected void load(String path) {
        try{
            if(path.endsWith("/"+ equipAffixExcelConfigDataFile)) {
                List<EquipAffixExcelConfigData> array = readJsonArray(path, EquipAffixExcelConfigData.class);
                for (EquipAffixExcelConfigData data : array) {
                    equipAffixExcelConfigDataMap.computeIfAbsent(data.getId(), v -> new HashMap<>()).put(data.getAffixId(), data);
                }
            }
        } catch (IOException e) {
            log.info("load error", e);
        }
    }

    public Map<Long ,EquipAffixExcelConfigData> getById(Long id){
        if(!equipAffixExcelConfigDataMap.containsKey(id)){
            return null;
        }
        return equipAffixExcelConfigDataMap.get(id);
    }

    public EquipAffixExcelConfigData getByIdAffixId(Long id, Long affixId){
        if(!equipAffixExcelConfigDataMap.containsKey(id) || !equipAffixExcelConfigDataMap.get(id).containsKey(affixId)){
            return null;
        }
        return equipAffixExcelConfigDataMap.get(id).get(affixId);
    }
}
