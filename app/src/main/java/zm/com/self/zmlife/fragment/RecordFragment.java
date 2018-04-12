package zm.com.self.zmlife.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
public class RecordFragment extends Fragment {
    @InjectView(R.id.yang_out)
    EditText yangOut;
    @InjectView(R.id.shen_out)
    EditText shenOut;
    @InjectView(R.id.zhang_out)
    EditText zhangOut;
    @InjectView(R.id.yang_buy)
    EditText yangBuy;
    @InjectView(R.id.shen_buy)
    EditText shenBuy;
    @InjectView(R.id.zhang_buy)
    EditText zhangBuy;
    @InjectView(R.id.all_buy)
    EditText allBuy;
    @InjectView(R.id.yang_in)
    TextView yangIn;
    @InjectView(R.id.shen_in)
    TextView shenIn;
    @InjectView(R.id.zhang_in)
    TextView zhangIn;
    float yangi = 0, sheni = 0, zhangi = 0, yang_buyi = 0, zhang_buyi = 0, shen_buyi = 0, all_buyi = 0;
    @InjectView(R.id.btn_result)
    Button btnResult;
    @InjectView(R.id.btn_save)
    Button btnSave;
    private UserInfoDao userInfoDao;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.inject(this, rootView);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        DaoSession daoSession = myApplication.getDaoSession();
        userInfoDao = daoSession.getUserInfoDao();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.btn_result, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_result:
                String yang = yangOut.getText().toString();
                String shen = shenOut.getText().toString();
                String zhang = zhangOut.getText().toString();

                String yang_buy = yangBuy.getText().toString();
                String shen_buy = shenBuy.getText().toString();
                String zhang_buy = zhangBuy.getText().toString();
                String all_buy = allBuy.getText().toString();


                if (!TextUtils.isEmpty(all_buy)) {
                    all_buyi = Float.parseFloat(all_buy);
                    all_buyi = all_buyi / 3;
                }

                if (!TextUtils.isEmpty(yang)) {
                    yangi = Float.parseFloat(yang);
                }
                if (!TextUtils.isEmpty(shen)) {
                    sheni = Float.parseFloat(shen);
                }
                if (!TextUtils.isEmpty(zhang)) {
                    zhangi = Float.parseFloat(zhang);
                }
                if (!TextUtils.isEmpty(yang_buy)) {
                    yang_buyi = Float.parseFloat(yang_buy);
                }
                if (!TextUtils.isEmpty(zhang_buy)) {
                    zhang_buyi = Float.parseFloat(zhang_buy);
                }
                if (!TextUtils.isEmpty(shen_buy)) {
                    shen_buyi = Float.parseFloat(shen_buy);
                }

                float yang_in = yang_buyi + all_buyi - yangi;
                float shen_in = shen_buyi + all_buyi - sheni;
                float zhang_in = zhang_buyi + all_buyi - zhangi;

                yangIn.setText(String.valueOf(yang_in));
                shenIn.setText(String.valueOf(shen_in));
                zhangIn.setText(String.valueOf(zhang_in));
                break;
            case R.id.btn_save:
                float res_yang = yang_buyi + all_buyi;
                float res_shen = shen_buyi + all_buyi;
                float res_zhang = zhang_buyi + all_buyi;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(new Date());
                UserInfo userRecordInfo = new UserInfo();
                userRecordInfo.setName_shen("申丽娜");
                userRecordInfo.setName_yang("杨玉秀");
                userRecordInfo.setName_zhang("张苗");
                userRecordInfo.setPrice_shen(res_shen);
                userRecordInfo.setPrice_yang(res_yang);
                userRecordInfo.setPrice_zhang(res_zhang);
                userRecordInfo.setDate(date);
                userInfoDao.insert(userRecordInfo);
                EventBus.getDefault().post(new MessageEvent("添加"));
//                userRecordInfoDao.deleteByKey("申丽娜");
//                List<UserInfo> userRecordInfoList_all = userInfoDao.loadAll();
//                UserRecordInfo userRecordInfo1=userRecordInfoDao.load("申丽娜");
//                List<UserRecordInfo> userRecordInfoList = userRecordInfoDao.queryRaw("where name_shen=?","申丽娜");

                break;
        }
    }
}
