package zm.com.self.zmlife.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by Administrator on 2018/4/12.
 */

public class JokeHomeFragment extends Fragment {
    @InjectView(R.id.act_recycler_variety_recycler_joke)
    RecyclerView actRecyclerVarietyRecyclerJoke;
    @InjectView(R.id.refreshLayout_joke)
    SmartRefreshLayout refreshLayoutJoke;
    RefreshLayout isrefreshLayout;
    boolean flag = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokehome, container, false);

        refreshLayoutJoke.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMessage("1","2012547");
                isrefreshLayout = refreshLayout;
                flag = true;
            }
        });
        refreshLayoutJoke.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 贝塞尔雷达 样式  Delivery  DropBox BezierRadar BezierCircle  FlyRefresh  Classics  Phoenix Taurus  BattleCity  HitBlock  WaveSwipe  Material  StoreHouse  WaterDrop
        refreshLayoutJoke.setRefreshHeader(new ClassicsHeader(getContext()));//.setEnableHorizontalDrag(true)
        //设置 Footer 为 球脉冲 样式
        refreshLayoutJoke.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        getMessage("1","20152555");
        ButterKnife.inject(this, view);
        return view;
    }
    private void initViewOper(final List<HeadLineModel> headLineModelList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        actRecyclerVarietyRecyclerJoke.setLayoutManager(linearLayoutManager);
        actRecyclerVarietyRecyclerJoke.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        HomeAdapter adapter = new HomeAdapter(headLineModelList, getContext());
        actRecyclerVarietyRecyclerJoke.setAdapter(adapter);
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

    public void getMessage(String page,String time) {
        OkGo.<String>get("http://v.juhe.cn/joke/content/list.php")
                .tag(this)
                .params("key", "a10037b9da2843c8fe689a828fe3e29e")
                .params("page", page)
                .params("pagesize", 20)
                .params("sort", "asc")
                .params("time", time)
                .cacheKey("getMessage")
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ReturnMessage returnMessage = JSON.parseObject(response.body().toString(), ReturnMessage.class);

                        if(returnMessage.getError_code().equals("0")){
                            if(returnMessage.getResult()!=null&&returnMessage.getResult().size()>0){
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
                        }

                        if (flag) {
                            isrefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                            ToastUtil.showToast(getContext(), "刷新成功");
                            flag = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtil.showToast(getContext(), "网络错误");
                        if (flag) {
                            isrefreshLayout.finishRefresh(2000, false);//传入false表示刷新失败
                            flag = false;
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
