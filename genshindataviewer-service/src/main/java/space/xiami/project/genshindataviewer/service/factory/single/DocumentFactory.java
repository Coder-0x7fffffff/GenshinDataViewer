package space.xiami.project.genshindataviewer.service.factory.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.json.DocumentExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

/**
 * @author Xiami
 */
@Component
public class DocumentFactory extends AbstractSingleFactory<DocumentExcelConfigData, Long> {

    public static final Logger log = LoggerFactory.getLogger(DocumentFactory.class);

    public static final String documentExcelConfigDataFile = "DocumentExcelConfigData.json";

    @Override
    public String relatedFilePath() {
        return PathUtil.getExcelBinOutputDirectory() + documentExcelConfigDataFile;
    }

    @Override
    public Long getGroupValue(DocumentExcelConfigData documentExcelConfigData) {
        return documentExcelConfigData.getId();
    }
}
