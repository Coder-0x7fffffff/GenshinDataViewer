package space.xiami.project.genshindataviewer.domain.model;

import java.util.List;
import java.util.Map;

/**
 * @author Xiami
 */
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
    private WeaponInfo initialWeapon;

    /**
     * 范围攻击
     */
    private Boolean isRangeAttack;

    /**
     * 技能
     * -skillDepotId-> AvatarSkillDepotExcelConfigData
     * -skill/energySkill-> AvatarSkillExcelConfigData
     * -proudSkillGroupId-> ProudSkillExcelConfigData
     * @see space.xiami.project.genshindataviewer.domain.json.AvatarSkillDepotExcelConfigData
     * @see space.xiami.project.genshindataviewer.domain.json.AvatarSkillExcelConfigData
     * @see space.xiami.project.genshindataviewer.domain.json.ProudSkillExcelConfigData
     */
    private List<List<ActiveSkill>> skillDepotsActive;
    private List<List<PassiveSkill>> skillDepotsPassive;

    /**
     * 命座
     * -skillDepotId-> AvatarSkillDepotExcelConfigData
     * -talents-> AvatarTalentExcelConfigData
     * @see space.xiami.project.genshindataviewer.domain.json.AvatarSkillDepotExcelConfigData
     * @see space.xiami.project.genshindataviewer.domain.json.AvatarTalentExcelConfigData
     */
    private List<Talent> talents;

    /**
     * 体力回复速度
     */
    private Double staminaRecoverSpeed;

    /**
     * 武器突破
     */
    private List<AvatarPromote> avatarPromotes;

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

    public WeaponInfo getInitialWeapon() {
        return initialWeapon;
    }

    public void setInitialWeapon(WeaponInfo initialWeapon) {
        this.initialWeapon = initialWeapon;
    }

    public Boolean getRangeAttack() {
        return isRangeAttack;
    }

    public void setRangeAttack(Boolean rangeAttack) {
        isRangeAttack = rangeAttack;
    }

    public List<List<ActiveSkill>> getSkillDepotsActive() {
        return skillDepotsActive;
    }

    public void setSkillDepotsActive(List<List<ActiveSkill>> skillDepotsActive) {
        this.skillDepotsActive = skillDepotsActive;
    }

    public List<List<PassiveSkill>> getSkillDepotsPassive() {
        return skillDepotsPassive;
    }

    public void setSkillDepotsPassive(List<List<PassiveSkill>> skillDepotsPassive) {
        this.skillDepotsPassive = skillDepotsPassive;
    }

    public List<Talent> getTalents() {
        return talents;
    }

    public void setTalents(List<Talent> talents) {
        this.talents = talents;
    }

    public Double getStaminaRecoverSpeed() {
        return staminaRecoverSpeed;
    }

    public void setStaminaRecoverSpeed(Double staminaRecoverSpeed) {
        this.staminaRecoverSpeed = staminaRecoverSpeed;
    }

    public List<AvatarPromote> getAvatarPromote() {
        return avatarPromotes;
    }

    public void setAvatarPromote(List<AvatarPromote> avatarPromotes) {
        this.avatarPromotes = avatarPromotes;
    }

    public static abstract  class Skill{

        /**
         * 技能名称
         */
        private String name;

        /**
         * 技能描述
         */
        private String desc;

        /**
         * 技能属性列
         */
        private List<SkillProperty> skillProperties;

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

        public List<SkillProperty> getSkillProperties() {
            return skillProperties;
        }

        public void setSkillProperties(List<SkillProperty> skillProperties) {
            this.skillProperties = skillProperties;
        }

        public static class SkillProperty {
            /**
             * 技能等级
             */
            private Integer level;

            /**
             * 消耗
             */
            private Integer coinCost;

            /**
             * 消耗物品
             */
            private List<CostItem> costItems;

            /**
             * 从 paramDescList -> paramList 映射
             */
            private Map<String, String> paramDescValueMap;

            /**
             * 参数
             */
            private List<Double> params;

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public Integer getCoinCost() {
                return coinCost;
            }

            public void setCoinCost(Integer coinCost) {
                this.coinCost = coinCost;
            }

            public List<CostItem> getCostItems() {
                return costItems;
            }

            public void setCostItems(List<CostItem> costItems) {
                this.costItems = costItems;
            }

            public Map<String, String> getParamDescValueMap() {
                return paramDescValueMap;
            }

            public void setParamDescValueMap(Map<String, String> paramDescValueMap) {
                this.paramDescValueMap = paramDescValueMap;
            }

            public List<Double> getParams() {
                return params;
            }

            public void setParams(List<Double> params) {
                this.params = params;
            }
        }
    }

    public static class ActiveSkill extends Skill {

        /**
         * 技能id
         */
        private Long id;

        /**
         * 技能冷却
         */
        private Double cdTime;

        /**
         * 消耗的元素种类
         */
        private String costElemType;

        /**
         * 消耗元素量
         */
        private Double costElemVal;

        /**
         * 体力消耗
         */
        private Double costStamina;

        /**
         * 最大使用次数
         */
        private Integer maxChargeNum;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Double getCdTime() {
            return cdTime;
        }

        public void setCdTime(Double cdTime) {
            this.cdTime = cdTime;
        }

        public String getCostElemType() {
            return costElemType;
        }

        public void setCostElemType(String costElemType) {
            this.costElemType = costElemType;
        }

        public Double getCostElemVal() {
            return costElemVal;
        }

        public void setCostElemVal(Double costElemVal) {
            this.costElemVal = costElemVal;
        }

        public Double getCostStamina() {
            return costStamina;
        }

        public void setCostStamina(Double costStamina) {
            this.costStamina = costStamina;
        }

        public Integer getMaxChargeNum() {
            return maxChargeNum;
        }

        public void setMaxChargeNum(Integer maxChargeNum) {
            this.maxChargeNum = maxChargeNum;
        }
    }

    public static class PassiveSkill extends Skill {
        /**
         * 需要的人物突破等级
         */
        private Integer needAvatarPromoteLevel;

        public Integer getNeedAvatarPromoteLevel() {
            return needAvatarPromoteLevel;
        }

        public void setNeedAvatarPromoteLevel(Integer needAvatarPromoteLevel) {
            this.needAvatarPromoteLevel = needAvatarPromoteLevel;
        }
    }

    public static class Talent{

        /**
         * 命座id
         */
        private Long talentId;

        /**
         * 前置命座
         */
        private Long prevTalentId;

        /**
         * 名称
         */
        private String name;

        /**
         * 描述
         */
        private String desc;

        /**
         * 提升消耗
         */
        private CostItem costItem;

        /**
         * 属性加成
         */
        private List<AddProperty> addProperties;

        /**
         * 参数
         */
        private List<Double> paramList;

        public Long getTalentId() {
            return talentId;
        }

        public void setTalentId(Long talentId) {
            this.talentId = talentId;
        }

        public Long getPrevTalentId() {
            return prevTalentId;
        }

        public void setPrevTalentId(Long prevTalentId) {
            this.prevTalentId = prevTalentId;
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

        public CostItem getCostItem() {
            return costItem;
        }

        public void setCostItem(CostItem costItem) {
            this.costItem = costItem;
        }

        public List<AddProperty> getAddProperties() {
            return addProperties;
        }

        public void setAddProperties(List<AddProperty> addProperties) {
            this.addProperties = addProperties;
        }

        public List<Double> getParamList() {
            return paramList;
        }

        public void setParamList(List<Double> paramList) {
            this.paramList = paramList;
        }
    }

    public static class AvatarPromote {
        private Long avatarPromoteId;
        private Integer promoteLevel;
        private List<CostItem> costItems;
        private Integer scoinCost;
        private Integer unlockMaxLevel;
        private Integer requiredPlayerLevel;
        private List<RewardItem> rewardItems;

        public Long getAvatarPromoteId() {
            return avatarPromoteId;
        }

        public void setAvatarPromoteId(Long avatarPromoteId) {
            this.avatarPromoteId = avatarPromoteId;
        }

        public Integer getPromoteLevel() {
            return promoteLevel;
        }

        public void setPromoteLevel(Integer promoteLevel) {
            this.promoteLevel = promoteLevel;
        }

        public List<CostItem> getCostItems() {
            return costItems;
        }

        public void setCostItems(List<CostItem> costItems) {
            this.costItems = costItems;
        }

        public Integer getScoinCost() {
            return scoinCost;
        }

        public void setScoinCost(Integer scoinCost) {
            this.scoinCost = scoinCost;
        }

        public Integer getUnlockMaxLevel() {
            return unlockMaxLevel;
        }

        public void setUnlockMaxLevel(Integer unlockMaxLevel) {
            this.unlockMaxLevel = unlockMaxLevel;
        }

        public Integer getRequiredPlayerLevel() {
            return requiredPlayerLevel;
        }

        public void setRequiredPlayerLevel(Integer requiredPlayerLevel) {
            this.requiredPlayerLevel = requiredPlayerLevel;
        }

        public List<RewardItem> getRewardItems() {
            return rewardItems;
        }

        public void setRewardItems(List<RewardItem> rewardItems) {
            this.rewardItems = rewardItems;
        }
    }
}
