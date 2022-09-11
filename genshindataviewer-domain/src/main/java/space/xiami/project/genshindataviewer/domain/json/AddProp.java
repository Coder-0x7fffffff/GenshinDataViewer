package space.xiami.project.genshindataviewer.domain.json;

public class AddProp{
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