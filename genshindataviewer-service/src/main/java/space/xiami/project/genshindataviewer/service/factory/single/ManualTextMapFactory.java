package space.xiami.project.genshindataviewer.service.factory.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.service.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.service.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.ManualTextMapConfigData;

import javax.annotation.Resource;

/**
 * @author Xiami
 */
@Component
public class ManualTextMapFactory extends AbstractSingleFactory<ManualTextMapConfigData, String> {

    public static final Logger log = LoggerFactory.getLogger(ManualTextMapFactory.class);

    public static final String manualTextMapConfigDataFile = "ManualTextMapConfigData.json";

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public String relatedFilePath() {
        return PathUtil.getExcelBinOutputDirectory() + manualTextMapConfigDataFile;
    }

    @Override
    public String getGroupValue(ManualTextMapConfigData manualTextMapConfigData) {
        return manualTextMapConfigData.getTextMapId();
    }

    public String getText(Byte lang, String textMapId){
        ManualTextMapConfigData data = get(textMapId);
        if(data == null){
            return null;
        }
        return textMapFactory.getText(lang, data.getTextMapContentTextMapHash());
    }
}
