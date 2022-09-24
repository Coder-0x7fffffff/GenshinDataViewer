package space.xiami.project.genshindataviewer.domain.model;

import java.util.List;

/**
 * @author Xiami
 */
public class LevelProperty {
        private String level;
        private List<Property> properties;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<Property> getProperties() {
            return properties;
        }

        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        public static class Property{
            private String propType; // 通过ManualTextMapConfigData映射得到的名称
            private Double value; // 通过type定义的ARITH操作后得到的结果 + promote对应的突破增加的属性

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
    }