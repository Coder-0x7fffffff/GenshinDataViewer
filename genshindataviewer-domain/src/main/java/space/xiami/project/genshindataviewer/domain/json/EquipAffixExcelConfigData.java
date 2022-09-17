package space.xiami.project.genshindataviewer.domain.json;

import space.xiami.project.genshindataviewer.domain.json.AddProp;

import java.util.List;

public class EquipAffixExcelConfigData {
    private Long affixId;
    private Long id;
    private Integer level;
    private Long nameTextMapHash;
    private Long descTextMapHash;
    private String openConfig;
    private List<AddProp> addProps;
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
