package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

public class MaterialExcelConfigData {
    private Long id;
    private Long nameTextMapHash;
    private Long descTextMapHash;
    private String icon;
    private String itemType;
    private Integer weight;
    private Integer rank;
    private Long gadgetId;
    private Long interactionTitleTextMapHash;
    private Boolean noFirstGetHint;
    private String materialType;
    private Integer stackLimit;
    private List<ItemUse> itemUse;
    private Integer rankLevel;
    private Long effectDescTextMapHash;
    private Long specialDescTextMapHash;
    private Long typeDescTextMapHash;
    private String effectIcon;
    private String effectName;
    private List<String> picPath;
    private List<Integer> satiationParams;
    private String destroyRule;
    private List<Long> destroyReturnMaterial;
    private List<Integer> destroyReturnMaterialCount;

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

    public Long getInteractionTitleTextMapHash() {
        return interactionTitleTextMapHash;
    }

    public void setInteractionTitleTextMapHash(Long interactionTitleTextMapHash) {
        this.interactionTitleTextMapHash = interactionTitleTextMapHash;
    }

    public Boolean getNoFirstGetHint() {
        return noFirstGetHint;
    }

    public void setNoFirstGetHint(Boolean noFirstGetHint) {
        this.noFirstGetHint = noFirstGetHint;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Integer getStackLimit() {
        return stackLimit;
    }

    public void setStackLimit(Integer stackLimit) {
        this.stackLimit = stackLimit;
    }

    public List<ItemUse> getItemUse() {
        return itemUse;
    }

    public void setItemUse(List<ItemUse> itemUse) {
        this.itemUse = itemUse;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Long getEffectDescTextMapHash() {
        return effectDescTextMapHash;
    }

    public void setEffectDescTextMapHash(Long effectDescTextMapHash) {
        this.effectDescTextMapHash = effectDescTextMapHash;
    }

    public Long getSpecialDescTextMapHash() {
        return specialDescTextMapHash;
    }

    public void setSpecialDescTextMapHash(Long specialDescTextMapHash) {
        this.specialDescTextMapHash = specialDescTextMapHash;
    }

    public Long getTypeDescTextMapHash() {
        return typeDescTextMapHash;
    }

    public void setTypeDescTextMapHash(Long typeDescTextMapHash) {
        this.typeDescTextMapHash = typeDescTextMapHash;
    }

    public String getEffectIcon() {
        return effectIcon;
    }

    public void setEffectIcon(String effectIcon) {
        this.effectIcon = effectIcon;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public List<String> getPicPath() {
        return picPath;
    }

    public void setPicPath(List<String> picPath) {
        this.picPath = picPath;
    }

    public List<Integer> getSatiationParams() {
        return satiationParams;
    }

    public void setSatiationParams(List<Integer> satiationParams) {
        this.satiationParams = satiationParams;
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

    private static class ItemUse{
        private List<String> useParam;

        public List<String> getUseParam() {
            return useParam;
        }

        public void setUseParam(List<String> useParam) {
            this.useParam = useParam;
        }
    }
}
