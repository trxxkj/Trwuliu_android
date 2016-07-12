package cn.trxxkj.trwuliu.driver.bean;

/**
 * App返回结果
 * @author cyh 2016.6.23 上午10:45
 */

public class AppResult {

    private String code;

    private String message;

    private Object returnData;

    private Integer total;
    private long serviceTime = System.currentTimeMillis();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
    }

}
