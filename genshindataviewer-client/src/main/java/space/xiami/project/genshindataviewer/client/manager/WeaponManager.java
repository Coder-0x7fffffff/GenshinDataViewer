package space.xiami.project.genshindataviewer.client.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.factory.ManualTextMapFactory;
import space.xiami.project.genshindataviewer.client.factory.MaterialFactory;
import space.xiami.project.genshindataviewer.client.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.client.factory.WeaponFactory;
import space.xiami.project.genshindataviewer.domain.json.AddProp;
import space.xiami.project.genshindataviewer.domain.json.WeaponCurveExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.WeaponExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.WeaponPromoteExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.EquipAffix;
import space.xiami.project.genshindataviewer.domain.model.Weapon;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WeaponManager {

    Logger log = LoggerFactory.getLogger(WeaponManager.class);

    @Resource
    private TextMapFactory textMapFactory;

    @Resource
    private WeaponFactory weaponFactory;

    @Resource
    private ManualTextMapFactory manualTextMapFactory;

    @Resource
    private MaterialFactory materialFactory;

    @Resource
    private EquipAffixManager equipAffixManager;

    public Map<String, Long> getWeaponIds(Byte language){
        return weaponFactory.getName2Ids(language);
    }

    public Long getWeaponId(Byte lang, String name){
        return weaponFactory.getIdByName(lang, name);
    }

    public Weapon getWeapon(Byte lang, String name){
        return getWeapon(lang, getWeaponId(lang, name));
    }

    public Weapon getWeapon(Byte lang, Long id){
        if(lang == null || id == null){
            return null;
        }
        WeaponExcelConfigData excelConfigData = weaponFactory.getWeapon(id);
        if(excelConfigData == null){
            return null;
        }
        return convert(lang, excelConfigData);
    }

    public Weapon convert(Byte lang, WeaponExcelConfigData excelConfigData){
        Weapon weapon = new Weapon();
        weapon.setId(excelConfigData.getId());
        weapon.setName(textMapFactory.getText(lang, excelConfigData.getNameTextMapHash()));
        weapon.setDesc(textMapFactory.getText(lang, excelConfigData.getDescTextMapHash()));
        weapon.setItemType(manualTextMapFactory.getText(lang, excelConfigData.getItemType()));
        weapon.setWeaponType(manualTextMapFactory.getText(lang, excelConfigData.getWeaponType()));
        weapon.setRankLevel(excelConfigData.getRankLevel());
        weapon.setWeaponBaseExp(excelConfigData.getWeaponBaseExp());
        // setWeaponEquipAffixes
        Map<Integer, List<EquipAffix>> refinementRank2EquipAffix = new HashMap<>();
        List<Long> skillAffix = excelConfigData.getSkillAffix();
        List<Integer> awakenCosts = excelConfigData.getAwakenCosts();
        int maxRefinementRank = awakenCosts.size()+1;
        // 获取精炼后的属性数据
        for(Long id : skillAffix){
            for(int refinementRank = 1; refinementRank <= maxRefinementRank; refinementRank++){
                EquipAffix equipAffix = equipAffixManager.getByIdAffixLevel(id, refinementRank - 1, lang);
                // 排除为空的效果
                if(equipAffix == null){
                    continue;
                }
                refinementRank2EquipAffix.computeIfAbsent(refinementRank, v -> new ArrayList<>()).add(equipAffix);
            }
        }
        // 精炼效果结构构建
        List<Weapon.WeaponEquipAffix> weaponEquipAffixes = new ArrayList<>();
        List<Integer> sortedRefinementRank = new ArrayList<>(refinementRank2EquipAffix.keySet());
        sortedRefinementRank.sort((Comparator.comparingInt(o -> o)));
        for(Integer refinementRank : sortedRefinementRank){
            Weapon.WeaponEquipAffix weaponEquipAffix = new Weapon.WeaponEquipAffix();
            weaponEquipAffix.setRefinementRank(refinementRank);
            if(refinementRank >= 2 && refinementRank - 2 < awakenCosts.size()){
                weaponEquipAffix.setAwakenCost(awakenCosts.get(refinementRank - 2));
            }
            weaponEquipAffix.setEquipAffix(refinementRank2EquipAffix.get(refinementRank));
            weaponEquipAffixes.add(weaponEquipAffix);
        }
        weapon.setWeaponEquipAffixes(weaponEquipAffixes);
        //set weaponPromoteId
        Map<Integer, WeaponPromoteExcelConfigData> promoteMap = weaponFactory.getWeaponPromoteMap(excelConfigData.getWeaponPromoteId());
        weapon.setWeaponPromoteCosts(promoteMap.values().stream().map(data -> {
            Weapon.WeaponPromoteCost cost = new Weapon.WeaponPromoteCost();
            cost.setWeaponPromoteId(data.getWeaponPromoteId());
            cost.setPromoteLevel(data.getPromoteLevel());
            cost.setCoinCost(data.getCoinCost());
            cost.setWeaponCostItems(data.getCostItems().stream().filter(costItem -> costItem.getId() != null).map(costItem -> {
                Weapon.WeaponPromoteCost.WeaponCostItem weaponCostItem = new Weapon.WeaponPromoteCost.WeaponCostItem();
                weaponCostItem.setId(costItem.getId());
                weaponCostItem.setName(materialFactory.getMaterialName(lang, costItem.getId()));
                weaponCostItem.setCount(costItem.getCount());
                return weaponCostItem;
            }).collect(Collectors.toList()));
            cost.setUnlockMaxLevel(data.getUnlockMaxLevel());
            cost.setRequiredPlayerLevel(data.getRequiredPlayerLevel());
            return cost;
        }).collect(Collectors.toList()));
        //set weaponProperties
        Map<String, List<Weapon.WeaponProperty.Property>> levelStr2PropertyMap = new HashMap<>();
        List<Integer> levels = weaponFactory.getSortedLevel();
        excelConfigData.getWeaponProp().forEach(weaponProp -> {
            // 获取prop数据
            String propType = weaponProp.getPropType();
            Double initValue = weaponProp.getInitValue();
            String type = weaponProp.getType();
            // 构建不同等级属性数据
            String propTypeText = manualTextMapFactory.getText(lang, propType);
            levels.forEach(level -> {
                // 获取武器等级提升
                WeaponCurveExcelConfigData.CurveInfo curveInfo = weaponFactory.getWeaponCurveInfo(level, type);
                // 获取武器进阶提升
                weaponFactory.getWeaponPromoteByLevel(excelConfigData.getWeaponPromoteId(), level).forEach((isPromote, promoteExcelConfigData) -> {
                    List<AddProp> addPropList = promoteExcelConfigData.getAddProps();
                    double promoteValue = 0.0;
                    for(AddProp addProp : addPropList){
                        if(addProp.getPropType().equals(propType)){
                            promoteValue = addProp.getValue() == null?0:addProp.getValue();
                            break;
                        }
                    }
                    // 计算属性值
                    double value = calculateCurveInfo(initValue, curveInfo, promoteValue);
                    String levelStr = level +(isPromote?"+":"");
                    // 构建属性数据
                    Weapon.WeaponProperty.Property property = new Weapon.WeaponProperty.Property();
                    property.setPropType(propTypeText);
                    property.setValue(value);
                    levelStr2PropertyMap.computeIfAbsent(levelStr, v->new ArrayList<>()).add(property);
                });
            });
        });
        List<Weapon.WeaponProperty> weaponProperties = new ArrayList<>();
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
            Weapon.WeaponProperty weaponProperty = new Weapon.WeaponProperty();
            weaponProperty.setLevel(levelStr);
            weaponProperty.setProperties(levelStr2PropertyMap.get(levelStr));
            weaponProperties.add(weaponProperty);
        }
        weapon.setWeaponProperties(weaponProperties);
        //TODO 结构化武器故事
        return weapon;
    }

    public Double calculateCurveInfo(Double initValue, WeaponCurveExcelConfigData.CurveInfo curveInfo, Double promoteValue){
        String curveArith = curveInfo.getArith();
        Double curveValue = curveInfo.getValue();
        double value = 0.0;
        if(curveArith.endsWith("ARITH_MULTI")){
            value = initValue * curveValue + promoteValue;
        }else{
            log.warn("Unknown arith:{}", curveArith);
        }
        return value;
    }
}
