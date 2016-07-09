package cn.trxxkj.trwuliu.driver.bean;

import cn.trxxkj.trwuliu.driver.ui.SelectCarActivity;

/**
 * 运单轨迹请求参数
 * @author cyh 2016.6.25 下午15:30
 */
public class Track {

    private String dateTrack;
    private String track;

    public Track(String dateTrack, String track){

        this.dateTrack = dateTrack;
        this.track =track;

    }

    public String getDateTrack() {
        return dateTrack;
    }

    public void setDateTrack(String dateTrack) {
        this.dateTrack = dateTrack;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }


}
