package space.xiami.project.genshindataviewer.web.domain;

public class ResultVO<T> {

    private Integer status;
    private T modal;
    private String error;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getModal() {
        return modal;
    }

    public void setModal(T modal) {
        this.modal = modal;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static <T> ResultVO<T> buildSuccessResult(T modal){
        ResultVO<T> result = new ResultVO<>();
        result.setStatus(200);
        result.setModal(modal);
        return result;
    }

    public static ResultVO<String> buildErrorResult(String error){
        ResultVO<String> result = new ResultVO<>();
        result.setStatus(200);
        result.setError(error);
        return result;
    }
}
