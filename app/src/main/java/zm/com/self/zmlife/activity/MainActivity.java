package zm.com.self.zmlife.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zm.com.self.zmlife.R;
import zm.com.self.zmlife.adapter.SectionsPagerAdapter;
import zm.com.self.zmlife.commonutil.ToastUtil;
import zm.com.self.zmlife.fragment.RecordFragment;
import zm.com.self.zmlife.fragment.MagazineFragment;
import zm.com.self.zmlife.fragment.UserCentralFragment;
import zm.com.self.zmlife.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.title)
    TextView title;
    private List<Fragment> fragments;
    private TextBadgeItem badgeItem, badgeItem1;
    private long firstTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();

    }

    private void initView() {
        title.setText("杂志");
        initBottomNavigationBar();
        initViewPager();
    }

    private void initBottomNavigationBar() {
        badgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setAnimationDuration(200)
                .setBackgroundColor(Color.RED)
                .setHideOnSelect(false)
                .setText("0");
        badgeItem1 = new TextBadgeItem()
                .setBorderWidth(4)
                .setAnimationDuration(200)
                .setBackgroundColor(Color.RED)
                .setHideOnSelect(true)
                .setText("0");
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//设置导航栏模式
        bottomNavigationBar.setBackgroundColor(Color.GRAY);
//        bottomNavigationBar.setInActiveColor(R.color.colorPrimaryDark);
//        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar.setBarBackgroundColor(R.color.tabbarbackground);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);//设置导航栏背景模式
        bottomNavigationBar// .setActiveColorResource(Color.BLUE)//设置BottomNavigationItem颜色
                .addItem(new BottomNavigationItem(R.mipmap.ic_import_contacts_black_36dp, "杂志").setInactiveIconResource(R.mipmap.ic_import_contacts_white_36dp).setActiveColor(Color.BLUE))
                .addItem(new BottomNavigationItem(R.mipmap.ic_border_color_black_36dp, "记录").setInactiveIconResource(R.mipmap.ic_border_color_white_36dp).setActiveColor(Color.GREEN))
                .addItem(new BottomNavigationItem(R.mipmap.ic_content_copy_black_36dp, "八卦").setInactiveIconResource(R.mipmap.ic_content_copy_white_36dp).setActiveColor(Color.RED))
                .addItem(new BottomNavigationItem(R.mipmap.ic_business_black_36dp, "设置").setInactiveIconResource(R.mipmap.ic_business_white_36dp).setActiveColor(Color.YELLOW))//.setBadgeItem(badgeItem)
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewpager.setCurrentItem(position);
                switch (position){
                    case 0:
                        title.setText("杂志");
                        break;
                    case 1:
                        title.setText("记录");
                        break;
                    case 2:
                        title.setText("八卦");
                        break;
                    case 3:
                        title.setText("设置");
                        break;
                }

            }

            //点击的上一次
            @Override
            public void onTabUnselected(int position) {
//                badgeItem.hide();
//                badgeItem1.show();
//                badgeItem.setText(String.valueOf(position));
            }

            //点击两次执行，可做刷新
            @Override
            public void onTabReselected(int position) {
//                badgeItem.show();
//                badgeItem.setText(String.valueOf(position));
//                badgeItem1.hide();

            }
        });
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new RecordFragment());
        fragments.add(new MagazineFragment());
        fragments.add(new UserCentralFragment());

        viewpager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);
    }

    //刚进来就执行一次
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    //点击执行
    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar.selectTab(position);
    }

    //状态改变执行
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                ToastUtil.showToast(MainActivity.this, "再按一次退出程序");
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                System.exit(0);//否则退出程序
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
