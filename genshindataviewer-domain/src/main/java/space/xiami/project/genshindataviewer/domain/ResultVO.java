package space.xiami.project.genshindataviewer.domain;

public class ResultVO {

    private Integer status;
    private Object modal;
    private String error;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getModal() {
        return modal;
    }

    public void setModal(Object modal) {
        this.modal = modal;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static ResultVO buildSuccessResult(Object modal){
        ResultVO result = new ResultVO();
        result.setStatus(200);
        result.setModal(modal);
        return result;
    }

    public static ResultVO buildErrorResult(String error){
        ResultVO result = new ResultVO();
        result.setStatus(200);
        result.setError(error);
        return result;
    }
}
