package space.xiami.project.genshindataviewer.common.enums;

public enum LanguageEnum {
    CHS((byte) 0, "CHS"),
    CHT((byte) 1, "CHT"),
    DE((byte) 2, "DE"),
    EN((byte) 3, "EN"),
    ES((byte) 4, "ES"),
    FR((byte) 5, "FR"),
    ID((byte) 6, "ID"),
    JP((byte) 7, "JP"),
    KR((byte) 8, "KR"),
    PT((byte) 9, "PT"),
    RU((byte) 10, "RU"),
    TH((byte) 11, "TH"),
    VI((byte) 12, "VI");


    private Byte code;
    private String desc;

    LanguageEnum(Byte code, String desc) {
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
