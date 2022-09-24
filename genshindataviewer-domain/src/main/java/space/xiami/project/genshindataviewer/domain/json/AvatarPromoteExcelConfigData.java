package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class AvatarPromoteExcelConfigData {

    private Long avatarPromoteId;
    private Integer promoteLevel;
    private String promoteAudio;
    private List<CostItem> costItems;
    private Integer scoinCost;
    private List<AddProp> addProps;
    private Integer unlockMaxLevel;
    private Integer requiredPlayerLevel;

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

    public String getPromoteAudio() {
        return promoteAudio;
    }

    public void setPromoteAudio(String promoteAudio) {
        this.promoteAudio = promoteAudio;
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

    public List<AddProp> getAddProps() {
        return addProps;
    }

    public void setAddProps(List<AddProp> addProps) {
        this.addProps = addProps;
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
}
