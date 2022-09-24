package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class ReliquarySetExcelConfigData {

    private Long setId;
    private String setIcon;
    private List<Integer> setNeedNum;
    private Long EquipAffixId;
    private Integer DisableFilter;
    private List<Long> containsList;
    private Integer IPJNHPIDCAA;
    private List<Long> IGDFMGFPICF;
    private List<Long> textList;

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getSetIcon() {
        return setIcon;
    }

    public void setSetIcon(String setIcon) {
        this.setIcon = setIcon;
    }

    public List<Integer> getSetNeedNum() {
        return setNeedNum;
    }

    public void setSetNeedNum(List<Integer> setNeedNum) {
        this.setNeedNum = setNeedNum;
    }

    public Long getEquipAffixId() {
        return EquipAffixId;
    }

    public void setEquipAffixId(Long equipAffixId) {
        EquipAffixId = equipAffixId;
    }

    public Integer getDisableFilter() {
        return DisableFilter;
    }

    public void setDisableFilter(Integer disableFilter) {
        DisableFilter = disableFilter;
    }

    public List<Long> getContainsList() {
        return containsList;
    }

    public void setContainsList(List<Long> containsList) {
        this.containsList = containsList;
    }

    public Integer getIPJNHPIDCAA() {
        return IPJNHPIDCAA;
    }

    public void setIPJNHPIDCAA(Integer IPJNHPIDCAA) {
        this.IPJNHPIDCAA = IPJNHPIDCAA;
    }

    public List<Long> getIGDFMGFPICF() {
        return IGDFMGFPICF;
    }

    public void setIGDFMGFPICF(List<Long> IGDFMGFPICF) {
        this.IGDFMGFPICF = IGDFMGFPICF;
    }

    public List<Long> getTextList() {
        return textList;
    }

    public void setTextList(List<Long> textList) {
        this.textList = textList;
    }
}
