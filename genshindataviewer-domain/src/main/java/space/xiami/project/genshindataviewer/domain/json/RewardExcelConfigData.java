package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class RewardExcelConfigData {
    private Long rewardId;
    private List<RewardItemData> rewardItemList;

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    public List<RewardItemData> getRewardItemList() {
        return rewardItemList;
    }

    public void setRewardItemList(List<RewardItemData> rewardItemDataList) {
        this.rewardItemList = rewardItemDataList;
    }

    public static class RewardItemData {
        private Long itemId;
        private Integer itemCount;

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public Integer getItemCount() {
            return itemCount;
        }

        public void setItemCount(Integer itemCount) {
            this.itemCount = itemCount;
        }
    }
}
