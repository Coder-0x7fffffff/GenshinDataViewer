package space.xiami.project.genshindataviewer.client.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Component
public class WeaponFactory extends AbstractFileBaseFactory {

    public static final Logger log = LoggerFactory.getLogger(WeaponFactory.class);

    public static final String weaponCodexExcelConfigDataFile = "WeaponCodexExcelConfigData.json";
    public static final String weaponCurveExcelConfigDataFile = "WeaponCurveExcelConfigData.json";
    public static final String weaponExcelConfigDataFile = "WeaponExcelConfigData.json";
    public static final String weaponLevelExcelConfigDataFile = "WeaponLevelExcelConfigData.json";
    public static final String weaponPromoteExcelConfigDataFile = "WeaponPromoteExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + weaponCodexExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + weaponCurveExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + weaponExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + weaponLevelExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + weaponPromoteExcelConfigDataFile);
    }

    // weaponId -> DO
    private final Map<Long, WeaponCodexExcelConfigData> weaponCodexExcelConfigDataMap = new HashMap<>();
    // level -> DO
    private final Map<Integer, WeaponCurveExcelConfigData> weaponCurveExcelConfigDataMap = new HashMap<>();
    // Id(weaponId) -> DO
    private final Map<Long, WeaponExcelConfigData> weaponExcelConfigDataMap = new HashMap<>();
    // level -> DO
    private final Map<Integer, WeaponLevelExcelConfigData> weaponLevelExcelConfigDataMap = new HashMap<>();
    // weaponPromoteId -> promoteLevel -> DO
    private final Map<Long, Map<Integer, WeaponPromoteExcelConfigData>> weaponPromoteExcelConfigDataMap = new HashMap<>();

    @Resource
    private EquipAffixFactory equipAffixFactory;

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    protected void load(String path) {
        try{
            if(path.endsWith("/"+ weaponCodexExcelConfigDataFile)){
                List<WeaponCodexExcelConfigData> array = readJsonArray(path, WeaponCodexExcelConfigData.class);
                for(WeaponCodexExcelConfigData data : array){
                    weaponCodexExcelConfigDataMap.put(data.getWeaponId(), data);
                }
            }else if(path.endsWith("/"+ weaponCurveExcelConfigDataFile)){
                List<WeaponCurveExcelConfigData> array = readJsonArray(path, WeaponCurveExcelConfigData.class);
                for(WeaponCurveExcelConfigData data : array){
                    weaponCurveExcelConfigDataMap.put(data.getLevel(), data);
                }
            }else if(path.endsWith("/"+ weaponExcelConfigDataFile)){
                List<WeaponExcelConfigData> array = readJsonArray(path, WeaponExcelConfigData.class);
                for(WeaponExcelConfigData data : array){
                    weaponExcelConfigDataMap.put(data.getId(), data);
                }
            }else if(path.endsWith("/"+ weaponLevelExcelConfigDataFile)){
                List<WeaponLevelExcelConfigData> array = readJsonArray(path, WeaponLevelExcelConfigData.class);
                for(WeaponLevelExcelConfigData data : array){
                    weaponLevelExcelConfigDataMap.put(data.getLevel(), data);
                }
            }else if(path.endsWith("/"+ weaponPromoteExcelConfigDataFile)){
                List<WeaponPromoteExcelConfigData> array = readJsonArray(path, WeaponPromoteExcelConfigData.class);
                for(WeaponPromoteExcelConfigData data : array){
                    weaponPromoteExcelConfigDataMap.computeIfAbsent(data.getWeaponPromoteId(), v -> new HashMap<>()).put(data.getPromoteLevel(), data);
                }
            }
        } catch (IOException e) {
            log.info("load error", e);
        }
    }

    public WeaponCodexExcelConfigData getWeaponCodex(Long weaponId){
        if(!weaponCodexExcelConfigDataMap.containsKey(weaponId)){
            return null;
        }
        return weaponCodexExcelConfigDataMap.get(weaponId);
    }

    public WeaponCurveExcelConfigData getWeaponCurve(Integer level){
        if(!weaponCurveExcelConfigDataMap.containsKey(level)){
            return null;
        }
        return weaponCurveExcelConfigDataMap.get(level);
    }

    public WeaponExcelConfigData getWeapon(Long id){
        if(!weaponExcelConfigDataMap.containsKey(id)){
            return null;
        }
        return weaponExcelConfigDataMap.get(id);
    }

    public WeaponLevelExcelConfigData getWeaponLevel(Integer level){
        if(!weaponLevelExcelConfigDataMap.containsKey(level)){
            return null;
        }
        return weaponLevelExcelConfigDataMap.get(level);
    }


    public Map<Integer ,WeaponPromoteExcelConfigData> getEquipAffixMap(Long weaponPromoteId){
        if(!weaponPromoteExcelConfigDataMap.containsKey(weaponPromoteId)){
            return null;
        }
        return weaponPromoteExcelConfigDataMap.get(weaponPromoteId);
    }

    public WeaponPromoteExcelConfigData getEquipAffix(Long weaponPromoteId, Integer promoteLevel){
        if(!weaponPromoteExcelConfigDataMap.containsKey(weaponPromoteId) || !weaponPromoteExcelConfigDataMap.get(weaponPromoteId).containsKey(promoteLevel)){
            return null;
        }
        return weaponPromoteExcelConfigDataMap.get(weaponPromoteId).get(promoteLevel);
    }
}
