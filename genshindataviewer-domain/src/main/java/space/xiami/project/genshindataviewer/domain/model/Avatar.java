package space.xiami.project.genshindataviewer.domain.model;

import space.xiami.project.genshindataviewer.domain.json.AvatarExcelConfigData;

import java.util.List;

public class Avatar {

    /**
     * 唯一id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 武器类型
     */
    private String weaponType;

    /**
     * 充能效率
     */
    private Double chargeEfficiency;

    /**
     * 基础生命值
     */
    private Double hpBase;

    /**
     * 基础攻击力
     */
    private Double attackBase;

    /**
     * 基础防御力
     */
    private Double defenseBase;

    /**
     * 基础暴击率
     */
    private Double critical;

    /**
     * 基础暴击伤害
     */
    private Double criticalHurt;

    /**
     * 属性
     */
    private List<LevelProperty> avatarProperties;

    /**
     * 初始武器
     */
    private Weapon initialWeapon;

    /**
     * 范围攻击
     */
    private Boolean isRangeAttack;

    /**
     * 技能
     * TODO 解析
     * -skillDepotId-> AvatarSkillDepotExcelConfigData
     * -skill/energySkill-> AvatarSkillExcelConfigData
     * -proudSkillGroupId-> ProudSkillExcelConfigData
     */
    private Long skillDepotId;

    /**
     * TODO 分析
     */
    private List<Long> candSkillDepotIds;

    /**
     * 体力回复速度
     */
    private Double staminaRecoverSpeed;

    /**
     * TODO 分析
     */
    private Long manekinMotionConfig;

    /**
     * TODO 分析 ? 是否需要
     */
    private String avatarIdentityType;

    /**
     * 角色突破ID TODO 解析 AvatarPromoteExcelConfigData.json
     */
    private Long avatarPromoteId;

    /**
     * 突破奖励对应的突破等级
     */
    private List<Integer> avatarPromoteRewardLevelList;

    /**
     * 对应的奖励RewardId TODO 解析 RewardExcelConfigData.json
     */
    private List<Long> avatarPromoteRewardIdList;

    /**
     * TODO 分析 ? 是否需要
     */
    private Long featureTagGroupID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public Double getChargeEfficiency() {
        return chargeEfficiency;
    }

    public void setChargeEfficiency(Double chargeEfficiency) {
        this.chargeEfficiency = chargeEfficiency;
    }

    public Double getHpBase() {
        return hpBase;
    }

    public void setHpBase(Double hpBase) {
        this.hpBase = hpBase;
    }

    public Double getAttackBase() {
        return attackBase;
    }

    public void setAttackBase(Double attackBase) {
        this.attackBase = attackBase;
    }

    public Double getDefenseBase() {
        return defenseBase;
    }

    public void setDefenseBase(Double defenseBase) {
        this.defenseBase = defenseBase;
    }

    public Double getCritical() {
        return critical;
    }

    public void setCritical(Double critical) {
        this.critical = critical;
    }

    public Double getCriticalHurt() {
        return criticalHurt;
    }

    public void setCriticalHurt(Double criticalHurt) {
        this.criticalHurt = criticalHurt;
    }

    public List<LevelProperty> getAvatarProperties() {
        return avatarProperties;
    }

    public void setAvatarProperties(List<LevelProperty> avatarProperties) {
        this.avatarProperties = avatarProperties;
    }

    public Weapon getInitialWeapon() {
        return initialWeapon;
    }

    public void setInitialWeapon(Weapon initialWeapon) {
        this.initialWeapon = initialWeapon;
    }

    public Boolean getRangeAttack() {
        return isRangeAttack;
    }

    public void setRangeAttack(Boolean rangeAttack) {
        isRangeAttack = rangeAttack;
    }

    public Long getSkillDepotId() {
        return skillDepotId;
    }

    public void setSkillDepotId(Long skillDepotId) {
        this.skillDepotId = skillDepotId;
    }

    public List<Long> getCandSkillDepotIds() {
        return candSkillDepotIds;
    }

    public void setCandSkillDepotIds(List<Long> candSkillDepotIds) {
        this.candSkillDepotIds = candSkillDepotIds;
    }

    public Double getStaminaRecoverSpeed() {
        return staminaRecoverSpeed;
    }

    public void setStaminaRecoverSpeed(Double staminaRecoverSpeed) {
        this.staminaRecoverSpeed = staminaRecoverSpeed;
    }

    public Long getManekinMotionConfig() {
        return manekinMotionConfig;
    }

    public void setManekinMotionConfig(Long manekinMotionConfig) {
        this.manekinMotionConfig = manekinMotionConfig;
    }

    public String getAvatarIdentityType() {
        return avatarIdentityType;
    }

    public void setAvatarIdentityType(String avatarIdentityType) {
        this.avatarIdentityType = avatarIdentityType;
    }

    public Long getAvatarPromoteId() {
        return avatarPromoteId;
    }

    public void setAvatarPromoteId(Long avatarPromoteId) {
        this.avatarPromoteId = avatarPromoteId;
    }

    public List<Integer> getAvatarPromoteRewardLevelList() {
        return avatarPromoteRewardLevelList;
    }

    public void setAvatarPromoteRewardLevelList(List<Integer> avatarPromoteRewardLevelList) {
        this.avatarPromoteRewardLevelList = avatarPromoteRewardLevelList;
    }

    public List<Long> getAvatarPromoteRewardIdList() {
        return avatarPromoteRewardIdList;
    }

    public void setAvatarPromoteRewardIdList(List<Long> avatarPromoteRewardIdList) {
        this.avatarPromoteRewardIdList = avatarPromoteRewardIdList;
    }

    public Long getFeatureTagGroupID() {
        return featureTagGroupID;
    }

    public void setFeatureTagGroupID(Long featureTagGroupID) {
        this.featureTagGroupID = featureTagGroupID;
    }
}
