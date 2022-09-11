package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

public class WeaponPromoteExcelConfigData {

    private Long weaponPromoteId;
    private Integer promoteLevel;
    private List<CostItem> costItems;
    private Integer coinCost;
    private List<AddProp> addProps;
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

    public List<CostItem> getCostItems() {
        return costItems;
    }

    public void setCostItems(List<CostItem> costItems) {
        this.costItems = costItems;
    }

    public Integer getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(Integer coinCost) {
        this.coinCost = coinCost;
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

    public static class CostItem{
        private Long id;
        private Integer count;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
