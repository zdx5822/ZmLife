package zm.com.self.zmlife.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zm.com.self.zmlife.R;
import zm.com.self.zmlife.adapter.UserCentralAdapter;
import zm.com.self.zmlife.commonutil.MyApplication;
import zm.com.self.zmlife.datebasedao.DaoSession;
import zm.com.self.zmlife.datebasedao.UserInfoDao;
import zm.com.self.zmlife.entity.MessageEvent;
import zm.com.self.zmlife.entity.UserInfo;

/**
 * Created by Administrator on 2018/3/27.
 */

@SuppressLint("ValidFragment")
public class UserCentralFragment extends Fragment {
    @InjectView(R.id.act_recycler_variety_recycler)
    RecyclerView actRecyclerVarietyRecycler;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usercentral, container, false);
        ButterKnife.inject(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                selectData();
                refreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        selectData();
    }

    private void selectData() {
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        DaoSession daoSession = myApplication.getDaoSession();
        final UserInfoDao userRecordInfoDao = daoSession.getUserInfoDao();
        List<UserInfo> userRecordInfoList_all = userRecordInfoDao.loadAll();
        final UserCentralAdapter adapter = new UserCentralAdapter(userRecordInfoList_all, getActivity());
//        actRecyclerVarietyRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        actRecyclerVarietyRecycler.setLayoutManager(linearLayoutManager);//设置布局管理器
        actRecyclerVarietyRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));//设置分割线
        actRecyclerVarietyRecycler.setAdapter(adapter);
        actRecyclerVarietyRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter.setonItemLongClickListener(new UserCentralAdapter.onItemLongClickListener() {
            @Override
            public void onclick(final int positon) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("删除记录").setMessage("是否删除？").setNegativeButton("否", null).setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = adapter.getPosition(positon).getId();
                        userRecordInfoDao.deleteByKey(id);
                        List<UserInfo> userRecordInfoList = userRecordInfoDao.loadAll();
                        adapter.setPosition(userRecordInfoList);
                        EventBus.getDefault().post(new MessageEvent("删除"));

                    }
                }).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
