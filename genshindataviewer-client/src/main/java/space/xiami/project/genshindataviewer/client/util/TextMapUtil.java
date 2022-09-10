package space.xiami.project.genshindataviewer.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import space.xiami.project.genshindataviewer.common.enums.LanguageEnum;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TextMapUtil {

    public static final String ERROR_NO_SUCH_LANG = "NO_SUCH_LANG";
    public static final String ERROR_NO_SUCH_ID = "NO_SUCH_ID";

    private static final Logger log = LoggerFactory.getLogger(TextMapUtil.class);

    private static final String filePathPrefix = "GenshinData/TextMap/TextMap";
    private static final String filePathSuffix = ".json";

    // Language, TextMap
    private static final Map<Byte, TextMap> language2TextMap = new HashMap<>();

    static{
        reload();
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

    public static void reload(){
        LanguageEnum[] languageEnums = LanguageEnum.values();
        for(LanguageEnum languageEnum : languageEnums){
            String filePath = filePathPrefix + languageEnum.getDesc() + filePathSuffix;
            Map<Long, String> idTextMap = new HashMap<>();
            try{
                File file = new ClassPathResource(filePath).getFile();
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
            textMap.setLanguage(languageEnum);
            textMap.setFilePath(filePath);
            textMap.setIdTextMap(idTextMap);
            language2TextMap.put(languageEnum.getCode(), textMap);
        }
    }

    public static String errorMsg(Byte language, Long id){
        if(language2TextMap.containsKey(language)){
            return null;
        }
        return ERROR_NO_SUCH_LANG;
    }

    public static String errorMsg(LanguageEnum languageEnum, Long id){
        return errorMsg(languageEnum.getCode(), id);
    }

    public static String getText(Byte language, Long id){
        if(id == null || language == null){
            return null;
        }
        if(language2TextMap.containsKey(language)){
            return language2TextMap.get(language).getText(id);
        }
        return null;
    }

    public static String getText(LanguageEnum languageEnum, Long id){
        return getText(languageEnum.getCode(), id);
    }
}
