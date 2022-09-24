package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.common.enums.CurveEnum;
import space.xiami.project.genshindataviewer.domain.json.CurveExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import java.io.IOException;
import java.util.*;

/**
 * @author Xiami
 */
@Component
public class CurveFactory extends AbstractFileBaseFactory{

    public static final Logger log = LoggerFactory.getLogger(CurveFactory.class);

    public static final String weaponCurveExcelConfigDataFile = "WeaponCurveExcelConfigData.json";
    public static final String avatarCurveExcelConfigDataFile = "AvatarCurveExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    private static final Map<String, Byte> path2type = new HashMap<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + weaponCurveExcelConfigDataFile);
        path2type.put(PathUtil.getExcelBinOutputDirectory() + weaponCurveExcelConfigDataFile, CurveEnum.WEAPON.getCode());
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + avatarCurveExcelConfigDataFile);
        path2type.put(PathUtil.getExcelBinOutputDirectory() + avatarCurveExcelConfigDataFile, CurveEnum.AVATAR.getCode());
    }

    /**
     * curveType -> level -> DO
     */
    private final Map<Byte, Map<Integer, CurveExcelConfigData>> curveExcelConfigDataMap = new HashMap<>();
    /**
     * curveType -> level -> type -> DO
     */
    private final Map<Byte, Map<Integer, Map<String, CurveExcelConfigData.CurveInfo>>> curveInfoMap = new HashMap<>();


    @Override
    protected void load(String path) {
        try{
            if(!relatedFilePath.contains(path)){
                return;
            }
            List<CurveExcelConfigData> array = readJsonArray(path, CurveExcelConfigData.class);
            Byte type = path2type.get(path);
            for(CurveExcelConfigData data : array){
                if(curveExcelConfigDataMap
                        .computeIfAbsent(type, v -> new HashMap<>())
                        .containsKey(data.getLevel()))
                {
                    log.warn("Ignore same curveExcelConfigData level={}", data.getLevel());
                    continue;
                }
                curveExcelConfigDataMap
                        .computeIfAbsent(type, v -> new HashMap<>())
                        .put(data.getLevel(), data);
                //curveInfo
                Map<String, CurveExcelConfigData.CurveInfo> innerMap = curveInfoMap
                        .computeIfAbsent(type, v -> new HashMap<>())
                        .computeIfAbsent(data.getLevel(), v -> new HashMap<>());
                List<CurveExcelConfigData.CurveInfo> curveInfos = data.getCurveInfos();
                curveInfos.forEach(curveInfo -> {
                    if(innerMap.containsKey(curveInfo.getType())){
                        log.warn("Ignore same curveInfo type={}", curveInfo.getType());
                        return;
                    }
                    innerMap.put(curveInfo.getType(), curveInfo);
                });
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        Byte type = path2type.get(path);
        curveExcelConfigDataMap.computeIfAbsent(type, v -> new HashMap<>()).clear();
        curveInfoMap.computeIfAbsent(type, v -> new HashMap<>()).clear();
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    public Map<String, CurveExcelConfigData.CurveInfo> getCurveInfoMap(Byte curveType, Integer level){
        if(!curveInfoMap.get(curveType).containsKey(level)){
            return null;
        }
        return curveInfoMap.get(curveType).get(level);
    }

    public CurveExcelConfigData.CurveInfo getCurveInfo(Byte curveType, Integer level, String type){
        Map<String, CurveExcelConfigData.CurveInfo> curveMap = getCurveInfoMap(curveType, level);
        if(curveMap == null){
            return null;
        }
        return curveMap.get(type);
    }
}
