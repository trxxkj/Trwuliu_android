package cn.trxxkj.trwuliu.driver.bean;

import java.util.List;

/**
 *  我的司机  返回 的 数据  实体类
 */
public class DriverBean {

    public String code;
    public String serviceTime;

    public String total;

    public List<ReturnData> returnData;

    public static class ReturnData {

        public String count;
        public String createtime;
        public String creator;
        public String driverid;
        public String drivername;
        public String drivertel;
        public String id;
        public String status;
        public String vehicleownerid;

    }

}
