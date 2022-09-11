package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

public class WeaponCurveExcelConfigData {

    private Integer level;

    private List<CurveInfo> curveInfos;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<CurveInfo> getCurveInfos() {
        return curveInfos;
    }

    public void setCurveInfos(List<CurveInfo> curveInfos) {
        this.curveInfos = curveInfos;
    }

    public static class CurveInfo{
        private String type;
        private String arith;
        private Double value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getArith() {
            return arith;
        }

        public void setArith(String arith) {
            this.arith = arith;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }

}
