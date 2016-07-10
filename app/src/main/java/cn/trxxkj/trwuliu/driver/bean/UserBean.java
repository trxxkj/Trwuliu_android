package cn.trxxkj.trwuliu.driver.bean;

/**
 * Created by guozhengwei on 2016/7/10.
 */
public class UserBean {

    public String code;
    public String serviceTime;

    public ReturnData returnData;

    public static class ReturnData {

        public String cellphone;
        public String companypercheck;
        public String driverpercheck;
        public String id;
        public String realName;
        public String tokenId;
        public String userpercheck;


    }

}
