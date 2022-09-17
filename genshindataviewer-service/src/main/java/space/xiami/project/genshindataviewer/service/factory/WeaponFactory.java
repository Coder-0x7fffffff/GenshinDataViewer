package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import space.xiami.project.genshindataviewer.service.util.PathUtil;
import space.xiami.project.genshindataviewer.common.enums.LanguageEnum;
import space.xiami.project.genshindataviewer.domain.json.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    // level -> type -> DO
    private final Map<Integer, Map<String, WeaponCurveExcelConfigData.CurveInfo>> weaponCurveInfoMap = new HashMap<>();

    // Id(weaponId) -> DO
    private final Map<Long, WeaponExcelConfigData> weaponExcelConfigDataMap = new HashMap<>();

    // level -> DO
    private final Map<Integer, WeaponLevelExcelConfigData> weaponLevelExcelConfigDataMap = new HashMap<>();

    // weaponPromoteId -> promoteLevel -> DO
    private final Map<Long, Map<Integer, WeaponPromoteExcelConfigData>> weaponPromoteExcelConfigDataMap = new HashMap<>();
    // weaponPromoteId -> level -> isPromote > DO
    private final Map<Long, Map<Integer, Map<Boolean, WeaponPromoteExcelConfigData>>> weaponPromoteExcelConfigDataMapLevel = new HashMap<>();

    // Lang -> name -> Id
    private final Map<Byte, Map<String, Long>> langNameId = new HashMap<>();

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        try{
            if(path.endsWith("/"+ weaponCodexExcelConfigDataFile)){
                List<WeaponCodexExcelConfigData> array = readJsonArray(path, WeaponCodexExcelConfigData.class);
                for(WeaponCodexExcelConfigData data : array){
                    if(weaponCodexExcelConfigDataMap.containsKey(data.getWeaponId())){
                        log.warn("Ignore same weaponCodexExcelConfigData weaponId={}", data.getWeaponId());
                        continue;
                    }
                    weaponCodexExcelConfigDataMap.put(data.getWeaponId(), data);
                }
            }else if(path.endsWith("/"+ weaponCurveExcelConfigDataFile)){
                List<WeaponCurveExcelConfigData> array = readJsonArray(path, WeaponCurveExcelConfigData.class);
                for(WeaponCurveExcelConfigData data : array){
                    if(weaponCurveExcelConfigDataMap.containsKey(data.getLevel())){
                        log.warn("Ignore same weaponCurveExcelConfigData level={}", data.getLevel());
                        continue;
                    }
                    weaponCurveExcelConfigDataMap.put(data.getLevel(), data);
                    //curveInfo
                    Map<String, WeaponCurveExcelConfigData.CurveInfo> innerMap = weaponCurveInfoMap.computeIfAbsent(data.getLevel(), v -> new HashMap<>());
                    List<WeaponCurveExcelConfigData.CurveInfo> curveInfos = data.getCurveInfos();
                    curveInfos.forEach(curveInfo -> {
                        if(innerMap.containsKey(curveInfo.getType())){
                            log.warn("Ignore same curveInfo type={}", curveInfo.getType());
                            return;
                        }
                        innerMap.put(curveInfo.getType(), curveInfo);
                    });
                }
            }else if(path.endsWith("/"+ weaponExcelConfigDataFile)){
                List<WeaponExcelConfigData> array = readJsonArray(path, WeaponExcelConfigData.class);
                for(WeaponExcelConfigData data : array){
                    if(weaponExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same weaponExcelConfigData id={}", data.getId());
                        continue;
                    }
                    weaponExcelConfigDataMap.put(data.getId(), data);
                    for(LanguageEnum language : LanguageEnum.values()){
                        String name = textMapFactory.getText(language.getCode(), data.getNameTextMapHash());
                        if(StringUtils.hasLength(name)){
                            langNameId.computeIfAbsent(language.getCode(), v -> new HashMap<>()).put(name, data.getId());
                        }else{
                            log.warn("Weapon id={} with nameHash={} has no string name of language={}", data.getId(), data.getNameTextMapHash(), language.getDesc());
                            langNameId.computeIfAbsent(language.getCode(), v -> new HashMap<>()).put(String.valueOf(data.getNameTextMapHash()), data.getId());
                        }
                    }
                }
            }else if(path.endsWith("/"+ weaponLevelExcelConfigDataFile)){
                List<WeaponLevelExcelConfigData> array = readJsonArray(path, WeaponLevelExcelConfigData.class);
                for(WeaponLevelExcelConfigData data : array){
                    if(weaponLevelExcelConfigDataMap.containsKey(data.getLevel())){
                        log.warn("Ignore same weaponLevelExcelConfigData level={}", data.getLevel());
                        continue;
                    }
                    weaponLevelExcelConfigDataMap.put(data.getLevel(), data);
                }
            }else if(path.endsWith("/"+ weaponPromoteExcelConfigDataFile)){
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
        if(path.endsWith("/"+ weaponCodexExcelConfigDataFile)){
            weaponCodexExcelConfigDataMap.clear();
        }else if(path.endsWith("/"+ weaponCurveExcelConfigDataFile)){
            weaponCurveExcelConfigDataMap.clear();
            weaponCurveInfoMap.clear();
        }else if(path.endsWith("/"+ weaponExcelConfigDataFile)){
            weaponExcelConfigDataMap.clear();
            langNameId.clear();
        }else if(path.endsWith("/"+ weaponLevelExcelConfigDataFile)){
            weaponLevelExcelConfigDataMap.clear();
        }else if(path.endsWith("/"+ weaponPromoteExcelConfigDataFile)){
            weaponPromoteExcelConfigDataMap.clear();
            weaponPromoteExcelConfigDataMapLevel.clear();
        }
    }

    public WeaponCodexExcelConfigData getWeaponCodex(Long weaponId){
        if(!weaponCodexExcelConfigDataMap.containsKey(weaponId)){
            return null;
        }
        return weaponCodexExcelConfigDataMap.get(weaponId);
    }

    public Map<String, WeaponCurveExcelConfigData.CurveInfo> getWeaponCurveInfoMap(Integer level){
        if(!weaponCurveInfoMap.containsKey(level)){
            return null;
        }
        return weaponCurveInfoMap.get(level);
    }

    public WeaponCurveExcelConfigData.CurveInfo getWeaponCurveInfo(Integer level, String type){
        Map<String, WeaponCurveExcelConfigData.CurveInfo> curveMap = getWeaponCurveInfoMap(level);
        if(curveMap == null){
            return null;
        }
        return curveMap.get(type);
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

    public List<Integer> getSortedLevel(){
        ArrayList<Integer> levelList = new ArrayList<>(weaponLevelExcelConfigDataMap.keySet());
        levelList.sort(Comparator.comparingInt(o -> o));
        return levelList;
    }

    public Map<Integer ,WeaponPromoteExcelConfigData> getWeaponPromoteMap(Long weaponPromoteId){
        if(!weaponPromoteExcelConfigDataMap.containsKey(weaponPromoteId)){
            return null;
        }
        return weaponPromoteExcelConfigDataMap.get(weaponPromoteId);
    }

    public WeaponPromoteExcelConfigData getWeaponPromote(Long weaponPromoteId, Integer promoteLevel){
        if(!weaponPromoteExcelConfigDataMap.containsKey(weaponPromoteId) || !weaponPromoteExcelConfigDataMap.get(weaponPromoteId).containsKey(promoteLevel)){
            return null;
        }
        return weaponPromoteExcelConfigDataMap.get(weaponPromoteId).get(promoteLevel);
    }

    public Map<Boolean, WeaponPromoteExcelConfigData> getWeaponPromoteByLevel(Long weaponPromoteId, Integer level){
        Map<Integer, Map<Boolean, WeaponPromoteExcelConfigData>> innerMap = weaponPromoteExcelConfigDataMapLevel.get(weaponPromoteId);
        if(innerMap != null && innerMap.containsKey(level)){
            return innerMap.get(level);
        }
        return null;
    }

    public Map<String, Long> getName2Ids(Byte language){
        if(!langNameId.containsKey(language)){
            return null;
        }
        return langNameId.get(language);
    }

    public Long getIdByName(Byte language, String name){
        if(langNameId.containsKey(language) && langNameId.get(language).containsKey(name)){
            return langNameId.get(language).get(name);
        }
        return null;
    }
}
