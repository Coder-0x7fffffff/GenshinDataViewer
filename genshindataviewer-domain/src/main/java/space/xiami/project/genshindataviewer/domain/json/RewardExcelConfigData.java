package space.xiami.project.genshindataviewer.domain.json;

import java.util.List;

/**
 * @author Xiami
 */
public class RewardExcelConfigData {
    private Long rewardId;
    private Integer hcoin;
    private Integer scoin;
    private Integer playerExp;
    private List<RewardItemData> rewardItemList;

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    public Integer getHcoin() {
        return hcoin;
    }

    public void setHcoin(Integer hcoin) {
        this.hcoin = hcoin;
    }

    public Integer getScoin() {
        return scoin;
    }

    public void setScoin(Integer scoin) {
        this.scoin = scoin;
    }

    public Integer getPlayerExp() {
        return playerExp;
    }

    public void setPlayerExp(Integer playerExp) {
        this.playerExp = playerExp;
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
