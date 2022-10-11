package space.xiami.project.genshindataviewer.domain.model;

import java.util.List;

/**
 * @author Xiami
 */
public class Reliquary {

    /**
     * 唯一id
     */
    private Long id;

    /**
     * 套装id
     */
    private Long setId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 圣遗物类型
     */
    private String equipType;

    /**
     * 属性加成的等级
     */
    private List<Integer> addPropLevels;

    /**
     * 故事
     */
    private String story;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public List<Integer> getAddPropLevels() {
        return addPropLevels;
    }

    public void setAddPropLevels(List<Integer> addPropLevels) {
        this.addPropLevels = addPropLevels;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
