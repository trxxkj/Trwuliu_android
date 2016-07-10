package cn.trxxkj.trwuliu.driver.wheel;

import cn.trxxkj.trwuliu.driver.view.WheelView;

public interface OnWheelChangedListener {

    void onChanged(WheelView wheel, int oldValue, int newValue);
}
