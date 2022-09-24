package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import space.xiami.project.genshindataviewer.domain.json.*;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xiami
 */
@Component
public class AvatarFactory extends AbstractFileBaseFactory{
    
    public static final Logger log = LoggerFactory.getLogger(AvatarFactory.class);

    public static final String avatarExcelConfigDataFile = "AvatarExcelConfigData.json";
    public static final String avatarLevelExcelConfigDataFile = "AvatarLevelExcelConfigData.json";
    public static final String avatarPromoteExcelConfigDataFile = "AvatarPromoteExcelConfigData.json";
    public static final String avatarSkillDepotExcelConfigDataFile = "AvatarSkillDepotExcelConfigData.json";
    public static final String avatarSkillExcelConfigDataFile = "AvatarSkillExcelConfigData.json";
    public static final String proudSkillExcelConfigDataFile = "ProudSkillExcelConfigData.json";
    public static final String avatarTalentExcelConfigDataFile = "AvatarTalentExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarLevelExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarPromoteExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarSkillDepotExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarSkillExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + proudSkillExcelConfigDataFile);
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarTalentExcelConfigDataFile);
    }

    /**
     * id -> DO
     */
    private final Map<Long, AvatarExcelConfigData> avatarExcelConfigDataMap = new HashMap<>();

    /**
     * level -> DO
     */
    private final Map<Integer, AvatarLevelExcelConfigData> avatarLevelExcelConfigDataMap = new HashMap<>();

    /**
     * avatarPromoteId -> promoteLevel -> DO
     */
    private final Map<Long, Map<Integer, AvatarPromoteExcelConfigData>> avatarPromoteExcelConfigDataMap = new HashMap<>();
    /**
     * avatarPromoteId -> level -> isPromote > DO
     */
    private final Map<Long, Map<Integer, Map<Boolean, AvatarPromoteExcelConfigData>>> avatarPromoteExcelConfigDataMapLevel = new HashMap<>();

    /**
     * skillDepotId -> DO
     */
    private final Map<Long, AvatarSkillDepotExcelConfigData> avatarSkillDepotExcelConfigDataMap = new HashMap<>();

    /**
     * skillId -> DO
     */
    private final Map<Long, AvatarSkillExcelConfigData> avatarSkillExcelConfigDataMap = new HashMap<>();

    /**
     * proudSkillGroupId -> level -> DO
     */
    private final Map<Long, Map<Integer, ProudSkillExcelConfigData>> proudSkillExcelConfigDataMapLevel = new HashMap<>();

    /**
     * talentId -> DO
     */
    private final Map<Long, AvatarTalentExcelConfigData> avatarTalentExcelConfigDataMap = new HashMap<>();

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
            if(path.endsWith(SPLASH + avatarExcelConfigDataFile)) {
                List<AvatarExcelConfigData> array = readJsonArray(path, AvatarExcelConfigData.class);
                for (AvatarExcelConfigData data : array) {
                    if(avatarExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same id={}", data.getId());
                        continue;
                    }
                    if(!data.getDescTextMapHash().equals(data.getInfoDescTextMapHash())){
                        log.warn("Different desc and info id={}", data.getId());
                    }
                    avatarExcelConfigDataMap.put(data.getId(), data);
                    if(nameTextMapHash2Id.containsKey(data.getNameTextMapHash())){
                        log.warn("Ignore same nameTextMapHash hash={}", data.getNameTextMapHash());
                        continue;
                    }
                    nameTextMapHash2Id.put(data.getNameTextMapHash(), data.getId());
                }
            }else if(path.endsWith(SPLASH + avatarLevelExcelConfigDataFile)){
                List<AvatarLevelExcelConfigData> array = readJsonArray(path, AvatarLevelExcelConfigData.class);
                for(AvatarLevelExcelConfigData data : array){
                    if(avatarLevelExcelConfigDataMap.containsKey(data.getLevel())){
                        log.warn("Ignore same avatarLevelExcelConfigData level={}", data.getLevel());
                        continue;
                    }
                    avatarLevelExcelConfigDataMap.put(data.getLevel(), data);
                }
            }else if(path.endsWith(SPLASH + avatarPromoteExcelConfigDataFile)){
                List<AvatarPromoteExcelConfigData> array = readJsonArray(path, AvatarPromoteExcelConfigData.class);
                for(AvatarPromoteExcelConfigData data : array){
                    Map<Integer, AvatarPromoteExcelConfigData> innerMap = avatarPromoteExcelConfigDataMap.computeIfAbsent(data.getAvatarPromoteId(), v -> new HashMap<>());
                    if(innerMap.containsKey(data.getPromoteLevel())){
                        log.warn("Ignore same AvatarPromoteExcelConfig promoteLevel={}", data.getPromoteLevel());
                        continue;
                    }
                    innerMap.put(data.getPromoteLevel() == null ? 0 : data.getPromoteLevel(), data);
                }
                avatarPromoteExcelConfigDataMap.forEach((avatarPromoteId, innerMap) -> {
                    List<Integer> sortedPromoteLevel = new ArrayList<>(innerMap.keySet());
                    sortedPromoteLevel.sort(Comparator.comparingInt(o -> o));
                    List<Integer> levelLimit = sortedPromoteLevel.stream()
                            .map(promoteLevel-> innerMap.get(promoteLevel).getUnlockMaxLevel())
                            .collect(Collectors.toList());
                    int currentLevel = 1;
                    for(int i = 0; i < sortedPromoteLevel.size(); i++){
                        int limit = levelLimit.get(i);
                        while(currentLevel <= limit){
                            avatarPromoteExcelConfigDataMapLevel
                                    .computeIfAbsent(avatarPromoteId, v->new HashMap<>())
                                    .computeIfAbsent(currentLevel, v->new HashMap<>())
                                    .put(false, innerMap.get(sortedPromoteLevel.get(i)));
                            if(currentLevel == limit && i+1<sortedPromoteLevel.size()){
                                avatarPromoteExcelConfigDataMapLevel
                                        .computeIfAbsent(avatarPromoteId, v->new HashMap<>())
                                        .computeIfAbsent(currentLevel, v->new HashMap<>())
                                        .put(true, innerMap.get(sortedPromoteLevel.get(i+1)));
                            }
                            currentLevel++;
                        }
                    }
                });
            }else if(path.endsWith(SPLASH + avatarSkillDepotExcelConfigDataFile)){
                List<AvatarSkillDepotExcelConfigData> array = readJsonArray(path, AvatarSkillDepotExcelConfigData.class);
                for(AvatarSkillDepotExcelConfigData data : array){
                    if(avatarSkillDepotExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same avatarSkillDepotExcelConfigData id={}", data.getId());
                        continue;
                    }
                    avatarSkillDepotExcelConfigDataMap.put(data.getId(), data);
                }
            }else if(path.endsWith(SPLASH + avatarSkillExcelConfigDataFile)){
                List<AvatarSkillExcelConfigData> array = readJsonArray(path, AvatarSkillExcelConfigData.class);
                for(AvatarSkillExcelConfigData data : array){
                    if(avatarSkillExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same avatarSkillExcelConfigData id={}", data.getId());
                        continue;
                    }
                    avatarSkillExcelConfigDataMap.put(data.getId(), data);
                }
            }else if(path.endsWith(SPLASH + proudSkillExcelConfigDataFile)){
                List<ProudSkillExcelConfigData> array = readJsonArray(path, ProudSkillExcelConfigData.class);
                for(ProudSkillExcelConfigData data : array){
                    Long proudSkillGroupId = data.getProudSkillGroupId();
                    Integer level = data.getLevel();
                    Map<Integer, ProudSkillExcelConfigData> innerMap = proudSkillExcelConfigDataMapLevel.computeIfAbsent(proudSkillGroupId, v -> new HashMap<>());
                    if(innerMap.containsKey(level)){
                        log.warn("Ignore same ProudSkillExcelConfigData level={}", level);
                        continue;
                    }
                    innerMap.put(level, data);
                }
            }else if(path.endsWith(SPLASH + avatarTalentExcelConfigDataFile)){
                List<AvatarTalentExcelConfigData> array = readJsonArray(path, AvatarTalentExcelConfigData.class);
                for(AvatarTalentExcelConfigData data : array){
                    if(avatarTalentExcelConfigDataMap.containsKey(data.getTalentId())){
                        log.warn("Ignore same AvatarTalentExcelConfigData talentId={}", data.getTalentId());
                        continue;
                    }
                    avatarTalentExcelConfigDataMap.put(data.getTalentId(), data);
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }
    
    @Override
    protected void clear(String path) {
        if(path.endsWith(SPLASH + avatarExcelConfigDataFile)) {
            avatarExcelConfigDataMap.clear();
            nameTextMapHash2Id.clear();
        }else if(path.endsWith(SPLASH + avatarLevelExcelConfigDataFile)){
            avatarLevelExcelConfigDataMap.clear();
        }else if(path.endsWith(SPLASH + avatarPromoteExcelConfigDataFile)){
            avatarPromoteExcelConfigDataMap.clear();
            avatarPromoteExcelConfigDataMapLevel.clear();
        }else if(path.endsWith(SPLASH + avatarSkillDepotExcelConfigDataFile)){
            avatarSkillDepotExcelConfigDataMap.clear();
        }else if(path.endsWith(SPLASH + avatarSkillExcelConfigDataFile)){
            avatarSkillExcelConfigDataMap.clear();
        }else if(path.endsWith(SPLASH + proudSkillExcelConfigDataFile)){
            proudSkillExcelConfigDataMapLevel.clear();
        }else if(path.endsWith(SPLASH + avatarTalentExcelConfigDataFile)){
            avatarTalentExcelConfigDataMap.clear();
        }
    }


    public AvatarExcelConfigData getAvatar(Long id){
        return avatarExcelConfigDataMap.get(id);
    }

    public AvatarLevelExcelConfigData getAvatarLevel(Integer level){
        return avatarLevelExcelConfigDataMap.get(level);
    }

    public List<Integer> getSortedLevel(){
        ArrayList<Integer> levelList = new ArrayList<>(avatarLevelExcelConfigDataMap.keySet());
        levelList.sort(Comparator.comparingInt(o -> o));
        return levelList;
    }

    public Map<Integer ,AvatarPromoteExcelConfigData> getAvatarPromoteMap(Long avatarPromoteId){
        return avatarPromoteExcelConfigDataMap.get(avatarPromoteId);
    }

    public AvatarPromoteExcelConfigData getAvatarPromote(Long avatarPromoteId, Integer promoteLevel){
        if(!avatarPromoteExcelConfigDataMap.containsKey(avatarPromoteId)){
            return null;
        }
        return avatarPromoteExcelConfigDataMap.get(avatarPromoteId).get(promoteLevel);
    }

    public Map<Boolean, AvatarPromoteExcelConfigData> getAvatarPromoteByLevel(Long avatarPromoteId, Integer level){
        Map<Integer, Map<Boolean, AvatarPromoteExcelConfigData>> innerMap = avatarPromoteExcelConfigDataMapLevel.get(avatarPromoteId);
        if(innerMap == null){
            return null;
        }
        return innerMap.get(level);
    }

    public AvatarSkillDepotExcelConfigData getAvatarSkillDepot(Long id){
        return avatarSkillDepotExcelConfigDataMap.get(id);
    }

    public AvatarSkillExcelConfigData getAvatarSkill(Long id){
        return avatarSkillExcelConfigDataMap.get(id);
    }

    public Map<Integer, ProudSkillExcelConfigData> getProudSkillLevelMap(Long proudSkillGroupId){
        return proudSkillExcelConfigDataMapLevel.get(proudSkillGroupId);
    }

    public ProudSkillExcelConfigData getProudSkillByLevel(Long proudSkillGroupId, Integer level){
        Map<Integer, ProudSkillExcelConfigData> innerMap = proudSkillExcelConfigDataMapLevel.get(proudSkillGroupId);
        if(innerMap == null){
            return null;
        }
        return innerMap.get(level);
    }

    public AvatarTalentExcelConfigData getAvatarTalent(Long talentId){
        return avatarTalentExcelConfigDataMap.get(talentId);
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
        return getName2Ids(language).get(name);
    }
}
