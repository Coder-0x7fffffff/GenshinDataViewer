package space.xiami.project.genshindataviewer.service.factory.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshincommon.enums.LanguageEnum;
import space.xiami.project.genshindataviewer.domain.json.LocalizationExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.FileUtil;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiami
 */
@Component
public class LocalizationFactory extends AbstractSingleFactory<LocalizationExcelConfigData, Long> {

    public static final Logger log = LoggerFactory.getLogger(LocalizationFactory.class);

    public static final String localizationExcelConfigDataFile = "LocalizationExcelConfigData.json";

    public static final String readableDir = PathUtil.getReadableDirectory();

    public static final String removePre = "ART/UI/Readable";

    public static final String suffix = ".txt";

    public static final Map<Byte, Method> lang2Method = new HashMap<>();

    static {
        registerLanguage(LanguageEnum.CHS.getCode(), "getScPath");
        registerLanguage(LanguageEnum.CHT.getCode(), "getTcPath");
        registerLanguage(LanguageEnum.DE.getCode(), "getDePath");
        registerLanguage(LanguageEnum.EN.getCode(), "getEnPath");
        registerLanguage(LanguageEnum.ES.getCode(), "getEsPath");
        registerLanguage(LanguageEnum.FR.getCode(), "getFrPath");
        registerLanguage(LanguageEnum.ID.getCode(), "getIdPath");
        registerLanguage(LanguageEnum.JP.getCode(), "getJpPath");
        registerLanguage(LanguageEnum.KR.getCode(), "getKrPath");
        registerLanguage(LanguageEnum.PT.getCode(), "getPtPath");
        registerLanguage(LanguageEnum.RU.getCode(), "getRuPath");
        registerLanguage(LanguageEnum.TH.getCode(), "getThPath");
        registerLanguage(LanguageEnum.VI.getCode(), "getViPath");
    }

    public static void registerLanguage(Byte lang, String methodName){
        try{
            lang2Method.put(
                    lang,
                    LocalizationExcelConfigData.class.getMethod(methodName)
            );
        }catch (Exception e){
            log.error("Register language error", e);
        }

    }

    @Override
    public String relatedFilePath() {
        return PathUtil.getExcelBinOutputDirectory() + localizationExcelConfigDataFile;
    }

    @Override
    public Long getGroupValue(LocalizationExcelConfigData localizationExcelConfigData) {
        return localizationExcelConfigData.getId();
    }

    public String getText(Byte lang, Long id){
        LocalizationExcelConfigData data = get(id);
        Method method = lang2Method.get(lang);
        if(method != null){
            try{
                return new String(FileUtil.readFileOnce(convertPath((String) method.invoke(data))));
            }catch (Exception e){
                log.error("Read file error", e);
            }
        }
        return null;
    }

    private String convertPath(String path){
        if(path.startsWith(removePre)){
            return readableDir + path.substring(removePre.length()) + suffix;
        }else{
            log.error("Unknown path, path={}", path);
        }
        return null;
    }
}
