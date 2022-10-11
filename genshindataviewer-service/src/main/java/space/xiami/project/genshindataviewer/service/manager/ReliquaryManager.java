package space.xiami.project.genshindataviewer.service.manager;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import space.xiami.project.genshindataviewer.domain.json.DocumentExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.ReliquaryExcelConfigData;
import space.xiami.project.genshindataviewer.domain.json.ReliquarySetExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.EquipAffix;
import space.xiami.project.genshindataviewer.domain.model.Reliquary;
import space.xiami.project.genshindataviewer.domain.model.ReliquarySet;
import space.xiami.project.genshindataviewer.service.factory.ReliquaryFactory;
import space.xiami.project.genshindataviewer.service.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.service.factory.single.DocumentFactory;
import space.xiami.project.genshindataviewer.service.factory.single.LocalizationFactory;
import space.xiami.project.genshindataviewer.service.factory.single.ManualTextMapFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Xiami
 */
@Component
public class ReliquaryManager {

    @Resource
    private ReliquaryFactory reliquaryFactory;

    @Resource
    private TextMapFactory textMapFactory;

    @Resource
    private ManualTextMapFactory manualTextMapFactory;

    @Resource
    private DocumentFactory documentFactory;

    @Resource
    private LocalizationFactory localizationFactory;

    @Resource
    private EquipAffixManager equipAffixManager;


    public Map<String, Long> getReliquarySetIdMap(Byte language){
        return reliquaryFactory.getName2SetId(language);
    }

    public Long getReliquarySetId(Byte lang, String name){
        return reliquaryFactory.getSetIdByName(lang, name);
    }

    public Map<String, Long> getReliquaryIdMap(Byte language){
        return reliquaryFactory.getName2Id(language);
    }

    public Long getReliquaryId(Byte lang, String name){
        return reliquaryFactory.getIdByName(lang, name);
    }

    public ReliquarySet getReliquarySet(Byte lang, String name){
        Long id = getReliquarySetId(lang, name);
        if(id == null){
            return null;
        }
        return getReliquarySet(lang, id);
    }

    public ReliquarySet getReliquarySet(Byte lang, Long setId){
        ReliquarySetExcelConfigData excelConfigData = reliquaryFactory.getReliquarySet(setId);
        if(excelConfigData == null){
            return null;
        }
        ReliquarySet reliquarySet = new ReliquarySet();
        reliquarySet.setSetId(excelConfigData.getSetId());
        reliquarySet.setReliquaries(excelConfigData.getContainsList().stream().map(id -> getReliquary(lang, id)).collect(Collectors.toList()));
        // groupEquipAffix
        Long equipAffixId = excelConfigData.getEquipAffixId();
        List<Integer> needNumList = excelConfigData.getSetNeedNum();
        Map<Integer, EquipAffix> groupEquipAffixMap = new ListOrderedMap<>();
        for(int idx=0; idx<needNumList.size(); idx++){
            // idx套装数下标对应EquipAffix的等级
            EquipAffix equipAffix = equipAffixManager.getByIdLevel(equipAffixId, idx, lang);
            if(equipAffix == null){
                continue;
            }
            groupEquipAffixMap.put(needNumList.get(idx), equipAffix);
        }
        reliquarySet.setGroupEquipAffix(groupEquipAffixMap.size() > 0 ? groupEquipAffixMap : null);
        return reliquarySet;
    }

    public Reliquary getReliquary(Byte lang, String name){
        Long id = getReliquaryId(lang, name);
        if(id == null){
            return null;
        }
        return getReliquary(lang, id);
    }
    
    public Reliquary getReliquary(Byte lang, Long id){
        ReliquaryExcelConfigData excelConfigData = reliquaryFactory.getReliquary(id);
        Reliquary reliquary = new Reliquary();
        reliquary.setId(excelConfigData.getId());
        reliquary.setSetId(excelConfigData.getSetId());
        reliquary.setName(textMapFactory.getText(lang, excelConfigData.getNameTextMapHash()));
        reliquary.setDesc(textMapFactory.getText(lang, excelConfigData.getDescTextMapHash()));
        reliquary.setEquipType(manualTextMapFactory.getText(lang, excelConfigData.getEquipType()));
        List<Integer> addPropLevels = excelConfigData.getAddPropLevels();
        if(!CollectionUtils.isEmpty(addPropLevels)){
            reliquary.setAddPropLevels(addPropLevels.stream()
                    .map(level -> level - 1)
                    .collect(Collectors.toList())
            );
        }
        DocumentExcelConfigData document = documentFactory.get(excelConfigData.getStoryId());
        if(document != null){
            String story = localizationFactory.getText(lang, document.getContentLocalizedId());
            reliquary.setStory(story);
        }
        return reliquary;
    }
}
