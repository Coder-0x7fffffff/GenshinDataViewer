package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import space.xiami.project.genshindataviewer.service.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xiami
 */
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

    /**
     * weaponId -> DO
     */
    private final Map<Long, WeaponCodexExcelConfigData> weaponCodexExcelConfigDataMap = new HashMap<>();

    /**
     * Id(weaponId) -> DO
     */
    private final Map<Long, WeaponExcelConfigData> weaponExcelConfigDataMap = new HashMap<>();

    /**
     * level -> DO
     */
    private final Map<Integer, WeaponLevelExcelConfigData> weaponLevelExcelConfigDataMap = new HashMap<>();

    /**
     * weaponPromoteId -> promoteLevel -> DO
     */
    private final Map<Long, Map<Integer, WeaponPromoteExcelConfigData>> weaponPromoteExcelConfigDataMap = new HashMap<>();
    /**
     * weaponPromoteId -> level -> isPromote > DO
     */
    private final Map<Long, Map<Integer, Map<Boolean, WeaponPromoteExcelConfigData>>> weaponPromoteExcelConfigDataMapLevel = new HashMap<>();

    /**
     * nameTextHash -> Id
     */
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
            if(path.endsWith(SPLASH + weaponCodexExcelConfigDataFile)){
                List<WeaponCodexExcelConfigData> array = readJsonArray(path, WeaponCodexExcelConfigData.class);
                for(WeaponCodexExcelConfigData data : array){
                    if(weaponCodexExcelConfigDataMap.containsKey(data.getWeaponId())){
                        log.warn("Ignore same weaponCodexExcelConfigData weaponId={}", data.getWeaponId());
                        continue;
                    }
                    weaponCodexExcelConfigDataMap.put(data.getWeaponId(), data);
                }
            }else if(path.endsWith(SPLASH + weaponExcelConfigDataFile)){
                List<WeaponExcelConfigData> array = readJsonArray(path, WeaponExcelConfigData.class);
                for(WeaponExcelConfigData data : array){
                    if(weaponExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same weaponExcelConfigData id={}", data.getId());
                        continue;
                    }
                    weaponExcelConfigDataMap.put(data.getId(), data);
                    if(nameTextMapHash2Id.containsKey(data.getNameTextMapHash())){
                        log.warn("Ignore same nameTextMapHash hash={}", data.getNameTextMapHash());
                        continue;
                    }
                    nameTextMapHash2Id.put(data.getNameTextMapHash(), data.getId());
                }
            }else if(path.endsWith(SPLASH + weaponLevelExcelConfigDataFile)){
                List<WeaponLevelExcelConfigData> array = readJsonArray(path, WeaponLevelExcelConfigData.class);
                for(WeaponLevelExcelConfigData data : array){
                    if(weaponLevelExcelConfigDataMap.containsKey(data.getLevel())){
                        log.warn("Ignore same weaponLevelExcelConfigData level={}", data.getLevel());
                        continue;
                    }
                    weaponLevelExcelConfigDataMap.put(data.getLevel(), data);
                }
            }else if(path.endsWith(SPLASH + weaponPromoteExcelConfigDataFile)){
                List<WeaponPromoteExcelConfigData> array = readJsonArray(path, WeaponPromoteExcelConfigData.class);
                for(WeaponPromoteExcelConfigData data : array){
                    Map<Integer, WeaponPromoteExcelConfigData> innerMap = weaponPromoteExcelConfigDataMap.computeIfAbsent(data.getWeaponPromoteId(), v -> new HashMap<>());
                    if(innerMap.containsKey(data.getPromoteLevel())){
                        log.warn("Ignore same WeaponPromoteExcelConfig promoteLevel={}", data.getPromoteLevel());
                        continue;
                    }
                    innerMap.put(data.getPromoteLevel() == null ? 0 : data.getPromoteLevel(), data);
                }
                weaponPromoteExcelConfigDataMap.forEach((weaponPromoteId, innerMap) -> {
                    List<Integer> sortedPromoteLevel = new ArrayList<>(innerMap.keySet());
                    sortedPromoteLevel.sort(Comparator.comparingInt(o -> o));
                    List<Integer> levelLimit = sortedPromoteLevel.stream()
                            .map(promoteLevel-> innerMap.get(promoteLevel).getUnlockMaxLevel())
                            .collect(Collectors.toList());
                    int currentLevel = 1;
                    for(int i = 0; i < sortedPromoteLevel.size(); i++){
                        int limit = levelLimit.get(i);
                        while(currentLevel <= limit){
                            weaponPromoteExcelConfigDataMapLevel
                                    .computeIfAbsent(weaponPromoteId, v->new HashMap<>())
                                    .computeIfAbsent(currentLevel, v->new HashMap<>())
                                    .put(false, innerMap.get(sortedPromoteLevel.get(i)));
                            if(currentLevel == limit && i+1<sortedPromoteLevel.size()){
                                weaponPromoteExcelConfigDataMapLevel
                                        .computeIfAbsent(weaponPromoteId, v->new HashMap<>())
                                        .computeIfAbsent(currentLevel, v->new HashMap<>())
                                        .put(true, innerMap.get(sortedPromoteLevel.get(i+1)));
                            }
                            currentLevel++;
                        }
                    }
                });
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        if(path.endsWith(SPLASH + weaponCodexExcelConfigDataFile)){
            weaponCodexExcelConfigDataMap.clear();
        }else if(path.endsWith(SPLASH + weaponExcelConfigDataFile)){
            weaponExcelConfigDataMap.clear();
            nameTextMapHash2Id.clear();
        }else if(path.endsWith(SPLASH + weaponLevelExcelConfigDataFile)){
            weaponLevelExcelConfigDataMap.clear();
        }else if(path.endsWith(SPLASH + weaponPromoteExcelConfigDataFile)){
            weaponPromoteExcelConfigDataMap.clear();
            weaponPromoteExcelConfigDataMapLevel.clear();
        }
    }

    public WeaponCodexExcelConfigData getWeaponCodex(Long weaponId){
        return weaponCodexExcelConfigDataMap.get(weaponId);
    }

    public WeaponExcelConfigData getWeapon(Long id){
        return weaponExcelConfigDataMap.get(id);
    }

    public WeaponLevelExcelConfigData getWeaponLevel(Integer level){
        return weaponLevelExcelConfigDataMap.get(level);
    }

    public List<Integer> getSortedLevel(){
        ArrayList<Integer> levelList = new ArrayList<>(weaponLevelExcelConfigDataMap.keySet());
        levelList.sort(Comparator.comparingInt(o -> o));
        return levelList;
    }

    public Map<Integer ,WeaponPromoteExcelConfigData> getWeaponPromoteMap(Long weaponPromoteId){
        return weaponPromoteExcelConfigDataMap.get(weaponPromoteId);
    }

    public WeaponPromoteExcelConfigData getWeaponPromote(Long weaponPromoteId, Integer promoteLevel){
        if(!weaponPromoteExcelConfigDataMap.containsKey(weaponPromoteId)){
            return null;
        }
        return weaponPromoteExcelConfigDataMap.get(weaponPromoteId).get(promoteLevel);
    }

    public Map<Boolean, WeaponPromoteExcelConfigData> getWeaponPromoteByLevel(Long weaponPromoteId, Integer level){
        Map<Integer, Map<Boolean, WeaponPromoteExcelConfigData>> innerMap = weaponPromoteExcelConfigDataMapLevel.get(weaponPromoteId);
        if(innerMap == null){
            return null;
        }
        return innerMap.get(level);
    }

    @Cacheable(cacheNames = "WeaponFactory_name2Ids", unless = "#result.size() == 0")
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
