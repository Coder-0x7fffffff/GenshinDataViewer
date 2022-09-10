package space.xiami.project.genshindataviewer.domain;

public class ResultDO<T> {

    private boolean isSuccess;
    private T result;
    private String msg;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T> ResultDO<T> buildSuccessResult(T obj){
        ResultDO<T> ret = new ResultDO<T>();
        ret.setSuccess(true);
        ret.setResult(obj);
        return ret;
    }

    public static <T> ResultDO<T> buildSuccessResult(T obj, String msg){
        ResultDO<T> ret = new ResultDO<T>();
        ret.setSuccess(true);
        ret.setResult(obj);
        ret.setMsg(msg);
        return ret;
    }

    public static <T> ResultDO<T> buildErrorResult(String msg){
        ResultDO<T> ret = new ResultDO<T>();
        ret.setSuccess(false);
        ret.setMsg(msg);
        return ret;

    }
}
