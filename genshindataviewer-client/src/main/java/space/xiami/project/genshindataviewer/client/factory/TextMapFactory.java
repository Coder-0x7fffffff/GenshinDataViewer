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

    private static final String filePathPrefix = "TextMap";
    private static final String filePathSuffix = ".json";

    // Language, TextMap
    private static final Map<Byte, TextMap> language2TextMap = new HashMap<>();
    private static final Set<String> relatedFilePath = new HashSet<>();
    private static final Map<String, Byte> path2lang = new HashMap<>();

    static {
        LanguageEnum[] languageEnums = LanguageEnum.values();
        for(LanguageEnum languageEnum : languageEnums){
            String filePath = PathUtil.getTextMapDirectory() + filePathPrefix + languageEnum.getDesc() + filePathSuffix;
            relatedFilePath.add(filePath);
            path2lang.put(filePath, languageEnum.getCode());
        }
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        Map<Long, String> hashTextMap = new HashMap<>();
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
                hashTextMap.put(Long.parseLong(entry.getKey()), (String) entry.getValue());
            }
        } catch (FileNotFoundException e) {
            log.error("File not found", e);
        } catch (IOException e) {
            log.error("Read file error", e);
        }
        TextMap textMap = new TextMap();
        Byte language = path2lang.get(path);
        textMap.setLanguage(language);
        textMap.setFilePath(path);
        textMap.setHashTextMap(hashTextMap);
        language2TextMap.put(language, textMap);
    }

    public String checkError(Byte language, Long hash){
        if(language2TextMap.containsKey(language)){
            return language2TextMap.get(language).getText(hash) == null ? ERROR_NO_SUCH_ID : null;
        }
        return ERROR_NO_SUCH_LANG;
    }

    public String getText(Byte language, Long hash){
        if(hash == null || language == null){
            return null;
        }
        if(language2TextMap.containsKey(language)){
            return language2TextMap.get(language).getText(hash);
        }
        return null;
    }

    public static class TextMap{

        private Byte language;
        private String filePath;
        private Map<Long, String> hashTextMap;

        public String getText(Long id){
            if(hashTextMap.containsKey(id)){
                return hashTextMap.get(id);
            }
            return null;
        }

        public Byte getLanguage() {
            return language;
        }

        public void setLanguage(Byte language) {
            this.language = language;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public Map<Long, String> getHashTextMap() {
            return hashTextMap;
        }

        public void setHashTextMap(Map<Long, String> hashTextMap) {
            this.hashTextMap = hashTextMap;
        }
    }
}
