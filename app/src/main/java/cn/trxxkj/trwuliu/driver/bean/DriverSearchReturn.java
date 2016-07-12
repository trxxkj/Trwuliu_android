package cn.trxxkj.trwuliu.driver.bean;

/**
 * 添加 司机 返回数据  对象
 */
public class DriverSearchReturn {


    public String code;
    public String serviceTime;
    public String message;

    public retdata returnData;

    public static class retdata {

        public String cellPhone;  //  drivertel

        public String id;   // driverid

        public String driveImagePath; // 驾驶证 图片url

        public String userName;    //  drivername

        public String driverpercheck; // 0：未认证， 1：认证通过 ，2：认证中，3：认证失败


    }


}
