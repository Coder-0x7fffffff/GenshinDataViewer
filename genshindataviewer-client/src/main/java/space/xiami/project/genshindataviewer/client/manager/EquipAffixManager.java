package space.xiami.project.genshindataviewer.client.manager;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.converter.EquipAffixConverter;
import space.xiami.project.genshindataviewer.client.factory.EquipAffixFactory;
import space.xiami.project.genshindataviewer.client.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.common.enums.LanguageEnum;
import space.xiami.project.genshindataviewer.domain.json.EquipAffixExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.EquipAffix;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class EquipAffixManager {

    @Resource
    private TextMapFactory textMapFactory;

    @Resource
    private EquipAffixFactory equipAffixFactory;

    @Resource
    private EquipAffixConverter equipAffixConverter;

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
            ret.put(k, equipAffixConverter.convert(v, language));
        });
        return ret;
    }

    public EquipAffix getByIdRankLevel(Long id, Integer rankLevel){
        return getByIdRankLevel(id, rankLevel, (byte) 0);
    }

    public EquipAffix getByIdRankLevel(Long id, Integer rankLevel, Byte language){
        EquipAffixExcelConfigData oriRet = equipAffixFactory.getByIdAffixId(id, equipAffixConverter.rankLevel2affixId(id, rankLevel));
        return oriRet == null ? null : equipAffixConverter.convert(oriRet, language);
    }
}
