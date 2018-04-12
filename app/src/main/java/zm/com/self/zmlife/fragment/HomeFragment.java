package zm.com.self.zmlife.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zm.com.self.zmlife.R;
import zm.com.self.zmlife.activity.HomeItemActivity;
import zm.com.self.zmlife.adapter.HomeAdapter;
import zm.com.self.zmlife.commonutil.HeadLineModel;
import zm.com.self.zmlife.commonutil.ReturnMessage;
import zm.com.self.zmlife.commonutil.ToastUtil;

/**
 * Created by Administrator on 2018/3/27.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
    @InjectView(R.id.act_recycler_variety_recycler)
    public
    RecyclerView actRecyclerVarietyRecycler;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    RefreshLayout isrefreshLayout;
    boolean flag=false;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMessage();
                isrefreshLayout=refreshLayout;
                flag=true;
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 贝塞尔雷达 样式  Delivery  DropBox BezierRadar BezierCircle  FlyRefresh  Classics  Phoenix Taurus  BattleCity  HitBlock  WaveSwipe  Material  StoreHouse  WaterDrop
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));//.setEnableHorizontalDrag(true)
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        getMessage();
        return rootView;
    }

    private void initViewOper(final List<HeadLineModel> headLineModelList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        actRecyclerVarietyRecycler.setLayoutManager(linearLayoutManager);
        actRecyclerVarietyRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        HomeAdapter adapter = new HomeAdapter(headLineModelList, getContext());
        actRecyclerVarietyRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onclick(int positon) {
                String url = headLineModelList.get(positon).getUrl();
                Intent intent = new Intent(getActivity(), HomeItemActivity.class);
                intent.putExtra("website", url);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void getMessage() {
        OkGo.<String>get("http://v.juhe.cn/toutiao/index")
                .tag(this)
                .params("type", "top")
                .params("key", "a10037b9da2843c8fe689a828fe3e29e")
                .cacheKey("getMessage")
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ReturnMessage returnMessage = JSON.parseObject(response.body().toString(), ReturnMessage.class);
                        List<HeadLineModel> headLineModelList = JSON.parseArray(returnMessage.getResult().get(0).get("data").toString(), HeadLineModel.class);
                        for (int i = 0; i < headLineModelList.size(); i++) {
                            boolean flag = (null == headLineModelList.get(i).getThumbnail_pic_s02() || headLineModelList.get(i).getThumbnail_pic_s02().equals(""));
                            boolean flag1 = (null == headLineModelList.get(i).getThumbnail_pic_s03() || headLineModelList.get(i).getThumbnail_pic_s03().equals(""));
                            if (flag && flag1) {
                                headLineModelList.get(i).setType(0);
                            } else if (flag1) {
                                headLineModelList.get(i).setType(1);
                            } else {
                                headLineModelList.get(i).setType(2);
                            }
                        }
                        initViewOper(headLineModelList);
                        if(flag){
                            isrefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                            ToastUtil.showToast(getContext(),"刷新成功");
                            flag=false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtil.showToast(getContext(), "网络错误");
                        if(flag){
                            isrefreshLayout.finishRefresh(2000,false);//传入false表示刷新失败
                            flag=false;
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        super.onCacheSuccess(response);
                        ReturnMessage returnMessage = JSON.parseObject(response.body().toString(), ReturnMessage.class);
                        List<HeadLineModel> headLineModelList = JSON.parseArray(returnMessage.getResult().get(0).get("data").toString(), HeadLineModel.class);
                        for (int i = 0; i < headLineModelList.size(); i++) {
                            boolean flag = (null == headLineModelList.get(i).getThumbnail_pic_s02() || headLineModelList.get(i).getThumbnail_pic_s02().equals(""));
                            boolean flag1 = (null == headLineModelList.get(i).getThumbnail_pic_s03() || headLineModelList.get(i).getThumbnail_pic_s03().equals(""));
                            if (flag && flag1) {
                                headLineModelList.get(i).setType(0);
                            } else if (flag1) {
                                headLineModelList.get(i).setType(1);
                            } else {
                                headLineModelList.get(i).setType(2);
                            }
                        }
                        initViewOper(headLineModelList);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
