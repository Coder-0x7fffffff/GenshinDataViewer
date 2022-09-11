package space.xiami.project.genshindataviewer.domain.json;

public class WeaponCodexExcelConfigData {

    private Long Id;
    private Long weaponId;
    private Long SortOrder;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(Long weaponId) {
        this.weaponId = weaponId;
    }

    public Long getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        SortOrder = sortOrder;
    }
}
