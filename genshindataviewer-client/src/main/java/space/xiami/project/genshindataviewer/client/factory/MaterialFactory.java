package space.xiami.project.genshindataviewer.client.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.util.PathUtil;
import space.xiami.project.genshindataviewer.domain.json.MaterialExcelConfigData;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Component
public class MaterialFactory extends AbstractFileBaseFactory{

    public static final Logger log = LoggerFactory.getLogger(MaterialFactory.class);

    public static final String materialExcelConfigDataFile = "MaterialExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + materialExcelConfigDataFile);
    }

    private final Map<Long, MaterialExcelConfigData> materialExcelConfigDataMap = new HashMap<>();

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        try{
            if(path.endsWith("/"+ materialExcelConfigDataFile)) {
                List<MaterialExcelConfigData> array = readJsonArray(path, MaterialExcelConfigData.class);
                for (MaterialExcelConfigData data : array) {
                    if(materialExcelConfigDataMap.containsKey(data.getId())){
                        log.warn("Ignore same id={}", data.getId());
                        continue;
                    }
                    materialExcelConfigDataMap.put(data.getId(), data);
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        if(path.endsWith("/"+ materialExcelConfigDataFile)) {
            materialExcelConfigDataMap.clear();
        }
    }

    public String getMaterialName(Long id){
        return getMaterialName((byte) 0, id);
    }

    public String getMaterialName(Byte lang, Long id){
        if(materialExcelConfigDataMap.containsKey(id)){
            return textMapFactory.getText(lang, materialExcelConfigDataMap.get(id).getNameTextMapHash());
        }
        return null;
    }

}
