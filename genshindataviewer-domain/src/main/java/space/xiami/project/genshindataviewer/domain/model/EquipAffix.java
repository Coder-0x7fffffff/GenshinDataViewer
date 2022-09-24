package space.xiami.project.genshindataviewer.domain.model;

import java.util.List;

/**
 * @author Xiami
 */
public class EquipAffix {

    /**
     * 技能详细id
     */
    private Long affixId;

    /**
     *  技能id
     */
    private Long id;

    /**
     * 等阶 武器的时候代表精炼等级(refinementRank - 1) 圣遗物代表套装效果(0/1)
     */
    private Integer level;

    /**
     * nameTextMapHash 通过 TextMap 映射
     */
    private String name;

    /**
     * descTextMapHash 通过 TextMap 映射
     */
    private String desc;

    /**
     *  TODO 解析
     */
    private String openConfig;

    /**
     * 属性加成
     */
    private List<AddProperty> addProperties;

    /**
     * 效果数值
     */
    private List<Double> paramList;

    public Long getAffixId() {
        return affixId;
    }

    public void setAffixId(Long affixId) {
        this.affixId = affixId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public String getOpenConfig() {
        return openConfig;
    }

    public void setOpenConfig(String openConfig) {
        this.openConfig = openConfig;
    }

    public List<AddProperty> getAddProperties() {
        return addProperties;
    }

    public void setAddProperties(List<AddProperty> addProperties) {
        this.addProperties = addProperties;
    }

    public List<Double> getParamList() {
        return paramList;
    }

    public void setParamList(List<Double> paramList) {
        this.paramList = paramList;
    }
}
