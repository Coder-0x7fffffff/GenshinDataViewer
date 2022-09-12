package space.xiami.project.genshindataviewer.domain.model;

import space.xiami.project.genshindataviewer.domain.json.AddProp;

import java.util.List;

public class EquipAffix {

    private Long id; // 技能id
    private Integer rankLevel; //技能等级
    private String name; // nameTextMapHash 通过 TextMap 映射
    private String desc; // descTextMapHash 通过 TextMap 映射
    private String openConfig; // 技能效果实现
    private List<AddProp> addProps; // ...
    private List<Double> paramList; // ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
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
