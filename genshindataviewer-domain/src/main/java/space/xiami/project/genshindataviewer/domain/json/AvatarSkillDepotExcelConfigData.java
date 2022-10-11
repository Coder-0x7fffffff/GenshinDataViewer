package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class AvatarSkillDepotExcelConfigData {
    private Long id;
    private Long energySkill;
    private List<Long> skills;
    private List<Long> subSkills;
    private Long attackModeSkill;
    private Long leaderTalent;
    private List<String> extraAbilities;
    private List<Long> talents;
    private String talentStarName;
    private List<InherentProudSkillOpen> inherentProudSkillOpens;
    private String skillDepotAbilityGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnergySkill() {
        return energySkill;
    }

    public void setEnergySkill(Long energySkill) {
        this.energySkill = energySkill;
    }

    public List<Long> getSkills() {
        return skills;
    }

    public void setSkills(List<Long> skills) {
        this.skills = skills;
    }

    public List<Long> getSubSkills() {
        return subSkills;
    }

    public void setSubSkills(List<Long> subSkills) {
        this.subSkills = subSkills;
    }

    public Long getAttackModeSkill() {
        return attackModeSkill;
    }

    public void setAttackModeSkill(Long attackModeSkill) {
        this.attackModeSkill = attackModeSkill;
    }

    public Long getLeaderTalent() {
        return leaderTalent;
    }

    public void setLeaderTalent(Long leaderTalent) {
        this.leaderTalent = leaderTalent;
    }

    public List<String> getExtraAbilities() {
        return extraAbilities;
    }

    public void setExtraAbilities(List<String> extraAbilities) {
        this.extraAbilities = extraAbilities;
    }

    public List<Long> getTalents() {
        return talents;
    }

    public void setTalents(List<Long> talents) {
        this.talents = talents;
    }

    public String getTalentStarName() {
        return talentStarName;
    }

    public void setTalentStarName(String talentStarName) {
        this.talentStarName = talentStarName;
    }

    public List<InherentProudSkillOpen> getInherentProudSkillOpens() {
        return inherentProudSkillOpens;
    }

    public void setInherentProudSkillOpens(List<InherentProudSkillOpen> inherentProudSkillOpens) {
        this.inherentProudSkillOpens = inherentProudSkillOpens;
    }

    public String getSkillDepotAbilityGroup() {
        return skillDepotAbilityGroup;
    }

    public void setSkillDepotAbilityGroup(String skillDepotAbilityGroup) {
        this.skillDepotAbilityGroup = skillDepotAbilityGroup;
    }

    public static class InherentProudSkillOpen{
        // TODO 没有名称，只能通过proudSkill拿到名称，在只有一个等级之前是合理的
        private Long proudSkillGroupId;
        private Integer needAvatarPromoteLevel;

        public Long getProudSkillGroupId() {
            return proudSkillGroupId;
        }

        public void setProudSkillGroupId(Long proudSkillGroupId) {
            this.proudSkillGroupId = proudSkillGroupId;
        }

        public Integer getNeedAvatarPromoteLevel() {
            return needAvatarPromoteLevel;
        }

        public void setNeedAvatarPromoteLevel(Integer needAvatarPromoteLevel) {
            this.needAvatarPromoteLevel = needAvatarPromoteLevel;
        }
    }
}
