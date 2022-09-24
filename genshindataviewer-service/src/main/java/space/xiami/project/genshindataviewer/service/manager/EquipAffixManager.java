package space.xiami.project.genshindataviewer.service.manager;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.model.AddProperty;
import space.xiami.project.genshindataviewer.service.factory.EquipAffixFactory;
import space.xiami.project.genshindataviewer.service.factory.single.ManualTextMapFactory;
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

    public EquipAffix getByIdLevel(Long id, Integer level){
        return getByIdLevel(id, level, (byte) 0);
    }

    public EquipAffix getByIdLevel(Long id, Integer level, Byte language){
        EquipAffixExcelConfigData oriRet = equipAffixFactory.getByIdAffixId(id, level2affixId(id, level));
        return oriRet == null ? null : convert(oriRet, language);
    }

    private EquipAffix convert(EquipAffixExcelConfigData data, Byte language){
        EquipAffix result = new EquipAffix();
        result.setAffixId(data.getAffixId());
        result.setId(data.getId());
        result.setLevel(data.getLevel());
        result.setName(textMapFactory.getText(language, data.getNameTextMapHash()));
        result.setDesc(textMapFactory.getText(language, data.getDescTextMapHash()));
        result.setOpenConfig(data.getOpenConfig());
        // 添加非空属性加成
        result.setAddProperties(data.getAddProps().stream().filter(addProp -> addProp.getPropType() != null).map(addProp -> {
            AddProperty addProperty = new AddProperty();
            addProperty.setPropType(manualTextMapFactory.getText(language, addProp.getPropType()));
            addProperty.setValue(addProp.getValue());
            return addProperty;
        }).collect(Collectors.toList()));
        result.setParamList(data.getParamList());
        return result;
    }

    private Integer affixId2Level(Long affixId){
        return affixId == null ? null : ((int) (affixId % 10));
    }

    private Long level2affixId(Long id, Integer level){
        return (id == null || level == null) ? null : (id * 10 + level);
    }
}
