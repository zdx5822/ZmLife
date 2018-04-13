package zm.com.self.zmlife.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.astuetz.PagerSlidingTabStrip;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import zm.com.self.zmlife.R;
import zm.com.self.zmlife.adapter.MyPagerAdapter;
import zm.com.self.zmlife.fragment.home.ShehuiHomeFragment;
import zm.com.self.zmlife.fragment.home.ShishangHomeFragment;
import zm.com.self.zmlife.fragment.home.TopHomeFragment;
import zm.com.self.zmlife.fragment.home.YuleHomeFragment;

/**
 * Created by Administrator on 2018/3/27.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
    @InjectView(R.id.pst)
    PagerSlidingTabStrip pst;
    @InjectView(R.id.pager)
    ViewPager viewPager;
    ArrayList<Fragment> fragments;
    String[] titles = {"头条", "社会", "娱乐", "时尚"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.inject(this, rootView);
        fragments = new ArrayList<>();
        TopHomeFragment topHomeFragment = new TopHomeFragment();
        ShehuiHomeFragment shehuiHomeFragment = new ShehuiHomeFragment();
        YuleHomeFragment yuleHomeFragment = new YuleHomeFragment();
        ShishangHomeFragment shishangHomeFragment = new ShishangHomeFragment();

        //添加fragment到集合中时注意顺序
        fragments.add(topHomeFragment);
        fragments.add(shehuiHomeFragment);
        fragments.add(yuleHomeFragment);
        fragments.add(shishangHomeFragment);

        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        viewPager.setAdapter(new MyPagerAdapter(fragmentManager, titles, fragments));
        viewPager.setCurrentItem(0);
        //当ViewPager的onPagerChangeListener回调时，PagerSlidingTabStrip也一起随之变动
        //具体做法都已封装到了PagerSlidingTabStrip.setViewPager()方法里，使用时调用实例如下

        pst.setTextSize(40);
        pst.setIndicatorColor(Color.RED);
        pst.setIndicatorHeight(8);
        pst.setShouldExpand(true);
        pst.setTabBackground(Color.TRANSPARENT);
        pst.setUnderlineHeight(5);
        pst.setDividerColor(Color.TRANSPARENT);
        pst.setBackgroundColor(Color.TRANSPARENT);
        pst.setViewPager(viewPager);


        return rootView;
    }
//123456   1762

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

//    pstsIndicatorColor: 滑动indicator的颜色
//    pstsIndicatorHeight: 滑动indicator的高度
//    pstsUnderlineColor: 底部分割线的颜色
//    pstsUnderlineHeight: 底部分割线的高度
//    pstsDividerColor: 两个tab之间分割线的颜色
//    pstsDividerPadding: 两个tab之间分割线的上下padding
//    pstsTabPaddingLeftRight: 每个tab的左右padding
//    pstsScrollOffset: Scroll offset of the selected tab
//    pstsTabBackground: Background drawable of each tab, should be a StateListDrawable
//    pstsShouldExpand: 设为true时，每个tab的weight相同，默认false
//    pstsTextAllCaps: 设为true时，所有tab标题为大写，默认true

}
