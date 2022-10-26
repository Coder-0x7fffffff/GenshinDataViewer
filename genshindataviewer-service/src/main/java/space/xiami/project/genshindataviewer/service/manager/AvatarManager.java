package space.xiami.project.genshindataviewer.service.manager;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import space.xiami.project.genshincommon.enums.CurveEnum;
import space.xiami.project.genshincommon.enums.ElementalTypeEnum;
import space.xiami.project.genshincommon.enums.LanguageEnum;
import space.xiami.project.genshindataviewer.domain.json.*;
import space.xiami.project.genshindataviewer.domain.model.*;
import space.xiami.project.genshindataviewer.service.factory.AvatarFactory;
import space.xiami.project.genshindataviewer.service.factory.CurveFactory;
import space.xiami.project.genshindataviewer.service.factory.TeamResonanceFactory;
import space.xiami.project.genshindataviewer.service.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.service.factory.single.ManualTextMapFactory;
import space.xiami.project.genshindataviewer.service.factory.single.MaterialFactory;
import space.xiami.project.genshindataviewer.service.factory.single.RewardFactory;
import space.xiami.project.genshindataviewer.service.util.AvatarUtil;
import space.xiami.project.genshindataviewer.service.util.CurveUtil;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xiami
 */
@Component
public class AvatarManager {

    public static final Logger log = LoggerFactory.getLogger(AvatarManager.class);

    private static final Map<String, Byte> elementString2Enum = new HashMap<>();

    // 元素对应可能的效果
    private Map<Byte, List<Long>> element2teamResonance = null;

    @Resource
    private AvatarFactory avatarFactory;

    @Resource
    private TextMapFactory textMapFactory;

    @Resource
    private ManualTextMapFactory manualTextMapFactory;

    @Resource
    private MaterialFactory materialFactory;

    @Resource
    private RewardFactory rewardFactory;

    @Resource
    private WeaponManager weaponManager;

    @Resource
    private CurveFactory curveFactory;

    @Resource
    private TeamResonanceFactory teamResonanceFactory;

    static {
        elementString2Enum.put("Fire", ElementalTypeEnum.PYRO.getCode());
        elementString2Enum.put("Water", ElementalTypeEnum.HYDRO.getCode());
        elementString2Enum.put("Grass", ElementalTypeEnum.DENDRO.getCode());
        elementString2Enum.put("Electric", ElementalTypeEnum.ELECTRO.getCode());
        elementString2Enum.put("Wind", ElementalTypeEnum.ANEMO.getCode());
        elementString2Enum.put("Ice", ElementalTypeEnum.CRYO.getCode());
        elementString2Enum.put("Rock", ElementalTypeEnum.GEO.getCode());
    }

    private void initElementToTeamResonance(){
        element2teamResonance = new HashMap<>();
        teamResonanceFactory.getTeamResonanceList().forEach(excelConfigData -> {
            Long id = excelConfigData.getTeamResonanceId();
            if(excelConfigData.getCond() != null){
                element2teamResonance.computeIfAbsent((byte) -1, v -> new ArrayList<>()).add(id);
            }
            if(excelConfigData.getFireAvatarCount() != null){
                element2teamResonance.computeIfAbsent(ElementalTypeEnum.PYRO.getCode(), v -> new ArrayList<>()).add(id);
            }
            if(excelConfigData.getWaterAvatarCount() != null){
                element2teamResonance.computeIfAbsent(ElementalTypeEnum.HYDRO.getCode(), v -> new ArrayList<>()).add(id);
            }
            if(excelConfigData.getGrassAvatarCount() != null){
                element2teamResonance.computeIfAbsent(ElementalTypeEnum.DENDRO.getCode(), v -> new ArrayList<>()).add(id);
            }
            if(excelConfigData.getElectricAvatarCount() != null){
                element2teamResonance.computeIfAbsent(ElementalTypeEnum.ELECTRO.getCode(), v -> new ArrayList<>()).add(id);
            }
            if(excelConfigData.getWindAvatarCount() != null){
                element2teamResonance.computeIfAbsent(ElementalTypeEnum.ANEMO.getCode(), v -> new ArrayList<>()).add(id);
            }
            if(excelConfigData.getIceAvatarCount() != null){
                element2teamResonance.computeIfAbsent(ElementalTypeEnum.CRYO.getCode(), v -> new ArrayList<>()).add(id);
            }
            if(excelConfigData.getRockAvatarCount() != null){
                element2teamResonance.computeIfAbsent(ElementalTypeEnum.GEO.getCode(), v -> new ArrayList<>()).add(id);
            }
        });
    }

