package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class ProudSkillExcelConfigData {
    private Long proudSkillId;
    private Long proudSkillGroupId;
    private Integer level;
    private Integer proudSkillType;
    private Long nameTextMapHash;
    private Long descTextMapHash;
    private Long unlockDescTextMapHash;
    private String icon;
    private Integer coinCost;
    private List<CostItemData> costItems;
    private List<String> filterConds;
    private Integer breakLevel;
    private List<Long> paramDescList;
    private String lifeEffectType;
    private List<String> lifeEffectParams;
    private Boolean EKCLMJKICCF;
    private Integer effectiveForTeam;
    private String openConfig;
    private List<AddProp> addProps;
    private List<Double> paramList;

    public Long getProudSkillId() {
        return proudSkillId;
    }

    public void setProudSkillId(Long proudSkillId) {
        this.proudSkillId = proudSkillId;
    }

    public Long getProudSkillGroupId() {
        return proudSkillGroupId;
    }

    public void setProudSkillGroupId(Long proudSkillGroupId) {
        this.proudSkillGroupId = proudSkillGroupId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getProudSkillType() {
        return proudSkillType;
    }

    public void setProudSkillType(Integer proudSkillType) {
        this.proudSkillType = proudSkillType;
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

    public Long getUnlockDescTextMapHash() {
        return unlockDescTextMapHash;
    }

    public void setUnlockDescTextMapHash(Long unlockDescTextMapHash) {
        this.unlockDescTextMapHash = unlockDescTextMapHash;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(Integer coinCost) {
        this.coinCost = coinCost;
    }

    public List<CostItemData> getCostItems() {
        return costItems;
    }

    public void setCostItems(List<CostItemData> costItemData) {
        this.costItems = costItemData;
    }

    public List<String> getFilterConds() {
        return filterConds;
    }

    public void setFilterConds(List<String> filterConds) {
        this.filterConds = filterConds;
    }

    public Integer getBreakLevel() {
        return breakLevel;
    }

    public void setBreakLevel(Integer breakLevel) {
        this.breakLevel = breakLevel;
    }

    public List<Long> getParamDescList() {
        return paramDescList;
    }

    public void setParamDescList(List<Long> paramDescList) {
        this.paramDescList = paramDescList;
    }

    public String getLifeEffectType() {
        return lifeEffectType;
    }

    public void setLifeEffectType(String lifeEffectType) {
        this.lifeEffectType = lifeEffectType;
    }

    public List<String> getLifeEffectParams() {
        return lifeEffectParams;
    }

    public void setLifeEffectParams(List<String> lifeEffectParams) {
        this.lifeEffectParams = lifeEffectParams;
    }

    public Boolean getEKCLMJKICCF() {
        return EKCLMJKICCF;
    }

    public void setEKCLMJKICCF(Boolean EKCLMJKICCF) {
        this.EKCLMJKICCF = EKCLMJKICCF;
    }

    public Integer getEffectiveForTeam() {
        return effectiveForTeam;
    }

    public void setEffectiveForTeam(Integer effectiveForTeam) {
        this.effectiveForTeam = effectiveForTeam;
    }

    public String getOpenConfig() {
        return openConfig;
    }

    public void setOpenConfig(String openConfig) {
        this.openConfig = openConfig;
    }

    public List<AddProp> getAddProps() {
        return addProps;
    }

    public void setAddProps(List<AddProp> addProps) {
        this.addProps = addProps;
    }

    public List<Double> getParamList() {
        return paramList;
    }

    public void setParamList(List<Double> paramList) {
        this.paramList = paramList;
    }
}
