package space.xiami.project.genshindataviewer.client.converter;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.common.enums.LanguageEnum;
import space.xiami.project.genshindataviewer.domain.json.EquipAffixExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.EquipAffix;

import javax.annotation.Resource;

@Component
public class EquipAffixConverter {

    @Resource
    private TextMapFactory textMapFactory;

    public EquipAffix convert(EquipAffixExcelConfigData data, Byte language){
        EquipAffix result = new EquipAffix();
        result.setId(data.getId());
        result.setRankLevel(affixId2RankLevel(data.getAffixId()));
        result.setName(textMapFactory.getText(language, data.getNameTextMapHash()));
        result.setDesc(textMapFactory.getText(language, data.getDescTextMapHash()));
        result.setAddProps(data.getAddProps());
        result.setParamList(data.getParamList());
        return result;
    }

    public Integer affixId2RankLevel(Long affixId){
        return affixId == null ? null : ((int) (affixId % 10));
    }

    public Long rankLevel2affixId(Long id, Integer rankLevel){
        return (id == null || rankLevel == null) ? null : (id * 10 + rankLevel);
    }
}
