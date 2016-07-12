package cn.trxxkj.trwuliu.driver.bean;


/**
 * App访问服务端接口参数格式
 * @author cyh 2016.6.23 上午10:30
 * @param <T>
 */
public class AppParam<T> {

    private Head head;
    private T body;
    private String sign;


    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
