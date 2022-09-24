package space.xiami.project.genshindataviewer.service.util;

import space.xiami.project.genshindataviewer.domain.json.AvatarExcelConfigData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiami
 */
public class AvatarUtil {

    private static final Map<String, Method> growCurveType2Getter = new HashMap<>();

    static {
        try{
            growCurveType2Getter.put("FIGHT_PROP_BASE_HP", AvatarExcelConfigData.class.getMethod("getHpBase"));
            growCurveType2Getter.put("FIGHT_PROP_BASE_ATTACK", AvatarExcelConfigData.class.getMethod("getAttackBase"));
            growCurveType2Getter.put("FIGHT_PROP_BASE_DEFENSE", AvatarExcelConfigData.class.getMethod("getDefenseBase"));
        }catch (Exception ignore) {}
    }

    public static Double getInitValueByGrowCurveType(AvatarExcelConfigData excelConfigData, String growCurveType) throws InvocationTargetException, IllegalAccessException {
        Method getter = growCurveType2Getter.get(growCurveType);
        if(getter != null){
            Double value = (Double) getter.invoke(excelConfigData);
            if(value == null){
                return 0.0;
            }
            return value;
        }
        return null;
    }


}
