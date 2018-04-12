package zm.com.self.zmlife.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zm.com.self.zmlife.R;
import zm.com.self.zmlife.entity.UserInfo;

/**
 * Created by Administrator on 2018/4/2.
 */
public class UserCentralAdapter extends RecyclerView.Adapter<UserCentralAdapter.UserCentralHolder> {
    private List<UserInfo> userRecordInfoList;
    private Context context;
    private onItemLongClickListener onItemLongClickListener;

    public UserCentralAdapter(List<UserInfo> userRecordInfoList, Context context) {
        this.userRecordInfoList = userRecordInfoList;
        this.context = context;
    }

    @Override
    public UserCentralHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
//        view = View.inflate(context, R.layout.item_usercentral, null);
        view = LayoutInflater.from(context).inflate(R.layout.item_usercentral, parent,false);
        return new UserCentralHolder(view);
    }

    @Override
    public void onBindViewHolder(UserCentralHolder holder, final int position) {
        holder.nameShen.setText(userRecordInfoList.get(position).getName_shen());
        holder.nameYang.setText(userRecordInfoList.get(position).getName_yang());
        holder.nameZhang.setText(userRecordInfoList.get(position).getName_zhang());
        holder.priceShen.setText(String.valueOf(userRecordInfoList.get(position).getPrice_shen()));
        holder.priceYang.setText(String.valueOf(userRecordInfoList.get(position).getPrice_yang()));
        holder.priceZhang.setText(String.valueOf(userRecordInfoList.get(position).getPrice_zhang()));
        holder.date.setText(userRecordInfoList.get(position).getDate());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onclick(position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userRecordInfoList != null) {
            return userRecordInfoList.size();
        }
        return 0;
    }

    public UserInfo getPosition(int position) {
        UserInfo userRecordInfo = userRecordInfoList.get(position);
        return userRecordInfo;
    }

    public void setPosition(List<UserInfo> userRecordInfoList) {
        this.userRecordInfoList = userRecordInfoList;
        notifyDataSetChanged();
    }

    public interface onItemLongClickListener {
        void onclick(int positon);
    }

    public void setonItemLongClickListener(onItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    static class UserCentralHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.name_shen)
        TextView nameShen;
        @InjectView(R.id.name_zhang)
        TextView nameZhang;
        @InjectView(R.id.name_yang)
        TextView nameYang;
        @InjectView(R.id.price_shen)
        TextView priceShen;
        @InjectView(R.id.price_zhang)
        TextView priceZhang;
        @InjectView(R.id.price_yang)
        TextView priceYang;

        UserCentralHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
