package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class TeamResonanceExcelConfigData {

    private Long teamResonanceId;
    private Long teamResonanceGroupId;
    private Integer level;
    private Integer fireAvatarCount;
    private Integer waterAvatarCount;
    private Integer windAvatarCount;
    private Integer electricAvatarCount;
    private Integer grassAvatarCount;
    private Integer iceAvatarCount;
    private Integer rockAvatarCount;
    private String cond;
    private Long nameTextMapHash;
    private Long descTextMapHash;
    private String openConfig;
    private List<AddProp> addProps;
    private List<Double> paramList;

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

    public Integer getFireAvatarCount() {
        return fireAvatarCount;
    }

    public void setFireAvatarCount(Integer fireAvatarCount) {
        this.fireAvatarCount = fireAvatarCount;
    }

    public Integer getWaterAvatarCount() {
        return waterAvatarCount;
    }

    public void setWaterAvatarCount(Integer waterAvatarCount) {
        this.waterAvatarCount = waterAvatarCount;
    }

    public Integer getWindAvatarCount() {
        return windAvatarCount;
    }

    public void setWindAvatarCount(Integer windAvatarCount) {
        this.windAvatarCount = windAvatarCount;
    }

    public Integer getElectricAvatarCount() {
        return electricAvatarCount;
    }

    public void setElectricAvatarCount(Integer electricAvatarCount) {
        this.electricAvatarCount = electricAvatarCount;
    }

    public Integer getGrassAvatarCount() {
        return grassAvatarCount;
    }

    public void setGrassAvatarCount(Integer grassAvatarCount) {
        this.grassAvatarCount = grassAvatarCount;
    }

    public Integer getIceAvatarCount() {
        return iceAvatarCount;
    }

    public void setIceAvatarCount(Integer iceAvatarCount) {
        this.iceAvatarCount = iceAvatarCount;
    }

    public Integer getRockAvatarCount() {
        return rockAvatarCount;
    }

    public void setRockAvatarCount(Integer rockAvatarCount) {
        this.rockAvatarCount = rockAvatarCount;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
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
