package cn.trxxkj.trwuliu.driver.bean;

import java.util.List;

/**
 * 我的计划返回的数据实体类
 * Created by admin on 2016/7/10.
 */
public class PlanBean {

    public String code;
    public String serviceTime;

    public String total;

    public String message;

    public List<ReturnData> returnData;

    public static class ReturnData {

        public String planCode;
        public String cargoname;
        public String ownerName;
        public String createtime;
        public String status;
        public String measure;
        public String totalplanned;
        public String isFamily;
        public String telephone;

    }

}
