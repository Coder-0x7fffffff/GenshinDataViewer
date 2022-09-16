package space.xiami.project.genshindataviewer.domain.model;

import space.xiami.project.genshindataviewer.domain.json.WeaponExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.WeaponPromoteExcelConfigData;

import java.util.List;
import java.util.Map;

public class Weapon {
    // 映射数据
    /**
     * 唯一id
     */
    private Long id;
    /**
     * 名称 nameTextMapHash 通过 TextMap 映射
     */
    private String name;
    /**
     * 描述 descTextMapHash 通过 TextMap 映射
     */
    private String desc;
    /**
     * 类型
     */
    private String itemType;
    /**
     * 武器类型
     */
    private String weaponType;
    /**
     * 武器阶级
     */
    private Integer rankLevel;
    /**
     * 基础经验值
     */
    private Integer weaponBaseExp;
    /**
     * 武器效果 refinementRank -> EquipAffix;
     */
    private List<WeaponEquipAffix> weaponEquipAffixes;
    /**
     * 武器属性 weaponProp -> WeaponCurveExcelConfigData; List<WeaponProperty>
     */
    private List<WeaponProperty> weaponProperties;
    /**
     * 武器突破材料
     */
    private List<WeaponPromoteCost> weaponPromoteCosts;
    /**
     * 武器故事 TODO storyId映射到DocumentExcelConfigData 再通过contentLocallizedId映射到LocalizationExcelCOnfigData ......
     */
    // story; GFCNPCMMGHC; gachaCardNameHashPre; destroyRule; destroyReturnMaterial; destroyReturnMaterialCount; initialLockState; awakenTexture; awakenLightMapTexture; awakenIcon; weight; rank; gadgetId; icon;

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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getWeaponBaseExp() {
        return weaponBaseExp;
    }

    public void setWeaponBaseExp(Integer weaponBaseExp) {
        this.weaponBaseExp = weaponBaseExp;
    }

    public List<WeaponEquipAffix> getWeaponEquipAffixes() {
        return weaponEquipAffixes;
    }

    public void setWeaponEquipAffixes(List<WeaponEquipAffix> weaponEquipAffixes) {
        this.weaponEquipAffixes = weaponEquipAffixes;
    }

    public List<WeaponProperty> getWeaponProperties() {
        return weaponProperties;
    }

    public void setWeaponProperties(List<WeaponProperty> weaponProperties) {
        this.weaponProperties = weaponProperties;
    }

    public List<WeaponPromoteCost> getWeaponPromoteCosts() {
        return weaponPromoteCosts;
    }

    public void setWeaponPromoteCosts(List<WeaponPromoteCost> weaponPromoteCosts) {
        this.weaponPromoteCosts = weaponPromoteCosts;
    }

    public static class WeaponPromoteCost {
        private Long weaponPromoteId;
        private Integer promoteLevel;
        private Integer coinCost;
        private List<WeaponCostItem> weaponCostItems;
        private Integer unlockMaxLevel;
        private Integer requiredPlayerLevel;

        public Long getWeaponPromoteId() {
            return weaponPromoteId;
        }

        public void setWeaponPromoteId(Long weaponPromoteId) {
            this.weaponPromoteId = weaponPromoteId;
        }

        public Integer getPromoteLevel() {
            return promoteLevel;
        }

        public void setPromoteLevel(Integer promoteLevel) {
            this.promoteLevel = promoteLevel;
        }

        public Integer getCoinCost() {
            return coinCost;
        }

        public void setCoinCost(Integer coinCost) {
            this.coinCost = coinCost;
        }

        public List<WeaponCostItem> getWeaponCostItems() {
            return weaponCostItems;
        }

        public void setWeaponCostItems(List<WeaponCostItem> weaponCostItems) {
            this.weaponCostItems = weaponCostItems;
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

        public static class WeaponCostItem{
            private Long id;
            private String name;
            private Integer count;

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

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }
        }
    }

    public static class WeaponProperty{
        private String level;
        private List<Property> properties;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<Property> getProperties() {
            return properties;
        }

        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        public static class Property{
            private String propType; // 通过ManualTextMapConfigData映射得到的名称
            private Double value; // initValue 通过type定义的ARITH操作后得到的结果 + promote对应的突破增加的属性

            public String getPropType() {
                return propType;
            }

            public void setPropType(String propType) {
                this.propType = propType;
            }

            public Double getValue() {
                return value;
            }

            public void setValue(Double value) {
                this.value = value;
            }
        }
    }

    public static class WeaponEquipAffix{
        private Integer refinementRank; // 精炼等级
        private Integer awakenCost; // 精炼费用
        private List<EquipAffix> equipAffix; // 效果

        public Integer getRefinementRank() {
            return refinementRank;
        }

        public void setRefinementRank(Integer refinementRank) {
            this.refinementRank = refinementRank;
        }

        public Integer getAwakenCost() {
            return awakenCost;
        }

        public void setAwakenCost(Integer awakenCost) {
            this.awakenCost = awakenCost;
        }

        public List<EquipAffix> getEquipAffix() {
            return equipAffix;
        }

        public void setEquipAffix(List<EquipAffix> equipAffix) {
            this.equipAffix = equipAffix;
        }
    }
}
