package space.xiami.project.genshindataviewer.service.util;

import space.xiami.project.genshindataviewer.domain.json.CurveExcelConfigData;

/**
 * @author Xiami
 */
public class CurveUtil {

    public static Double calculateCurveInfo(Double initValue, CurveExcelConfigData.CurveInfo curveInfo, Double promoteValue){
        double value = promoteValue;
        if(initValue != null && curveInfo != null){
            String curveArith = curveInfo.getArith();
            Double curveValue = curveInfo.getValue();
            if("ARITH_MULTI".equals(curveArith)){
                value += initValue * curveValue;
            }
        }
        return value;
    }
}