    public Map<String, Long> getAvatarIds(Byte language){
        return avatarFactory.getName2Ids(language);
    }

    public Long getAvatarId(Byte lang, String name){
        return avatarFactory.getIdByName(lang, name);
    }

    public Avatar getAvatar(Byte lang, String name, Byte elementalType){
        return getAvatar(lang, getAvatarId(lang, name), elementalType);
    }

    public Avatar getAvatar(Byte lang, Long id, Byte elementalType){
        if(lang == null || id == null){
            return null;
        }
        AvatarExcelConfigData excelConfigData = avatarFactory.getAvatar(id);
        if(excelConfigData == null){
            return null;
        }
        return convert(lang, excelConfigData, elementalType);
    }

    public TeamResonance getTeamResonance(Byte lang, Long id) {
        if(lang == null || id == null){
            return null;
        }
        TeamResonanceExcelConfigData excelConfigData = teamResonanceFactory.getByTeamResonanceId(id);
        if(excelConfigData == null){
            return null;
        }
        return convertTeamResonance(lang, excelConfigData);
    }

    /**
     * @see ElementalTypeEnum
     * @param lang 语言
     * @param ids id列表
     * @param appointElementalTypes 指定的元素类型 null 表示不指定
     * @return 效果组
     */
    public TeamResonanceGroup getTeamResonanceGroup(Byte lang, List<Long> ids, List<Byte> appointElementalTypes){
        if(CollectionUtils.isEmpty(ids) || CollectionUtils.isEmpty(appointElementalTypes) || ids.size() != appointElementalTypes.size()){
            return null;
        }
        Map<Byte, Integer> elementAvatarCount = new HashMap<>();
        for(int idx=0; idx<ids.size();idx++){
            Byte elementalType = appointElementalTypes.get(idx);
            if(elementalType == null){
                // skillDepot -> activeSkill -> costElemType
                AvatarExcelConfigData excelConfigData = avatarFactory.getAvatar(ids.get(idx));
                Set<Long> skillDepotIdSet = new ListOrderedSet<>();
                skillDepotIdSet.add(excelConfigData.getSkillDepotId());
                skillDepotIdSet.addAll(excelConfigData.getCandSkillDepotIds());
                skillDepotIdSet.forEach(skillDepotId -> {
                    AvatarSkillDepotExcelConfigData skillDepot = avatarFactory.getAvatarSkillDepot(skillDepotId);
                    List<Long> activeSkillIds = new ArrayList<>(skillDepot.getSkills());
                    activeSkillIds.add(skillDepot.getEnergySkill());
                    activeSkillIds.forEach(skillId -> {
                        AvatarSkillExcelConfigData skill = avatarFactory.getAvatarSkill(skillId);
                        if(skill != null && skill.getCostElemType() != null){
                            Byte elemCode = elementString2Enum.get(skill.getCostElemType());
                            elementAvatarCount.put(elemCode, elementAvatarCount.getOrDefault(elemCode, 0) + 1);
                        }
                    });
                });
            }else{
                elementAvatarCount.put(elementalType, elementAvatarCount.getOrDefault(elementalType, 0) + 1);
            }
        }
        if(element2teamResonance == null){
            initElementToTeamResonance();
        }
        List<TeamResonanceExcelConfigData> trs = teamResonanceFactory.getTeamResonanceList();
        TeamResonanceGroup teamResonanceGroup = new TeamResonanceGroup();
        teamResonanceGroup.setTeamResonances(new ArrayList<>());
        trs.forEach(tr -> {
            TeamResonance teamResonance = getTeamResonance(lang, tr.getTeamResonanceId());
            if(isMatchTeamResonance(teamResonance, elementAvatarCount)){
                teamResonanceGroup.getTeamResonances().add(teamResonance);
            }
        });
        return teamResonanceGroup;
    }

