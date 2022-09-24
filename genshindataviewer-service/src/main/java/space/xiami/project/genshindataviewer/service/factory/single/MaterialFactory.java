package space.xiami.project.genshindataviewer.service.factory.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.service.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.service.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.MaterialExcelConfigData;

import javax.annotation.Resource;

/**
 * @author Xiami
 */
@Component
public class MaterialFactory extends AbstractSingleFactory<MaterialExcelConfigData, Long> {

    public static final Logger log = LoggerFactory.getLogger(MaterialFactory.class);

    public static final String materialExcelConfigDataFile = "MaterialExcelConfigData.json";

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public String relatedFilePath() {
        return PathUtil.getExcelBinOutputDirectory() + materialExcelConfigDataFile;
    }

    @Override
    public Long getGroupValue(MaterialExcelConfigData materialExcelConfigData) {
        return materialExcelConfigData.getId();
    }

    public String getMaterialName(Long id){
        return getMaterialName((byte) 0, id);
    }

    public String getMaterialName(Byte lang, Long id){
        MaterialExcelConfigData data = get(id);
        if(data != null){
            return textMapFactory.getText(lang, data.getNameTextMapHash());
        }
        return null;
    }

}
