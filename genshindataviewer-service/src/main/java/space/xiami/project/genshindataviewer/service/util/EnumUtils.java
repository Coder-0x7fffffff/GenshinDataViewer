package space.xiami.project.genshindataviewer.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.xiami.project.genshindataviewer.common.enums.LanguageEnum;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiami
 */
public class EnumUtils {

    private static final Logger log = LoggerFactory.getLogger(EnumUtils.class);

    //Enum -> Code -> Desc
    public static Map<String, Map<Byte, String>> enumValueDesc = new HashMap<>();

    static {
        // 对外暴露的enum初始化
        loadEnumMap(LanguageEnum.class);
    }

    private static void loadEnumMap(Class<?> enumClass){
        if(enumValueDesc.containsKey(enumClass.getSimpleName())){
            return;
        }
        try{
            Object[] values = enumClass.getEnumConstants();
            Method getCode = enumClass.getMethod("getCode");
            Method getDesc = enumClass.getMethod("getDesc");
            Map<Byte, String> result = new HashMap<>();
            for(Object e : values){
                result.put((Byte) getCode.invoke(e), (String) getDesc.invoke(e));
            }
            enumValueDesc.put(enumClass.getSimpleName(), result);
        }catch (Exception e){
            log.error("loadEnumMap error", e);
        }
    }

    public static Map<Byte, String> getEnum(Class<?> enumClass){
        return getEnum(enumClass.getSimpleName());
    }

    public static Map<Byte, String> getEnum(String name){
        return enumValueDesc.get(name);
    }

    public static Map<String, Map<Byte, String>> getAllEnum(){
        return enumValueDesc;
    }
}
