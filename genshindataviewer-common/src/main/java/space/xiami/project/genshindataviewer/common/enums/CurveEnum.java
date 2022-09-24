package space.xiami.project.genshindataviewer.common.enums;

/**
 * @author Xiami
 */
public enum CurveEnum {

    WEAPON((byte) 0, "WEAPON"),
    AVATAR((byte) 1, "AVATAR");

    private Byte code;
    private String desc;

    CurveEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
