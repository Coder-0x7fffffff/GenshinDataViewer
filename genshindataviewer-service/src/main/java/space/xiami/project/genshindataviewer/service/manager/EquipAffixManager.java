package space.xiami.project.genshindataviewer.service.manager;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.service.factory.EquipAffixFactory;
import space.xiami.project.genshindataviewer.service.factory.ManualTextMapFactory;
import space.xiami.project.genshindataviewer.service.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.domain.json.EquipAffixExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.EquipAffix;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EquipAffixManager {

    @Resource
    private TextMapFactory textMapFactory;

    @Resource
    private ManualTextMapFactory manualTextMapFactory;

    @Resource
    private EquipAffixFactory equipAffixFactory;

    public Map<Long, EquipAffix> getById(Long id){
        return getById(id, (byte) 0);
    }

    public Map<Long, EquipAffix> getById(Long id, Byte language){
        Map<Long, EquipAffixExcelConfigData> oriRet = equipAffixFactory.getById(id);
        if(oriRet == null){
            return null;
        }
        Map<Long, EquipAffix> ret = new HashMap<>();
        oriRet.forEach((k, v) -> {
            ret.put(k, convert(v, language));
        });
        return ret;
    }

    public EquipAffix getByIdAffixLevel(Long id, Integer affixLevel){
        return getByIdAffixLevel(id, affixLevel, (byte) 0);
    }

    public EquipAffix getByIdAffixLevel(Long id, Integer affixLevel, Byte language){
        EquipAffixExcelConfigData oriRet = equipAffixFactory.getByIdAffixId(id, affixLevel2affixId(id, affixLevel));
        return oriRet == null ? null : convert(oriRet, language);
    }

    private EquipAffix convert(EquipAffixExcelConfigData data, Byte language){
        EquipAffix result = new EquipAffix();
        result.setId(data.getId());
        result.setAffixLevel(affixId2affixLevel(data.getAffixId()));
        result.setName(textMapFactory.getText(language, data.getNameTextMapHash()));
        result.setDesc(textMapFactory.getText(language, data.getDescTextMapHash()));
        result.setOpenConfig(data.getOpenConfig());
        // 添加非空属性加成
        result.setAddProps(data.getAddProps().stream().filter(addProp -> addProp.getPropType() != null).map(addProp -> {
            EquipAffix.AddProperty addProperty = new EquipAffix.AddProperty();
            addProperty.setPropType(manualTextMapFactory.getText(language, addProp.getPropType()));
            addProperty.setValue(addProp.getValue());
            return addProperty;
        }).collect(Collectors.toList()));
        result.setParamList(data.getParamList());
        return result;
    }

    private Integer affixId2affixLevel(Long affixId){
        return affixId == null ? null : ((int) (affixId % 10));
    }

    private Long affixLevel2affixId(Long id, Integer affixLevel){
        return (id == null || affixLevel == null) ? null : (id * 10 + affixLevel);
    }
}