    private boolean isMatchTeamResonance(TeamResonance teamResonance, Map<Byte, Integer> elemAvatarCount){
        if(teamResonance.getAllDifferent() != null && teamResonance.getAllDifferent()){
            for(Map.Entry<Byte, Integer> entry : elemAvatarCount.entrySet()){
                if(entry.getValue() > 1){
                    return false;
                }
            }
        }
        if(teamResonance.getElementAvatarLimit() != null){
            for(Map.Entry<Byte, Integer> limit : teamResonance.getElementAvatarLimit().entrySet()){
                if(!elemAvatarCount.containsKey(limit.getKey()) || elemAvatarCount.get(limit.getKey()) < limit.getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    private TeamResonance convertTeamResonance(Byte lang, TeamResonanceExcelConfigData from){
        TeamResonance to = new TeamResonance();
        to.setTeamResonanceId(from.getTeamResonanceId());
        to.setTeamResonanceGroupId(from.getTeamResonanceGroupId());
        to.setLevel(from.getLevel());
        if(from.getCond() != null){
            to.setAllDifferent(true);
        }else{
            Map<Byte, Integer> limit = new HashMap<>();
            if(from.getFireAvatarCount() != null){
                limit.put(ElementalTypeEnum.PYRO.getCode(), from.getFireAvatarCount());
            }
            if(from.getWaterAvatarCount() != null){
                limit.put(ElementalTypeEnum.HYDRO.getCode(), from.getWaterAvatarCount());
            }
            if(from.getGrassAvatarCount() != null){
                limit.put(ElementalTypeEnum.DENDRO.getCode(), from.getGrassAvatarCount());
            }
            if(from.getElectricAvatarCount() != null){
                limit.put(ElementalTypeEnum.ELECTRO.getCode(), from.getElectricAvatarCount());
            }
            if(from.getWindAvatarCount() != null){
                limit.put(ElementalTypeEnum.ANEMO.getCode(), from.getWindAvatarCount());
            }
            if(from.getIceAvatarCount() != null){
                limit.put(ElementalTypeEnum.CRYO.getCode(), from.getIceAvatarCount());
            }
            if(from.getRockAvatarCount() != null){
                limit.put(ElementalTypeEnum.GEO.getCode(), from.getRockAvatarCount());
            }
            to.setElementAvatarLimit(limit);
        }
        to.setName(textMapFactory.getText(lang, from.getNameTextMapHash()));
        to.setDesc(textMapFactory.getText(lang, from.getDescTextMapHash()));
        to.setOpenConfig(from.getOpenConfig());
        to.setAddProperties(from.getAddProps().stream().map(addProp -> {
            AddProperty addProperty = new AddProperty();
            addProperty.setPropType(manualTextMapFactory.getText(lang, addProp.getPropType()));
            addProperty.setValue(addProp.getValue());
            return addProperty;
        }).collect(Collectors.toList()));
        to.setParamList(from.getParamList());
        return to;
    }

    private Avatar convert(Byte lang, AvatarExcelConfigData excelConfigData, Byte elementalType){
        Avatar avatar = new Avatar();
        avatar.setId(excelConfigData.getId());
        avatar.setName(textMapFactory.getText(lang, excelConfigData.getNameTextMapHash()));
        avatar.setDesc(textMapFactory.getText(lang, excelConfigData.getDescTextMapHash()));
        avatar.setWeaponType(manualTextMapFactory.getText(lang, excelConfigData.getWeaponType()));
        avatar.setChargeEfficiency(excelConfigData.getChargeEfficiency());
        avatar.setHpBase(excelConfigData.getHpBase());
        avatar.setAttackBase(excelConfigData.getAttackBase());
        avatar.setDefenseBase(excelConfigData.getDefenseBase());
        avatar.setCritical(excelConfigData.getCritical());
        avatar.setCriticalHurt(excelConfigData.getCriticalHurt());
        //set avatarProperties
        Map<String, List<LevelProperty.Property>> levelStr2PropertyMap = new HashMap<>();
        List<Integer> levels = avatarFactory.getSortedLevel();
        List<AvatarExcelConfigData.PropGrowCurve> propGrowCurves = excelConfigData.getPropGrowCurves();
        levels.forEach(level -> {
            Map<Boolean, AvatarPromoteExcelConfigData> promoteByLevel = avatarFactory.getAvatarPromoteByLevel(excelConfigData.getAvatarPromoteId(), level);
            if(promoteByLevel == null){
                return;
            }
            promoteByLevel.forEach((isPromote, promoteExcelConfigData) -> {
                // 等级字符串
                String levelStr = level + (isPromote ? "+" : "");
                // 构建属性数据， 自己提升等级得到的属性 + 突破带来的特有属性加成
                Set<String> propTypeSet = new ListOrderedSet<>();
                propTypeSet.addAll(propGrowCurves.stream().map(AvatarExcelConfigData.PropGrowCurve::getType).collect(Collectors.toList()));
                // 突破带来的特有属性加成放在最后
                propTypeSet.addAll(promoteExcelConfigData.getAddProps().stream().map(AddProp::getPropType).collect(Collectors.toList()));
                propTypeSet.forEach(propType -> {
                    String propTypeText = manualTextMapFactory.getText(lang, propType);
                    // 获取prop数据
                    if(propType == null){
                        return;
                    }
                    Double initValue;
                    try{
                        initValue = AvatarUtil.getInitValueByGrowCurveType(excelConfigData, propType);
                    }catch (IllegalAccessException | InvocationTargetException e){
                        log.error("Get initValue error", e);
                        return;
                    }
                    // 获取武器等级提升
                    String growCurve = null;
                    for(AvatarExcelConfigData.PropGrowCurve propGrowCurve : propGrowCurves){
                        if(propGrowCurve.getType().equals(propType)){
                            growCurve = propGrowCurve.getGrowCurve();
                            break;
                        }
                    }
                    CurveExcelConfigData.CurveInfo curveInfo = curveFactory.getCurveInfo(CurveEnum.AVATAR.getCode(), level, growCurve);
                    // 突破提升的属性
                    double promoteValue = 0.0;
                    for (AddProp addProp : promoteExcelConfigData.getAddProps()) {
                        if (addProp.getPropType().equals(propType)) {
                            promoteValue = addProp.getValue() == null ? 0 : addProp.getValue();
                            break;
                        }
                    }
                    // 计算属性值
                    double value = CurveUtil.calculateCurveInfo(initValue, curveInfo, promoteValue);
                    // 构建属性数据
                    LevelProperty.Property property = new LevelProperty.Property();
                    property.setPropType(propTypeText);
                    property.setValue(value);
                    levelStr2PropertyMap.computeIfAbsent(levelStr, v -> new ArrayList<>()).add(property);
                });
            });
        });
        List<LevelProperty> avatarProperties = new ArrayList<>();
        List<String> sortedLevelStr = new ArrayList<>(levelStr2PropertyMap.keySet());
        sortedLevelStr.sort((o1, o2) -> {
            int o1v = o1.endsWith("+") ?
                    Integer.parseInt(o1.substring(0, o1.length()-1)) * 2 + 1:
                    Integer.parseInt(o1) * 2;
            int o2v = o2.endsWith("+") ?
                    Integer.parseInt(o2.substring(0, o2.length()-1)) * 2 + 1:
                    Integer.parseInt(o2) * 2;
            return o1v - o2v;
        });
        for(String levelStr : sortedLevelStr){
            LevelProperty avatarProperty = new LevelProperty();
            avatarProperty.setLevel(levelStr);
            avatarProperty.setProperties(levelStr2PropertyMap.get(levelStr));
            avatarProperties.add(avatarProperty);
        }
        avatar.setAvatarProperties(avatarProperties);
        avatar.setInitialWeapon(weaponManager.getWeaponInfo(lang, excelConfigData.getInitialWeapon()));
        avatar.setRangeAttack(excelConfigData.getRangeAttack());
        // 解析技能 canSkillDepotIds是不同形态角色技能和天赋 eg:旅行者
        Set<Long> skillDepotIdSet = new ListOrderedSet<>();
        skillDepotIdSet.add(excelConfigData.getSkillDepotId());
        skillDepotIdSet.addAll(excelConfigData.getCandSkillDepotIds());
        Map<Long, List<Avatar.ActiveSkill>> skillActive = new HashMap<>();
        Map<Long, List<Avatar.PassiveSkill>> skillPassive = new HashMap<>();
        Map<Long, List<Avatar.Talent>> talents = new HashMap<>();
        skillDepotIdSet.forEach(skillDepotId -> {
            AvatarSkillDepotExcelConfigData skillDepot = avatarFactory.getAvatarSkillDepot(skillDepotId);
            if(elementalType != null && !isMatchSkillDepotElementType(skillDepot, elementalType)){
                return;
            }
            List<Avatar.ActiveSkill> skillsActive = new ArrayList<>();
            List<Avatar.PassiveSkill> skillsPassive = new ArrayList<>();
            List<Avatar.Talent> talentList = new ArrayList<>();
            // 解析主动技能
            List<Long> activeSkillIds = new ArrayList<>(skillDepot.getSkills());
            activeSkillIds.add(skillDepot.getEnergySkill());
            activeSkillIds.forEach(skillId -> {
                Avatar.ActiveSkill activeSkill = convertActiveSkill(lang, avatarFactory.getAvatarSkill(skillId));
                if(activeSkill != null){
                    skillsActive.add(activeSkill);
                }
            });
            // 解析被动技能
            skillDepot.getInherentProudSkillOpens().forEach(inherentProudSkillOpen -> {
                Avatar.PassiveSkill passiveSkill = convertPassiveSkill(lang, inherentProudSkillOpen);
                if(passiveSkill != null){
                    skillsPassive.add(passiveSkill);
                }
            });
            // 解析天赋
            List<Long> talentIds = skillDepot.getTalents();
            if(talentIds != null){
                talentIds.forEach(talentId -> {
                    AvatarTalentExcelConfigData talent = avatarFactory.getAvatarTalent(talentId);
                    if(talent == null){
                        return;
                    }
                    talentList.add(convertTalent(lang, talent));
                });
            }
            skillActive.put(skillDepotId, skillsActive);
            skillPassive.put(skillDepotId, skillsPassive);
            talents.put(skillDepotId, talentList);
        });
        avatar.setSkillActive(skillActive);
        avatar.setSkillPassive(skillPassive);
        avatar.setTalents(talents);
        avatar.setStaminaRecoverSpeed(excelConfigData.getStaminaRecoverSpeed());
        // 解析进阶
        List<Integer> rewardPromoteLevelList = excelConfigData.getAvatarPromoteRewardLevelList();
        List<Long> rewardIdList = excelConfigData.getAvatarPromoteRewardIdList();
        Map<Integer, RewardExcelConfigData> promoteLevel2Reward = new HashMap<>();
        for(int i=0; i<rewardIdList.size(); i++){
            promoteLevel2Reward.put(rewardPromoteLevelList.get(i), rewardFactory.get(rewardIdList.get(i)));
        }
        Map<Integer, AvatarPromoteExcelConfigData> promoteMap = avatarFactory.getAvatarPromoteMap(excelConfigData.getAvatarPromoteId());
        avatar.setAvatarPromote(promoteMap.values().stream().map(data -> {
            Avatar.AvatarPromote cost = new Avatar.AvatarPromote();
            cost.setAvatarPromoteId(data.getAvatarPromoteId());
            cost.setPromoteLevel(data.getPromoteLevel());
            cost.setScoinCost(data.getScoinCost());
            List<CostItem> costItems = data.getCostItems().stream().filter(costItem -> costItem.getId() != null).map(costItem -> {
                CostItem weaponCostItem = new CostItem();
                weaponCostItem.setId(costItem.getId());
                weaponCostItem.setName(materialFactory.getMaterialName(lang, costItem.getId()));
                weaponCostItem.setCount(costItem.getCount());
                return weaponCostItem;
            }).collect(Collectors.toList());
            cost.setCostItems(costItems.size() > 0 ? costItems : null);
            cost.setUnlockMaxLevel(data.getUnlockMaxLevel());
            cost.setRequiredPlayerLevel(data.getRequiredPlayerLevel());
            if(promoteLevel2Reward.containsKey(data.getPromoteLevel())){
                RewardExcelConfigData reward = promoteLevel2Reward.get(data.getPromoteLevel());
                List<RewardItem> rewardItems = new ArrayList<>();
                reward.getRewardItemList().forEach(rewardItemData -> {
                    if(rewardItemData == null || rewardItemData.getItemId() == null){
                        return;
                    }
                    RewardItem rewardItem = new RewardItem();
                    rewardItem.setId(rewardItemData.getItemId());
                    rewardItem.setName(materialFactory.getMaterialName(lang, rewardItemData.getItemId()));
                    rewardItem.setCount(rewardItemData.getItemCount());
                    rewardItems.add(rewardItem);
                });
                cost.setRewardItems(rewardItems.size() > 0 ? rewardItems : null);
            }
            return cost;
        }).collect(Collectors.toList()));
        return avatar;
    }

    private boolean isMatchSkillDepotElementType(AvatarSkillDepotExcelConfigData skillDepot, Byte elementalType){
        List<Long> activeSkillIds = new ArrayList<>(skillDepot.getSkills());
        activeSkillIds.add(skillDepot.getEnergySkill());
        for(Long skillId : activeSkillIds){
            AvatarSkillExcelConfigData skill = avatarFactory.getAvatarSkill(skillId);
            if(skill != null && skill.getCostElemType() != null){
                Byte elemCode = elementString2Enum.get(skill.getCostElemType());
                if(elemCode.equals(elementalType)){
                    return true;
                }
            }
        }
        return false;
    }

    private Avatar.ActiveSkill convertActiveSkill(Byte lang, AvatarSkillExcelConfigData activeSkill){
        if(activeSkill == null){
            return null;
        }
        Avatar.ActiveSkill avatarActiveSkill = new Avatar.ActiveSkill();
        avatarActiveSkill.setId(activeSkill.getId());
        avatarActiveSkill.setName(textMapFactory.getText(lang, activeSkill.getNameTextMapHash()));
        avatarActiveSkill.setDesc(textMapFactory.getText(lang, activeSkill.getDescTextMapHash()));
        avatarActiveSkill.setCdTime(activeSkill.getCdTime());
        avatarActiveSkill.setCostElemType(manualTextMapFactory.getText(lang, activeSkill.getCostElemType()));
        avatarActiveSkill.setCostElemVal(activeSkill.getCostElemVal());
        avatarActiveSkill.setCostStamina(activeSkill.getCostStamina());
        avatarActiveSkill.setMaxChargeNum(activeSkill.getMaxChargeNum());
        avatarActiveSkill.setSkillProperties(convertSkillProperties(lang, avatarFactory.getProudSkillLevelMap(activeSkill.getProudSkillGroupId())));
        return avatarActiveSkill;
    }

    private Avatar.PassiveSkill convertPassiveSkill(Byte lang, AvatarSkillDepotExcelConfigData.InherentProudSkillOpen inherentProudSkillOpen){
        if(inherentProudSkillOpen == null){
            return null;
        }
        Map<Integer, ProudSkillExcelConfigData> proudSkillExcelConfigDataMap = avatarFactory.getProudSkillLevelMap(inherentProudSkillOpen.getProudSkillGroupId());
        if(proudSkillExcelConfigDataMap == null){
            return null;
        }
        Avatar.PassiveSkill avatarPassiveSkill = new Avatar.PassiveSkill();
        //TODO 名称设计不合理，只能从技能中拿到名字，只有在一个等级的时候是合理的
        proudSkillExcelConfigDataMap.forEach((level, proudSkillExcelConfigData) -> {
            Long nameHash = proudSkillExcelConfigData.getNameTextMapHash();
            Long descHash = proudSkillExcelConfigData.getDescTextMapHash();
            if(!StringUtils.hasLength(avatarPassiveSkill.getName())){
                avatarPassiveSkill.setName(textMapFactory.getText(lang, nameHash));
            }else{
                log.warn("Multi name for PassiveSkill, groupId={}", proudSkillExcelConfigData.getProudSkillGroupId());
            }
            if(!StringUtils.hasLength(avatarPassiveSkill.getDesc())){
                avatarPassiveSkill.setDesc(textMapFactory.getText(lang, descHash));
            }else{
                log.warn("Multi desc for PassiveSkill, groupId={}", proudSkillExcelConfigData.getProudSkillGroupId());
            }
            if(avatarPassiveSkill.getProudSkillId() != null){
                avatarPassiveSkill.setProudSkillId(proudSkillExcelConfigData.getProudSkillId());
            }else{
                log.warn("Multi id for PassiveSkill, groupId={}", proudSkillExcelConfigData.getProudSkillGroupId());
            }
        });
        avatarPassiveSkill.setProudSkillGroupId(inherentProudSkillOpen.getProudSkillGroupId());
        avatarPassiveSkill.setNeedAvatarPromoteLevel(inherentProudSkillOpen.getNeedAvatarPromoteLevel());
        avatarPassiveSkill.setSkillProperties(convertSkillProperties(lang, proudSkillExcelConfigDataMap));
        return avatarPassiveSkill;
    }

    private List<Avatar.Skill.SkillProperty> convertSkillProperties(Byte lang, Map<Integer, ProudSkillExcelConfigData> proudSkillExcelConfigDataMap){
        List<Avatar.Skill.SkillProperty> skillProperties = new ArrayList<>();
        if(proudSkillExcelConfigDataMap == null){
            return null;
        }
        proudSkillExcelConfigDataMap.forEach((level, proudSkillExcelConfigData) -> {
            Avatar.Skill.SkillProperty skillProperty = new Avatar.Skill.SkillProperty();
            skillProperty.setLevel(proudSkillExcelConfigData.getLevel());
            skillProperty.setCoinCost(proudSkillExcelConfigData.getCoinCost());
            List<CostItem> costItems = proudSkillExcelConfigData.getCostItems().stream().filter(costItem -> costItem.getId() != null).map(costItem -> {
                CostItem weaponCostItem = new CostItem();
                weaponCostItem.setId(costItem.getId());
                weaponCostItem.setName(materialFactory.getMaterialName(lang, costItem.getId()));
                weaponCostItem.setCount(costItem.getCount());
                return weaponCostItem;
            }).collect(Collectors.toList());
            skillProperty.setCostItems(costItems.size() > 0 ? costItems : null);
            Map<String, String> paramDescValueMap = new ListOrderedMap<>();
            List<Long> paramDescList = proudSkillExcelConfigData.getParamDescList();
            for (Long hash : paramDescList) {
                String descValue = textMapFactory.getText(lang, hash);
                if (!StringUtils.hasLength(descValue)) {
                    continue;
                }
                String[] descValueArr = descValue.split("\\|");
                String desc = descValueArr[0];
                String value = descValueArr.length > 1 ? descValueArr[1] : null;
                paramDescValueMap.put(desc, value);
            }
            if(paramDescValueMap.size() > 0){
                skillProperty.setParamDescValueMap(paramDescValueMap);
                skillProperty.setParams(proudSkillExcelConfigData.getParamList());
            }
            skillProperties.add(skillProperty);
        });
        return skillProperties.size() > 0 ? skillProperties : null;
    }

    public Avatar.Talent convertTalent(Byte lang, AvatarTalentExcelConfigData avatarTalentExcelConfigData){
        Avatar.Talent talent = new Avatar.Talent();
        talent.setTalentId(avatarTalentExcelConfigData.getTalentId());
        talent.setPrevTalentId(avatarTalentExcelConfigData.getPrevTalent());
        talent.setName(textMapFactory.getText(lang, avatarTalentExcelConfigData.getNameTextMapHash()));
        talent.setDesc(textMapFactory.getText(lang, avatarTalentExcelConfigData.getDescTextMapHash()));
        CostItem costItem = new CostItem();
        costItem.setId(avatarTalentExcelConfigData.getMainCostItemId());
        costItem.setName(materialFactory.getMaterialName(lang, avatarTalentExcelConfigData.getMainCostItemId()));
        costItem.setCount(avatarTalentExcelConfigData.getMainCostItemCount());
        talent.setCostItem(costItem);
        // 添加非空属性加成
        talent.setAddProperties(avatarTalentExcelConfigData.getAddProps().stream().filter(addProp -> addProp.getPropType() != null).map(addProp -> {
            AddProperty addProperty = new AddProperty();
            addProperty.setPropType(manualTextMapFactory.getText(lang, addProp.getPropType()));
            addProperty.setValue(addProp.getValue());
            return addProperty;
        }).collect(Collectors.toList()));
        talent.setParamList(avatarTalentExcelConfigData.getParamList());
        return talent;
    }
}
