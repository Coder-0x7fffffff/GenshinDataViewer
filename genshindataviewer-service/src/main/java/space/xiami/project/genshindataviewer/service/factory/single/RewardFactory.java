package space.xiami.project.genshindataviewer.service.factory.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.json.RewardExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

/**
 * @author Xiami
 */
@Component
public class RewardFactory extends AbstractSingleFactory<RewardExcelConfigData, Long> {

    public static final Logger log = LoggerFactory.getLogger(RewardFactory.class);

    public static final String rewardExcelConfigDataFile = "RewardExcelConfigData.json";

    @Override
    public String relatedFilePath() {
        return PathUtil.getExcelBinOutputDirectory() + rewardExcelConfigDataFile;
    }

    @Override
    public Long getGroupValue(RewardExcelConfigData rewardExcelConfigData) {
        return rewardExcelConfigData.getRewardId();
    }
}
