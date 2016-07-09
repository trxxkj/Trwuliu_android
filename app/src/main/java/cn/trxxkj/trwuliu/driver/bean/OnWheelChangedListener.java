package cn.trxxkj.trwuliu.driver.bean;

import cn.trxxkj.trwuliu.driver.adapter.WheelView;

public interface OnWheelChangedListener {

    void onChanged(WheelView wheel, int oldValue, int newValue);
}
