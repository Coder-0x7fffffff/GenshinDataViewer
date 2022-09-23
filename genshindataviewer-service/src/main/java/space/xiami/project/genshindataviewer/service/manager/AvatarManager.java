package space.xiami.project.genshindataviewer.service.manager;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.json.AvatarExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.Avatar;
import space.xiami.project.genshindataviewer.service.factory.AvatarFactory;
import space.xiami.project.genshindataviewer.service.factory.ManualTextMapFactory;
import space.xiami.project.genshindataviewer.service.factory.TextMapFactory;

import javax.annotation.Resource;
import javax.xml.soap.Text;
import java.util.Map;

/**
 * @author Xiami
 */
@Component
public class AvatarManager {

    @Resource
    private AvatarFactory avatarFactory;

    @Resource
    private TextMapFactory textMapFactory;

    @Resource
    private ManualTextMapFactory manualTextMapFactory;

    @Resource
    private WeaponManager weaponManager;

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
        avatar.setPropGrowCurves(excelConfigData.getPropGrowCurves());
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
