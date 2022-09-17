package space.xiami.project.genshindataviewer.domain.model;

import java.util.List;

public class EquipAffix {

    private Long affixId; // 技能详细id
    private Long id; // 技能id
    private Integer level; //等阶 武器的时候代表精炼等级(refinementRank - 1) 圣遗物代表套装效果(0/1)
    private String name; // nameTextMapHash 通过 TextMap 映射
    private String desc; // descTextMapHash 通过 TextMap 映射
    private String openConfig; // TODO 解析
    private List<AddProperty> addProps; // 属性加成
    private List<Double> paramList; // 效果数值

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

    public List<AddProperty> getAddProps() {
        return addProps;
    }

    public void setAddProps(List<AddProperty> addProps) {
        this.addProps = addProps;
    }

    public List<Double> getParamList() {
        return paramList;
    }

    public void setParamList(List<Double> paramList) {
        this.paramList = paramList;
    }

    public static class AddProperty{
        private String propType;
        private Double value;

        public String getPropType() {
            return propType;
        }

        public void setPropType(String propType) {
            this.propType = propType;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }
}
