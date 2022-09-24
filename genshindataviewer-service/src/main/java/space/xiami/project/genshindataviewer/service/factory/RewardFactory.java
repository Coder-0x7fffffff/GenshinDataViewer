package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.json.RewardExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Xiami
 */
@Component
public class RewardFactory extends AbstractFileBaseFactory{

    public static final Logger log = LoggerFactory.getLogger(RewardFactory.class);

    public static final String rewardExcelConfigDataFile = "RewardExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + rewardExcelConfigDataFile);
    }

    private final Map<Long, RewardExcelConfigData> rewardExcelConfigDataMap = new HashMap<>();

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        try{
            if(path.endsWith(SPLASH + rewardExcelConfigDataFile)) {
                List<RewardExcelConfigData> array = readJsonArray(path, RewardExcelConfigData.class);
                for (RewardExcelConfigData data : array) {
                    if(rewardExcelConfigDataMap.containsKey(data.getRewardId())){
                        log.warn("Ignore same rewardId={}", data.getRewardId());
                        continue;
                    }
                    rewardExcelConfigDataMap.put(data.getRewardId(), data);
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        if(path.endsWith(SPLASH + rewardExcelConfigDataFile)) {
            rewardExcelConfigDataMap.clear();
        }
    }

    public RewardExcelConfigData getReward(Long rewardId){
        if(rewardExcelConfigDataMap.containsKey(rewardId)){
            return rewardExcelConfigDataMap.get(rewardId);
        }
        return null;
    }
}
