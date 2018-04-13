package zm.com.self.zmlife.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zm.com.self.zmlife.R;
import zm.com.self.zmlife.commonutil.MyApplication;
import zm.com.self.zmlife.datebasedao.DaoSession;
import zm.com.self.zmlife.datebasedao.UserInfoDao;
import zm.com.self.zmlife.entity.MessageEvent;
import zm.com.self.zmlife.entity.UserInfo;

/**
 * Created by Administrator on 2018/3/27.
 */

@SuppressLint("ValidFragment")
public class MagazineFragment extends Fragment {

    @InjectView(R.id.line_graphic)
    LineChart mChart;
    private ArrayList<Entry> yVals1;
    private XAxis xAxis;
    Typeface mTfLight;
    private List<UserInfo> userRecordInfoList_all;
    private UserInfoDao userRecordInfoDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_magazine, container, false);
        ButterKnife.inject(this, rootView);
        EventBus.getDefault().register(this);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        DaoSession daoSession = myApplication.getDaoSession();
        userRecordInfoDao = daoSession.getUserInfoDao();
        userRecordInfoList_all = userRecordInfoDao.loadAll();
        initView();
        return rootView;
    }

    private void initView() {
//        mChart.setOnChartValueSelectedListener(getActivity());
        // no description text
//        mChart.getDescription().setEnabled(false);
        //设置是否绘制chart边框的线
        mChart.setDrawBorders(false);
        //设置chart边框线颜色
        mChart.setBorderColor(Color.RED);
        //设置chart边框线宽度
        mChart.setBorderWidth(1f);

        //设置chart是否可以触摸
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);

        //设置是否可以拖拽
        mChart.setDragEnabled(true);
//        设置是否可以缩放 x和y，默认true
        mChart.setScaleEnabled(true);
        //设置是否可以通过双击屏幕放大图表。默认是true
        mChart.setDoubleTapToZoomEnabled(true);
        //是否启用网格背景
//        mChart.setDrawGridBackground(true);
        mChart.setHighlightPerDragEnabled(true);

        //设置chart动画 x轴y轴都有动画
//        mChart.animateXY(2000, 2000);
        // false代表缩放时X与Y轴分开缩放,true代表同时缩放
        mChart.setPinchZoom(true);
        // set an alternative background color
        //图表背景颜色的设置
        mChart.setBackgroundColor(Color.WHITE);
        //图表进入的动画时间
        mChart.animateX(1000);
        // add data
        //描述信息
        Description description = new Description();
        description.setText("");
//        //设置描述信息
        mChart.setDescription(description);
        //设置没有数据时显示的文本
        mChart.setNoDataText("没有数据...");
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        // modify the legend ...
        //表头代表线条说明前的图形 可以设置线形,圆形,方形
        l.setForm(Legend.LegendForm.SQUARE);

        l.setTypeface(mTfLight);
        //表头字体大小
        l.setTextSize(11f);
        //表头线条说明的颜色
        l.setTextColor(Color.RED);
        //表头线条放置的位置
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //表头多条线条的排列方式
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //表头的说明是否绘制到图表内部
        l.setDrawInside(false);
//        l.setYOffset(11f);

        xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(8f);
        //X轴显示的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //X轴
        xAxis.setSpaceMin(0.5f);
        //X轴数据的颜色
        xAxis.setTextColor(Color.BLUE);
        //是否绘制X轴的网格线
        xAxis.setDrawGridLines(false);


        xAxis.setDrawAxisLine(false);
        //TODO 设置x轴坐标显示的数量  加true才能显示设置的数量 一旦加true后续的x轴数据显示有问题,
        // xAxis.setLabelCount(5,true);

        //设置竖线为虚线样子
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        /********************************************************************************/
        //图表第一个和最后一个label数据不超出左边和右边的Y轴
        xAxis.setAvoidFirstLastClipping(true);
        //设置限制线 70代表某个该轴某个值，也就是要画到该轴某个值上
