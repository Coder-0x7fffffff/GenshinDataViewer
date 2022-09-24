package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class ReliquaryExcelConfigData {

    private Long id;
    private Long nameTextMapHash;
    private Long descTextMapHash;
    private String icon;
    private String itemType;
    private Integer weight;
    private Integer rank;
    private Long gadgetId;
    private Boolean dropable;
    private String equipType;
    private String showPic;
    private Integer rankLevel;
    private Long mainPropDepotId;
    private Long appendPropDepotId;
    private Integer appendPropNum;
    private Long setId;
    private List<Integer> addPropLevels;
    private Integer baseConvExp;
    private Integer maxLevel;
    private Long storyId;
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

    public Boolean getDropable() {
        return dropable;
    }

    public void setDropable(Boolean dropable) {
        this.dropable = dropable;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getShowPic() {
        return showPic;
    }

    public void setShowPic(String showPic) {
        this.showPic = showPic;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Long getMainPropDepotId() {
        return mainPropDepotId;
    }

    public void setMainPropDepotId(Long mainPropDepotId) {
        this.mainPropDepotId = mainPropDepotId;
    }

    public Long getAppendPropDepotId() {
        return appendPropDepotId;
    }

    public void setAppendPropDepotId(Long appendPropDepotId) {
        this.appendPropDepotId = appendPropDepotId;
    }

    public Integer getAppendPropNum() {
        return appendPropNum;
    }

    public void setAppendPropNum(Integer appendPropNum) {
        this.appendPropNum = appendPropNum;
    }

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public List<Integer> getAddPropLevels() {
        return addPropLevels;
    }

    public void setAddPropLevels(List<Integer> addPropLevels) {
        this.addPropLevels = addPropLevels;
    }

    public Integer getBaseConvExp() {
        return baseConvExp;
    }

    public void setBaseConvExp(Integer baseConvExp) {
        this.baseConvExp = baseConvExp;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
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
}
