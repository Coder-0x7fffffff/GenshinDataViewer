package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

public class WeaponExcelConfigData {
    private Long id;
    private Long nameTextMapHash;
    private Long descTextMapHash;
    private String icon;
    private String itemType;
    private Integer weight;
    private Integer rank;
    private Long gadgetId;
    private String weaponType;
    private Integer rankLevel;
    private Integer weaponBaseExp;
    private List<Long> skillAffix;
    private List<WeaponProp> weaponProp;
    private String awakenTexture;
    private String awakenLightMapTexture;
    private String awakenIcon;
    private Long weaponPromoteId;
    private Long storyId;
    private List<Integer> awakenCosts;
    private Long GFCNPCMMGHC;
    private Long gachaCardNameHashPre;
    private String destroyRule;
    private List<Long> destroyReturnMaterial;
    private List<Integer> destroyReturnMaterialCount;
    private Integer initialLockState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNameTextMapHash() {
        return nameTextMapHash;
    }

    public void setNameTextMapHash(Long nameTextMapHash) {
        this.nameTextMapHash = nameTextMapHash;
    }

    public Long getDescTextMapHash() {
        return descTextMapHash;
    }

    public void setDescTextMapHash(Long descTextMapHash) {
        this.descTextMapHash = descTextMapHash;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getGadgetId() {
        return gadgetId;
    }

    public void setGadgetId(Long gadgetId) {
        this.gadgetId = gadgetId;
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

    public List<Long> getSkillAffix() {
        return skillAffix;
    }

    public void setSkillAffix(List<Long> skillAffix) {
        this.skillAffix = skillAffix;
    }

    public List<WeaponProp> getWeaponProp() {
        return weaponProp;
    }

    public void setWeaponProp(List<WeaponProp> weaponProp) {
        this.weaponProp = weaponProp;
    }

    public String getAwakenTexture() {
        return awakenTexture;
    }

    public void setAwakenTexture(String awakenTexture) {
        this.awakenTexture = awakenTexture;
    }

    public String getAwakenLightMapTexture() {
        return awakenLightMapTexture;
    }

    public void setAwakenLightMapTexture(String awakenLightMapTexture) {
        this.awakenLightMapTexture = awakenLightMapTexture;
    }

    public String getAwakenIcon() {
        return awakenIcon;
    }

    public void setAwakenIcon(String awakenIcon) {
        this.awakenIcon = awakenIcon;
    }

    public Long getWeaponPromoteId() {
        return weaponPromoteId;
    }

    public void setWeaponPromoteId(Long weaponPromoteId) {
        this.weaponPromoteId = weaponPromoteId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public List<Integer> getAwakenCosts() {
        return awakenCosts;
    }

    public void setAwakenCosts(List<Integer> awakenCosts) {
        this.awakenCosts = awakenCosts;
    }

    public Long getGFCNPCMMGHC() {
        return GFCNPCMMGHC;
    }

    public void setGFCNPCMMGHC(Long GFCNPCMMGHC) {
        this.GFCNPCMMGHC = GFCNPCMMGHC;
    }

    public Long getGachaCardNameHashPre() {
        return gachaCardNameHashPre;
    }

    public void setGachaCardNameHashPre(Long gachaCardNameHashPre) {
        this.gachaCardNameHashPre = gachaCardNameHashPre;
    }

    public String getDestroyRule() {
        return destroyRule;
    }

    public void setDestroyRule(String destroyRule) {
        this.destroyRule = destroyRule;
    }

    public List<Long> getDestroyReturnMaterial() {
        return destroyReturnMaterial;
    }

    public void setDestroyReturnMaterial(List<Long> destroyReturnMaterial) {
        this.destroyReturnMaterial = destroyReturnMaterial;
    }

    public List<Integer> getDestroyReturnMaterialCount() {
        return destroyReturnMaterialCount;
    }

    public void setDestroyReturnMaterialCount(List<Integer> destroyReturnMaterialCount) {
        this.destroyReturnMaterialCount = destroyReturnMaterialCount;
    }

    public Integer getInitialLockState() {
        return initialLockState;
    }

    public void setInitialLockState(Integer initialLockState) {
        this.initialLockState = initialLockState;
    }

    public static class WeaponProp{
        private String propType;
        private Double initValue;
        private String type;

        public String getPropType() {
            return propType;
        }

        public void setPropType(String propType) {
            this.propType = propType;
        }

        public Double getInitValue() {
            return initValue;
        }

        public void setInitValue(Double initValue) {
            this.initValue = initValue;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