//        LimitLine limitLine = new LimitLine(70);
        //设置限制线的宽
//        limitLine.setLineWidth(1f);
        //设置限制线的颜色
//        limitLine.setLineColor(Color.RED);
        //设置基线的位置
//        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
//        limitLine.setLabel("我是基线，也可以叫我水位线");
        //设置限制线为虚线
//        limitLine.enableDashedLine(10f, 10f, 0f);
        /********************************************************************************/

        if (userRecordInfoList_all != null && userRecordInfoList_all.size() > 0) {
            setYData(userRecordInfoList_all.size());
            setXData();
        }
        /********************************************************************************/

        //左侧Y轴属性设置
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        //Y轴数据的字体颜色
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        //左侧Y轴最大值
        leftAxis.setAxisMaximum(200);
        //左侧Y轴最小值
        leftAxis.setAxisMinimum(0f);
        //是否绘制Y轴网格线
        leftAxis.setDrawGridLines(true);
        //TODO 什么属性?
        leftAxis.setGranularityEnabled(true);
        leftAxis.setDrawZeroLine(true);
        //左边Y轴添加限制线
//        leftAxis.addLimitLine(limitLine);
        //右侧Y轴坐标
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setInverted(false);
        rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setAxisMaximum(200);
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        //是否绘制等0线
//        rightAxis.setDrawZeroLine(true);
        rightAxis.setGranularityEnabled(false);
    }


    private void setXData() {
        final Map<Integer, String> xMap = new HashMap<>();

        final String[] valueArry = new String[userRecordInfoList_all.size()];

        xAxis.setLabelCount(userRecordInfoList_all.size());

        for (int i = 0; i < userRecordInfoList_all.size(); i++) {
            String date = userRecordInfoList_all.get(i).getDate();
            String[] datesp = date.split("-");
            valueArry[i] = datesp[1] + datesp[2];
        }

        for (int i = 0; i < yVals1.size(); i++) {
            xMap.put((int) yVals1.get(i).getX(), valueArry[i]);
        }
        //自定义x轴标签数据
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xMap.get((int) value);
            }
        });
    }

    private void setYData(int count) {
        yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = userRecordInfoList_all.get(i).getPrice_zhang();
            yVals1.add(new Entry(i, val));
        }
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float val = userRecordInfoList_all.get(i).getPrice_shen();
            yVals2.add(new Entry(i, val));
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }

        ArrayList<Entry> yVals3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = userRecordInfoList_all.get(i).getPrice_yang();
            yVals3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "张苗");

            set1.setCubicIntensity(1.0f);
            //数据对应的是左边还是右边的Y值
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            //线条的颜色
            set1.setColor(Color.BLUE);
            //表中数据圆点的颜色
            set1.setCircleColor(Color.RED);
            //表中数据线条的宽度
            set1.setLineWidth(2f);
            //表中数据圆点的半径
            set1.setCircleRadius(3f);
            //设置线面部分是否填充
            set1.setDrawFilled(false);
            //填充的颜色透明度
            set1.setFillAlpha(255);
            //填充颜色
            set1.setFillColor(ColorTemplate.rgb("ffffff"));
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            //绘制的数据的圆点是否是实心还是空心
            set1.setDrawCircleHole(true);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "申丽娜");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(true);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(yVals3, "杨玉秀");
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(Color.GREEN);
            set3.setCircleColor(Color.RED);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.GREEN, 200));
            set3.setDrawCircleHole(true);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            LineDataSet set4 = new LineDataSet(yVals1, "Content");

//            LineData lineData = new LineData(xVals, set1);
            // create a data object with the datasets
            LineData data = new LineData(set1, set2, set3);
            //设置图标中显示数字的颜色
            data.setValueTextColor(Color.RED);
            //设置图标中显示数字的大小
            data.setValueTextSize(9f);
            // set data
            mChart.setData(data);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        userRecordInfoList_all = userRecordInfoDao.loadAll();
        setYData(userRecordInfoList_all.size());
        setXData();
        mChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
