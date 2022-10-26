package space.xiami.project.genshindataviewer.domain.model;

import java.util.List;
import java.util.Map;

/**
 * @author Xiami
 */
public class TeamResonance {

    private String name;
    private String desc;
    private Long teamResonanceId;
    private Long teamResonanceGroupId;
    private Integer level;
    private Map<Byte, Integer> elementAvatarLimit;
    private Boolean allDifferent;
    private String openConfig;
    private List<AddProperty> addProperties;
    private List<Double> paramList;

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

    public Long getTeamResonanceId() {
        return teamResonanceId;
    }

    public void setTeamResonanceId(Long teamResonanceId) {
        this.teamResonanceId = teamResonanceId;
    }

    public Long getTeamResonanceGroupId() {
        return teamResonanceGroupId;
    }

    public void setTeamResonanceGroupId(Long teamResonanceGroupId) {
        this.teamResonanceGroupId = teamResonanceGroupId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Map<Byte, Integer> getElementAvatarLimit() {
        return elementAvatarLimit;
    }

    public void setElementAvatarLimit(Map<Byte, Integer> elementAvatarLimit) {
        this.elementAvatarLimit = elementAvatarLimit;
    }

    public Boolean getAllDifferent() {
        return allDifferent;
    }

    public void setAllDifferent(Boolean allDifferent) {
        this.allDifferent = allDifferent;
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
