package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class AvatarTalentExcelConfigData {
    private Long talentId;
    private Long nameTextMapHash;
    private Long descTextMapHash;
    private String icon;
    private Long prevTalent;
    private Long mainCostItemId;
    private Integer mainCostItemCount;
    private String openConfig;
    private List<AddProp> addProps;
    private List<Double> paramList;

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
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

    public Long getPrevTalent() {
        return prevTalent;
    }

    public void setPrevTalent(Long prevTalent) {
        this.prevTalent = prevTalent;
    }

    public Long getMainCostItemId() {
        return mainCostItemId;
    }

    public void setMainCostItemId(Long mainCostItemId) {
        this.mainCostItemId = mainCostItemId;
    }

    public Integer getMainCostItemCount() {
        return mainCostItemCount;
    }

    public void setMainCostItemCount(Integer mainCostItemCount) {
        this.mainCostItemCount = mainCostItemCount;
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
