package space.xiami.project.genshindataviewer.service.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.common.enums.CurveEnum;
import space.xiami.project.genshindataviewer.domain.json.AddProp;
import space.xiami.project.genshindataviewer.domain.json.AvatarExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.CurveExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.AvatarPromoteExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.Avatar;
import space.xiami.project.genshindataviewer.domain.model.LevelProperty;
import space.xiami.project.genshindataviewer.service.factory.AvatarFactory;
import space.xiami.project.genshindataviewer.service.factory.CurveManager;
import space.xiami.project.genshindataviewer.service.factory.ManualTextMapFactory;
import space.xiami.project.genshindataviewer.service.factory.TextMapFactory;
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
                List<String> propTypeList = propGrowCurves.stream().map(AvatarExcelConfigData.PropGrowCurve::getType).collect(Collectors.toList());
                Set<String> propTypeSet = new HashSet<>(propTypeList);
                promoteExcelConfigData.getAddProps().stream().map(AddProp::getPropType).collect(Collectors.toList()).forEach(propType -> {
                    // 突破带来的特有属性加成放在最后
                    if(!propTypeSet.contains(propType)){
                        propTypeSet.add(propType);
                        propTypeList.add(propType);
                    }
                });
                propTypeList.forEach(propType -> {
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
        avatar.setInitialWeapon(weaponManager.getWeapon(lang, excelConfigData.getInitialWeapon()));
        avatar.setRangeAttack(excelConfigData.getRangeAttack());
        avatar.setSkillDepotId(excelConfigData.getSkillDepotId());
        avatar.setCandSkillDepotIds(excelConfigData.getCandSkillDepotIds());
        avatar.setStaminaRecoverSpeed(excelConfigData.getStaminaRecoverSpeed());
        avatar.setManekinMotionConfig(excelConfigData.getManekinMotionConfig());
        avatar.setAvatarIdentityType(excelConfigData.getAvatarIdentityType());
        avatar.setAvatarPromoteId(excelConfigData.getAvatarPromoteId());
        avatar.setAvatarPromoteRewardLevelList(excelConfigData.getAvatarPromoteRewardLevelList());
        avatar.setAvatarPromoteRewardIdList(excelConfigData.getAvatarPromoteRewardIdList());
        avatar.setFeatureTagGroupID(excelConfigData.getFeatureTagGroupID());
        return avatar;
    }
}
