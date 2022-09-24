package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class WeaponLevelExcelConfigData {

    private Integer level;
    private List<Integer> requiredExps;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Integer> getRequiredExps() {
        return requiredExps;
    }

    public void setRequiredExps(List<Integer> requiredExps) {
        this.requiredExps = requiredExps;
    }
}
