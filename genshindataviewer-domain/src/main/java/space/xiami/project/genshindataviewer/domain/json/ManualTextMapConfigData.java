package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

public class ManualTextMapConfigData {

    private String textMapId;
    private Long textMapContentTextMapHash;
    private List<String> paramTypes;

    public String getTextMapId() {
        return textMapId;
    }

    public void setTextMapId(String textMapId) {
        this.textMapId = textMapId;
    }

    public Long getTextMapContentTextMapHash() {
        return textMapContentTextMapHash;
    }

    public void setTextMapContentTextMapHash(Long textMapContentTextMapHash) {
        this.textMapContentTextMapHash = textMapContentTextMapHash;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
    }
}
