package cn.trxxkj.trwuliu.driver.utils;

/**
 *  初始化  url
 */
public class TRurl {


    // http://172.19.4.23:8091     http://172.20.10.123:8080
    public final static String BASE_URL = "http://172.19.4.23:8091";

    /**
     * 登陆
     */
    public final static String LOGIN_URL = BASE_URL + "/app/member/login";

    /**
     *  获取验证  快速登陆  getValCode
     */
    public final static String VALCODE = BASE_URL + "/app/member/getValCode";
    /**'
     *  获取  注册 验证 码  register
     */
    public final static String REGISTER = BASE_URL + "/app/member/register";

    /**
     *  运力 --- 我的司机
     */
    public final static String MYDRIVER =  BASE_URL + "/app/VehicleAndDriver/myDriver";
    /**
     * 添加 司机  searchDriver
     */
    public final static String ADDDRIVER =  BASE_URL + "/app/VehicleAndDriver/addDriver";

    /**
     *  根据账号  查询司机
     */
    public final static String SEARCHDRIVER =  BASE_URL + "/app/MemberInfo/findMemberCellphone";

    /**
     * 我的 车辆
     */
    public final static String MYCAR =  BASE_URL + "/app/VehicleAndDriver/myVehicle";

    /**
     *  车辆 绑定司机
     */
    public final static String CARADDDRIVER = BASE_URL + "/app/VehicleAndDriver/addVehicleDriver";
    /**
     * 车辆  司机 解除绑定
     */
    public final static String CARREMOVEDRIVER = BASE_URL + "/app/VehicleAndDriver/delVehicleDriver";

}
