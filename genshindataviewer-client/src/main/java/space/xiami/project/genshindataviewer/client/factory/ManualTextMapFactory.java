package space.xiami.project.genshindataviewer.client.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.ManualTextMapConfigData;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Component
public class ManualTextMapFactory extends AbstractFileBaseFactory{

    public static final Logger log = LoggerFactory.getLogger(ManualTextMapFactory.class);

    public static final String manualTextMapConfigDataFile = "ManualTextMapConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + manualTextMapConfigDataFile);
    }

    private final Map<String, ManualTextMapConfigData> manualTextMapConfigDataMap = new HashMap<>();

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        try{
            if(path.endsWith("/"+ manualTextMapConfigDataFile)) {
                List<ManualTextMapConfigData> array = readJsonArray(path, ManualTextMapConfigData.class);
                for (ManualTextMapConfigData data : array) {
                    if(manualTextMapConfigDataMap.containsKey(data.getTextMapId())){
                        log.warn("Ignore same textMapId={}", data.getTextMapId());
                        continue;
                    }
                    manualTextMapConfigDataMap.put(data.getTextMapId(), data);
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        if(path.endsWith("/"+ manualTextMapConfigDataFile)) {
            manualTextMapConfigDataMap.clear();
        }
    }

    public String getText(Byte lang, String textMapId){
        ManualTextMapConfigData data = manualTextMapConfigDataMap.get(textMapId);
        if(data == null){
            return null;
        }
        return textMapFactory.getText(lang, data.getTextMapContentTextMapHash());
    }
}
