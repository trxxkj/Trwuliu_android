//package cn.trxxkj.trwuliu.driver.util;
//
//import com.activeandroid.query.Select;
//
//import java.util.List;
//
//import cn.trxxkj.trwuliu.driver.bean.WaybillInfo;
//
///**
// * Created by admin on 2016/4/14.
// */
//public final class DBUtils {
//
////    public static void delete(WaybillInfo info) {
////        new Delete()
////                .from(WaybillInfo.class)
////                .where("WaybillId=? And isFavor=?", info.getWaybillId(),
////                        info.getIsFavor()).executeSingle();
////    }
//
//    public static List<WaybillInfo> getWaybill() {
//        return new Select().from(WaybillInfo.class).where("isWaybill=?", 1)
//                .execute();
//    }
//
//}
