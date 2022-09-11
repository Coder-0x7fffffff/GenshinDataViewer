package space.xiami.project.genshindataviewer.client.factory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.util.PathUtil;
import space.xiami.project.genshindataviewer.common.enums.LanguageEnum;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class TextMapFactory extends AbstractFileBaseFactory {

    private static final Logger log = LoggerFactory.getLogger(TextMapFactory.class);

    public static final String ERROR_NO_SUCH_LANG = "NO_SUCH_LANG";
    public static final String ERROR_NO_SUCH_ID = "NO_SUCH_ID";

    private static final String filePathPrefix = "/TextMap";
    private static final String filePathSuffix = ".json";

    // Language, TextMap
    private static final Map<Byte, TextMap> language2TextMap = new HashMap<>();
    private static final Set<String> relatedFilePath = new HashSet<>();
    private static final Map<String, LanguageEnum> path2lang = new HashMap<>();

    static {
        LanguageEnum[] languageEnums = LanguageEnum.values();
        for(LanguageEnum languageEnum : languageEnums){
            String filePath = PathUtil.getTextMapDirectory() + filePathPrefix + languageEnum.getDesc() + filePathSuffix;
            relatedFilePath.add(filePath);
            path2lang.put(filePath, languageEnum);
        }
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        Map<Long, String> idTextMap = new HashMap<>();
        try{
            File file = new File(path);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int len;
            while((len = fis.read(buffer)) > 0){
                os.write(buffer, 0, len);
            }
            JSONObject jsonObject = JSON.parseObject(os.toByteArray());
            for(Map.Entry<String, Object> entry : jsonObject.entrySet()){
                if(!(entry.getValue() instanceof String)){
                    log.warn("TextMap value is not pojo string, value="+JSON.toJSONString(entry.getValue()));
                    continue;
                }
                idTextMap.put(Long.parseLong(entry.getKey()), (String) entry.getValue());
            }
        } catch (FileNotFoundException e) {
            log.error("File not found", e);
        } catch (IOException e) {
            log.error("Read file error", e);
        }
        TextMap textMap = new TextMap();
        LanguageEnum languageEnum = path2lang.get(path);
        textMap.setLanguage(languageEnum);
        textMap.setFilePath(path);
        textMap.setIdTextMap(idTextMap);
        language2TextMap.put(languageEnum.getCode(), textMap);
    }

    public String checkError(Byte language, Long id){
        if(language2TextMap.containsKey(language)){
            return language2TextMap.get(language).getText(id) == null ? ERROR_NO_SUCH_ID : null;
        }
        return ERROR_NO_SUCH_LANG;
    }

    public String checkError(LanguageEnum languageEnum, Long id){
        return checkError(languageEnum.getCode(), id);
    }

    public String getText(Byte language, Long id){
        if(id == null || language == null){
            return null;
        }
        if(language2TextMap.containsKey(language)){
            return language2TextMap.get(language).getText(id);
        }
        return null;
    }

    public String getText(LanguageEnum languageEnum, Long id){
        return getText(languageEnum.getCode(), id);
    }

    public static class TextMap{

        private LanguageEnum languageEnum;
        private String filePath;
        private Map<Long, String> idTextMap;

        public String getText(Long id){
            if(idTextMap.containsKey(id)){
                return idTextMap.get(id);
            }
            return null;
        }

        public LanguageEnum getLanguage() {
            return languageEnum;
        }

        public void setLanguage(LanguageEnum languageEnum) {
            this.languageEnum = languageEnum;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public Map<Long, String> getIdTextMap() {
            return idTextMap;
        }

        public void setIdTextMap(Map<Long, String> idTextMap) {
            this.idTextMap = idTextMap;
        }
    }
}
