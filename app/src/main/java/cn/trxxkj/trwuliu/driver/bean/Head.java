package cn.trxxkj.trwuliu.driver.bean;

/**
 * App请求参数Head类
 * @author cyh 2016.6.23 上午9:30
 */

public class Head {

    private String tokenId;
    private String account;
    private String callType;
    private String callTypeNo;
    private String mobileType;
    private String appVersion;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallTypeNo() {
        return callTypeNo;
    }

    public void setCallTypeNo(String callTypeNo) {
        this.callTypeNo = callTypeNo;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
