package cn.trxxkj.trwuliu.driver.bean;

import java.util.List;

import cn.trxxkj.trwuliu.driver.ui.SelectCarActivity;

/**
 * 未确认计划详情返回的数据实体类
 * Created by admin on 2016/7/13.
 */
public class UnconfirmPlanBean {

    public String code;
    public String serviceTime;

    public String total;

    public List<ReturnData> returnData;

    public static class ReturnData {

        public String planCode;
        public String cargoname;
        public String measure;
        public String startcity;
        public String endcity;
        public String distance;
        public String sendPerson;
        public String sendPersonPhone;
        public String receivePerson;
        public String receivePersonPhone;
        public Double price;
        public String priceunits;
        public String starttime;
        public String endtime;
        public String totalplanned;
        public String linkman;
        public String telephone;
        public String status;
        public String isFamily;

    }

}
