package cn.trxxkj.trwuliu.driver.bean;

import java.util.List;

import javax.sql.StatementEvent;

/**
 * 我的车辆  返回数据  实体类
 */
public class MyCarReturn {

    public String code;
    public String serviceTime;

    public String total;

    public List<Mycar> returnData;

    public static class Mycar {

        public String billstatus;  // 车辆状态   车辆进行中的  运单状态2-发货中3-运货中4-卸货中5-空闲中
        public String memberId;   // 车辆所有者 id
        public String memo;    // 认证失败 原因
        public String vehiHeadImgPath; // 车头照片路径
        public String vehiId;     //  车辆id
        public String id;  // 车辆id
        public String status;  // 认证 成功失败状态
        public String vehiLength;  // 车辆长度
        public String vehiNo;  // 车牌号
        public String vehiPrefix; //  车牌号前缀
        public String vehiType;  // 车辆类型
        public String vehiTypeName; // 类型名称
        public String vehiWeight;  // 车辆重量(吨)
        public String driverName;  // 绑定的 司机
        public String driverTel;   // 司机电话
        public String vehiDriverId; // 绑定司机的id


    }


}
