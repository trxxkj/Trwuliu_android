package cn.trxxkj.trwuliu.driver.bean;

import cn.trxxkj.trwuliu.driver.adapter.WheelView;


public interface OnWheelScrollListener {

    void onScrollingStarted(WheelView wheel);


    void onScrollingFinished(WheelView wheel);
}

