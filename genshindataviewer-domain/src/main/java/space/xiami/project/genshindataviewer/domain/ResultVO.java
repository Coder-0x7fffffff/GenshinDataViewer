package space.xiami.project.genshindataviewer.domain;

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

    public static <R> ResultVO<R> buildSuccessResult(R modal){
        ResultVO<R> result = new ResultVO<R>();
        result.setStatus(200);
        result.setModal(modal);
        return result;
    }

    public static <R> ResultVO<R> buildErrorResult(String error){
        ResultVO<R> result = new ResultVO<R>();
        result.setStatus(200);
        result.setError(error);
        return result;
    }
}
