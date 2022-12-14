package space.xiami.project.genshindataviewer.service.factory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshincommon.enums.LanguageEnum;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xiami
 */
@Component
public class TextMapFactory extends AbstractFileBaseFactory {

    private static final Logger log = LoggerFactory.getLogger(TextMapFactory.class);

    private static final Pattern paramPattern = Pattern.compile("(\\{.*?})");
    private static final Pattern arrayParamPattern = Pattern.compile("\\{param(.*):(.*)}");

    private static final String filePathPrefix = "TextMap";
    private static final String filePathSuffix = ".json";
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

    // Language, TextMap
    private static final Map<Byte, TextMap> language2TextMap = new HashMap<>();

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
                Long id = Long.parseLong(entry.getKey());
                if(hashTextMap.containsKey(id)){
                    log.warn("Ignore same textMap id={}", id);
                    continue;
                }
                if(!(entry.getValue() instanceof String)){
                    log.warn("TextMap value is not pojo string, value="+JSON.toJSONString(entry.getValue()));
                    continue;
                }
                String text = (String) entry.getValue();
                hashTextMap.put(id, text);
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
        if(language2TextMap.containsKey(language)){
            log.warn("Already exists lang textMap, lang: {}", language);
            return;
        }
        language2TextMap.put(language, textMap);
    }

    @Override
    protected void clear(String path) {
        Byte language = path2lang.get(path);
        if(language != null){
            language2TextMap.remove(language);
        }
    }

    public String getText(Byte language, Long hash){
        return getText(language, hash, null);
    }

    public String getText(Byte language, Long hash, List<Double> params){
        if(hash == null || language == null){
            return null;
        }
        readLock();
        String text = null;
        if(language2TextMap.containsKey(language)){
            text = language2TextMap.get(language).getText(hash);
            if(text != null && params != null){
                text = replaceParam(text, params);
            }
        }
        readUnlock();
        return text;
    }

    public static String replaceParam(String text, List<Double> params){
        Matcher paramMatcher = paramPattern.matcher(text);
        String result = text;
        while (paramMatcher.find()){
            for(int i=1; i<=paramMatcher.groupCount(); i++){
                String paramText = paramMatcher.group(i);
                Matcher matcher;
                if((matcher = arrayParamPattern.matcher(paramText)).matches()){
                    int index = Integer.parseInt(matcher.group(1)) - 1;
                    // TODO ?????? pat????????? String type = matcher.group(2);
                    if(params == null || index >= params.size()){
                        log.warn("Out bound param index, param={}, index={}", paramText, index);
                        continue;
                    }
                    result = result.replace(paramText, String.valueOf(params.get(index)));
                }else{
                    log.warn("Unknown param format, param={}", paramText);
                }
            }
        }
        return result;
    }

    public static class TextMap{

        private Byte language;
        private String filePath;
        private Map<Long, String> hashTextMap;

        public String getText(Long id){
            return hashTextMap.get(id);
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
