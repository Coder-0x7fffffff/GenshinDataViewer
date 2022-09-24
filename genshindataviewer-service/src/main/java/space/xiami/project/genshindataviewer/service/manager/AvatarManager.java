package space.xiami.project.genshindataviewer.service.manager;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import space.xiami.project.genshindataviewer.common.enums.CurveEnum;
import space.xiami.project.genshindataviewer.domain.json.*;
import space.xiami.project.genshindataviewer.domain.model.*;
import space.xiami.project.genshindataviewer.service.factory.*;
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
    private CurveManager curveManager;

    public Map<String, Long> getAvatarIds(Byte language){
        return avatarFactory.getName2Ids(language);
    }

    public Long getAvatarId(Byte lang, String name){
        return avatarFactory.getIdByName(lang, name);
    }

    public Avatar getAvatar(Byte lang, String name){
        return getAvatar(lang, getAvatarId(lang, name));
    }

    public Avatar getAvatar(Byte lang, Long id){
        if(lang == null || id == null){
            return null;
        }
        AvatarExcelConfigData excelConfigData = avatarFactory.getAvatar(id);
        if(excelConfigData == null){
            return null;
        }
        return convert(lang, excelConfigData);
    }
    
    public Avatar convert(Byte lang, AvatarExcelConfigData excelConfigData){
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
                    CurveExcelConfigData.CurveInfo curveInfo = curveManager.getCurveInfo(CurveEnum.AVATAR.getCode(), level, growCurve);
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
        List<List<Avatar.Skill>> skillDepots = new ArrayList<>();
        List<Avatar.Talent> talents = new ArrayList<>();
        skillDepotIdSet.forEach(skillDepotId -> {
            AvatarSkillDepotExcelConfigData skillDepot = avatarFactory.getAvatarSkillDepot(skillDepotId);
            List<Avatar.Skill> skills = new ArrayList<>();
            // 解析主动技能
            List<Long> activeSkillIds = new ArrayList<>(skillDepot.getSkills());
            activeSkillIds.add(skillDepot.getEnergySkill());
            activeSkillIds.forEach(skillId -> {
                Avatar.Skill activeSkill = convertActiveSkill(lang, avatarFactory.getAvatarSkill(skillId));
                if(activeSkill != null){
                    skills.add(activeSkill);
                }
            });
            // 解析被动技能
            skillDepot.getInherentProudSkillOpens().forEach(inherentProudSkillOpen -> {
                Avatar.Skill passiveSkill = convertPassiveSkill(lang, inherentProudSkillOpen);
                if(passiveSkill != null){
                    skills.add(passiveSkill);
                }
            });
            skillDepots.add(skills);
            // 解析天赋
            List<Long> talentIds = skillDepot.getTalents();
            if(talentIds != null){
                talentIds.forEach(talentId -> {
                    AvatarTalentExcelConfigData talent = avatarFactory.getAvatarTalent(talentId);
                    talents.add(convertTalent(lang, talent));
                });
            }
        });
        avatar.setSkillDepots(skillDepots);
        avatar.setTalents(talents);
        avatar.setStaminaRecoverSpeed(excelConfigData.getStaminaRecoverSpeed());
        // 解析进阶
        List<Integer> rewardPromoteLevelList = excelConfigData.getAvatarPromoteRewardLevelList();
        List<Long> rewardIdList = excelConfigData.getAvatarPromoteRewardIdList();
        Map<Integer, RewardExcelConfigData> promoteLevel2Reward = new HashMap<>();
        for(int i=0; i<rewardIdList.size(); i++){
            promoteLevel2Reward.put(rewardPromoteLevelList.get(i), rewardFactory.getReward(rewardIdList.get(i)));
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

    private Avatar.ActiveSkill convertActiveSkill(Byte lang, AvatarSkillExcelConfigData activeSkill){
        if(activeSkill == null){
            return null;
        }
        Avatar.ActiveSkill avatarActiveSkill = new Avatar.ActiveSkill();
        avatarActiveSkill.setId(activeSkill.getId());
        avatarActiveSkill.setName(textMapFactory.getText(lang, activeSkill.getNameTextMapHash()));
        avatarActiveSkill.setDesc(textMapFactory.getText(lang, activeSkill.getDescTextMapHash()));
        avatarActiveSkill.setCdTime(activeSkill.getCdTime());
        avatarActiveSkill.setCostElemType(activeSkill.getCostElemType());
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
        //名称设计不合理，只能从技能中拿到名字，只有在一个等级的时候是合理的
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
        });
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
