package cn.trxxkj.trwuliu.driver.utils;

/**
 * 初始化url
 * Created by cyh on 2016/7/11.
 */
public class TRurl1 {

    public final static String BASE_URL = "http://172.19.4.23:8091";

    /**
     * 计划列表
     */
    public final static String  PLANPAGE = BASE_URL + "/app/planvender/page";

    /**
     * 计划详情
     */
    public final static String  PLANDRTAIL = BASE_URL + "/app/planvender/detail";

    /**
     * 计划统计
     */
    public final static String  PLANSTAT = BASE_URL + "/app/planvender/planStat";

    /**
     * 接收计划
     */
    public final static String  PLANACCEPT = BASE_URL + "/app/planvender/accept";

    /**
     * 拒绝计划
     */
    public final static String  PLANREFUSE = BASE_URL + "/app/planvender/refuse";

    /**
     *删除计划
     */
    public final static String  PLANDELETE = BASE_URL + "/app/planvender/delete";




    /**
     *我承运的运单列表
     */
    public final static String  WAYBILLPAGE = BASE_URL + "/app/billvender/page";

    /**
     *我运输的运单列表
     */
    public final static String  WAYBILLDRIVER = BASE_URL + "/app/billdriver/page";

    /**
     *运单详情
     */
    public final static String  WAYBILLDETAIL = BASE_URL + "/app/billvender/detail";

    /**
     * 车主新建运单
     */
    public final static String  WAYBILLSAVE = BASE_URL + "/app/billvender/save";

    /**
     *车主获取车辆列表
     */
    public final static String  WAYBILLQUERYVEHICLE = BASE_URL + "/app/billvender/queryVehicle";

    /**
     *车主收回运单
     */
    public final static String  WAYBILLCANCLE = BASE_URL + "/app/billvender/cancle";

    /**
     *车主删除运单
     */
    public final static String  WAYBILLDELETE = BASE_URL + "/app/billvender/delete";

    /**
     *车主修改运单
     */
    public final static String  WAYBILLEDIT = BASE_URL + "/app/planvender/edit";

    /**
     *司机删除运单
     */
    public final static String  DRIVERDELETE = BASE_URL + "/app/billdriver/delete";

    /**
     *司机拒绝运单
     */
    public final static String  DRIVERREFUSE = BASE_URL + "/app/billdriver/refuse";

    /**
     *司机接受运单
     */
    public final static String  DRIVERACCEPT = BASE_URL + "/app/billdriver/accept";

    /**
     *司机确认到达发货地
     */
    public final static String  DRIVERPICKUP = BASE_URL + "/app/billdriver/pickup";

    /**
     *司机确认装货完毕
     */
    public final static String  DRIVERDEPARTURE = BASE_URL + "/app/billdriver/departure";

    /**
     *司机确认到达目的地
     */
    public final static String  DRIVERARRIVE = BASE_URL + "/app/billdriver/arrived";

    /**
     *司机确认卸货完成
     */
    public final static String  DRIVERDISCHARGE = BASE_URL + "/app/billdriver/discharge";



    /**
     *获取经纬度
     */
    public final static String  WAYBILLGPS = BASE_URL + "/app/billvender/gps";

    /**
     *运单轨迹
     */
    public final static String  WAYBILLTRACK = BASE_URL + "/app/billvender/track";

}
