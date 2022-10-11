package space.xiami.project.genshindataviewer.domain.model;

import java.util.List;
import java.util.Map;

/**
 * @author Xiami
 */
public class ReliquarySet {

    /**
     * 圣遗物分组id
     */
    private Long setId;

    /**
     * 可用的圣遗物
     */
    private List<Reliquary> reliquaries;

    /**
     * 圣遗物套装效果 count(setNeedNum) -> EquipAffix(EquipAffixId)
     */
    private Map<Integer, EquipAffix> groupEquipAffix;

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public List<Reliquary> getReliquaries() {
        return reliquaries;
    }

    public void setReliquaries(List<Reliquary> reliquaries) {
        this.reliquaries = reliquaries;
    }

    public Map<Integer, EquipAffix> getGroupEquipAffix() {
        return groupEquipAffix;
    }

    public void setGroupEquipAffix(Map<Integer, EquipAffix> groupEquipAffix) {
        this.groupEquipAffix = groupEquipAffix;
    }
}
