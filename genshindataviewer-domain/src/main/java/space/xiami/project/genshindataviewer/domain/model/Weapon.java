package space.xiami.project.genshindataviewer.domain.model;

import space.xiami.project.genshindataviewer.domain.json.WeaponExcelConfigData;

import java.util.List;
import java.util.Map;

public class Weapon {
    // 映射数据
    /**
     * 唯一id
     */
    private Long id;
    /**
     * 名称 nameTextMapHash 通过 TextMap 映射
     */
    private String name;
    /**
     * 描述 descTextMapHash 通过 TextMap 映射
     */
    private String desc;
    // icon
    /**
     * 类型 TODO 通过XXX映射得到名称
     */
    private String itemType;
    // weight; rank; gadgetId;
    /**
     * 武器类型 TODO 通过XXX映射得到名称
     */
    private String weaponType;
    /**
     * 武器阶级
     */
    private Integer rankLevel;
    /**
     * 基础经验值
     */
    private Integer weaponBaseExp;
    /**
     * 武器效果 TODO 连接到EquipAffix
     */
    private List<Integer> skillAffix;
    /**
     * 武器属性 weaponProp -> WeaponCurveExcelConfigData; Level -> WeaponProperty
     */
    private List<Map<Integer, WeaponProperty>> weaponProperties;
    // awakenTexture; awakenLightMapTexture; awakenIcon;
    /**
     * 武器突破材料 TODO 结构化突破材料 以及突破产生的属性加成累加
     */
    private Long weaponPromoteId;
    /**
     * 武器故事 TODO storyId映射到DocumentExcelConfigData 再通过contentLocallizedId映射到LocalizationExcelCOnfigData ......
     */
    private Long storyId;
    // awakenCosts; GFCNPCMMGHC; gachaCardNameHashPre; destroyRule; destroyReturnMaterial; destroyReturnMaterialCount; initialLockState;



    public static class WeaponProperty{
        private String propType; // TODO 通过ManualTextMapConfigData映射得到的名称
        private Integer level; // TODO arit操作对应的等级
        private Double value; // TODO initValue 通过type定义的ARIT操作后得到的结果 + promote对应的突破增加的属性

        public String getPropType() {
            return propType;
        }

        public void setPropType(String propType) {
            this.propType = propType;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }

}
