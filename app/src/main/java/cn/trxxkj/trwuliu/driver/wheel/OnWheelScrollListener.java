package cn.trxxkj.trwuliu.driver.wheel;

import cn.trxxkj.trwuliu.driver.view.WheelView;


public interface OnWheelScrollListener {

    void onScrollingStarted(WheelView wheel);


    void onScrollingFinished(WheelView wheel);
}

