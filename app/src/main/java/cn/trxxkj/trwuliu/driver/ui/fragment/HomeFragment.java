package cn.trxxkj.trwuliu.driver.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Arrays;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.ui.CapacityActivity;
import cn.trxxkj.trwuliu.driver.ui.LoginActivity;
import cn.trxxkj.trwuliu.driver.ui.MainActivity;
import cn.trxxkj.trwuliu.driver.ui.PlanActivity;
import cn.trxxkj.trwuliu.driver.ui.WaybillActivity;
import cn.trxxkj.trwuliu.driver.view.NetworkImageHolderView;

/**
 * 主界面
 * @author cyh 2016.6.10 下午16:20
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<String> networkImages;

    private View layout;   //主界面子Fragment

    //网络轮播图地址
    private String[] images = {"http://www.tianruigroup.cn/Upload/image/20150828/20150828183905_2812.jpg",
            "http://www.tianruigroup.cn/Upload/image/20150826/20150826120137_9218.jpg",
            "http://www.tianruigroup.cn/Upload/image/20150827/20150827163252_8437.jpg",
            "http://www.tianruigroup.cn/Upload/image/20131023/20131023114555_9531.jpg"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (layout != null) {
            // 防止多次new出片段对象，造成图片错乱问题
            return layout;
        }
        layout = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        init();
        return layout;
    }

    //初始化视图
    private void initView() {
        layout.findViewById(R.id.home_login).setOnClickListener(this);
        layout.findViewById(R.id.layout_waybill).setOnClickListener(this);
        layout.findViewById(R.id.layout_plan).setOnClickListener(this);
        convenientBanner = (ConvenientBanner) layout.findViewById(R.id.convenientBanner);
    }

    private void init() {
        initImageLoader();
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 将layout从父组件中移除
        ViewGroup parent = (ViewGroup) layout.getParent();
        parent.removeView(layout);
    }

    /**
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.layout_waybill:
                startActivity(new Intent(getActivity(), WaybillActivity.class));
                break;
            case R.id.layout_plan:
                startActivity(new Intent(getActivity(), PlanActivity.class));
                break;
        }
    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

}
