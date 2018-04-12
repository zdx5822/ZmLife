package zm.com.self.zmlife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;
import java.util.List;
import zm.com.self.zmlife.R;
import zm.com.self.zmlife.commonutil.HeadLineModel;
import zm.com.self.zmlife.commonutil.ToastUtil;

/**
 * Created by Administrator on 2018/3/28.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public static final int TYPE_fIRST_IMAGE = 0;
    public static final int TYPE_SECOND_IMAGE = 1;
    public static final int TYPE_THREE_IMAGE = 2;
    private List<HeadLineModel> headLineModelList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public HomeAdapter(List<HeadLineModel> headLineModelList, Context context) {
        this.headLineModelList = headLineModelList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //根据viewtype来创建条目

        if (viewType == TYPE_fIRST_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_first_img, null);
            return new FirstImageHolder(view);
        } else if (viewType == TYPE_SECOND_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_second_img, null);
            return new ScendImageHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_third_img, null);
            return new ThirdImageHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        HeadLineModel headLineModel = headLineModelList.get(position);

        if (holder instanceof FirstImageHolder) {
            ((FirstImageHolder) holder).title.setText("\t\t\t\t" + headLineModel.getTitle());
            ((FirstImageHolder) holder).date.setText(headLineModel.getDate());
            ((FirstImageHolder) holder).authorName.setText(headLineModel.getAuthor_name());
            OkGo.<Bitmap>get(headLineModel.getThumbnail_pic_s())
                    .tag(this)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onSuccess(Response<Bitmap> response) {
                            ((FirstImageHolder) holder).thumbnailPicS.setImageBitmap(response.body());
                        }

                        @Override
                        public void onCacheSuccess(Response<Bitmap> response) {
                            super.onCacheSuccess(response);
                            ((FirstImageHolder) holder).thumbnailPicS.setImageBitmap(response.body());
                        }

                        @Override
                        public void onError(Response<Bitmap> response) {
                            super.onError(response);
                            ToastUtil.showToast(context, "网络错误");
                        }
                    });

        }
        if (holder instanceof ScendImageHolder) {
            ((ScendImageHolder) holder).title.setText("\t\t\t\t" + headLineModel.getTitle());
            ((ScendImageHolder) holder).date.setText(headLineModel.getDate());
            ((ScendImageHolder) holder).authorName.setText(headLineModel.getAuthor_name());
            OkGo.<Bitmap>get(headLineModel.getThumbnail_pic_s())
                    .tag(this)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onSuccess(Response<Bitmap> response) {
                            ((ScendImageHolder) holder).thumbnailPicS.setImageBitmap(response.body());
                        }

                        @Override
                        public void onCacheSuccess(Response<Bitmap> response) {
                            super.onCacheSuccess(response);
                            ((ScendImageHolder) holder).thumbnailPicS.setImageBitmap(response.body());
                        }

                        @Override
                        public void onError(Response<Bitmap> response) {
                            super.onError(response);
                            ToastUtil.showToast(context, "网络错误");
                        }
                    });
            OkGo.<Bitmap>get(headLineModel.getThumbnail_pic_s02())
                    .tag(this)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onSuccess(Response<Bitmap> response) {
                            ((ScendImageHolder) holder).thumbnailPicS02.setImageBitmap(response.body());
                        }

                        @Override
                        public void onCacheSuccess(Response<Bitmap> response) {
                            super.onCacheSuccess(response);
                            ((ScendImageHolder) holder).thumbnailPicS02.setImageBitmap(response.body());
                        }

                        @Override
                        public void onError(Response<Bitmap> response) {
                            super.onError(response);
                            ToastUtil.showToast(context, "网络错误");
                        }
                    });
        }
        if (holder instanceof ThirdImageHolder) {
            ((ThirdImageHolder) holder).title.setText("\t\t\t\t" + headLineModel.getTitle());
            ((ThirdImageHolder) holder).date.setText(headLineModel.getDate());
            ((ThirdImageHolder) holder).authorName.setText(headLineModel.getAuthor_name());
            OkGo.<Bitmap>get(headLineModel.getThumbnail_pic_s())
                    .tag(this)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onSuccess(Response<Bitmap> response) {
                            ((ThirdImageHolder) holder).thumbnailPicS.setImageBitmap(response.body());
                        }

                        @Override
                        public void onCacheSuccess(Response<Bitmap> response) {
                            super.onCacheSuccess(response);
                            ((ThirdImageHolder) holder).thumbnailPicS.setImageBitmap(response.body());
                        }

                        @Override
                        public void onError(Response<Bitmap> response) {
                            super.onError(response);
                            ToastUtil.showToast(context, "网络错误");
                        }
                    });
            OkGo.<Bitmap>get(headLineModel.getThumbnail_pic_s02())
                    .tag(this)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onSuccess(Response<Bitmap> response) {
                            ((ThirdImageHolder) holder).thumbnailPicS02.setImageBitmap(response.body());
                        }

                        @Override
                        public void onCacheSuccess(Response<Bitmap> response) {
                            super.onCacheSuccess(response);
                            ((ThirdImageHolder) holder).thumbnailPicS02.setImageBitmap(response.body());
                        }

                        @Override
                        public void onError(Response<Bitmap> response) {
                            super.onError(response);
                            ToastUtil.showToast(context, "网络错误");
                        }
                    });
            OkGo.<Bitmap>get(headLineModel.getThumbnail_pic_s03())
                    .tag(this)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onSuccess(Response<Bitmap> response) {
                            ((ThirdImageHolder) holder).thumbnailPicS03.setImageBitmap(response.body());
                        }

                        @Override
                        public void onCacheSuccess(Response<Bitmap> response) {
                            super.onCacheSuccess(response);
                            ((ThirdImageHolder) holder).thumbnailPicS03.setImageBitmap(response.body());
                        }

                        @Override
                        public void onError(Response<Bitmap> response) {
                            super.onError(response);
                            ToastUtil.showToast(context, "网络错误");
                        }
                    });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (headLineModelList != null) {
            return headLineModelList.size();
        }
        return 0;
    }

    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {

        HeadLineModel headLineModel = headLineModelList.get(position);
        if (headLineModel.getType() == 0) {
            return TYPE_fIRST_IMAGE;
        } else if (headLineModel.getType() == 1) {
            return TYPE_SECOND_IMAGE;
        } else {
            return TYPE_THREE_IMAGE;
        }
    }
    /**
     * 创建三种ViewHolder
     */

    private class FirstImageHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnailPicS;
        TextView date;
        TextView authorName;

        public FirstImageHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            thumbnailPicS = itemView.findViewById(R.id.thumbnail_pic_s);
            date = itemView.findViewById(R.id.date);
            authorName = itemView.findViewById(R.id.author_name);
        }
    }

    private class ScendImageHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnailPicS;
        ImageView thumbnailPicS02;
        TextView date;
        TextView authorName;

        public ScendImageHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            thumbnailPicS = itemView.findViewById(R.id.thumbnail_pic_s);
            thumbnailPicS02 = itemView.findViewById(R.id.thumbnail_pic_s02);
            date = itemView.findViewById(R.id.date);
            authorName = itemView.findViewById(R.id.author_name);
        }
    }

    private class ThirdImageHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnailPicS;
        ImageView thumbnailPicS02;
        ImageView thumbnailPicS03;
        TextView date;
        TextView authorName;

        public ThirdImageHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            thumbnailPicS = itemView.findViewById(R.id.thumbnail_pic_s);
            thumbnailPicS02 = itemView.findViewById(R.id.thumbnail_pic_s02);
            thumbnailPicS03 = itemView.findViewById(R.id.thumbnail_pic_s03);
            date = itemView.findViewById(R.id.date);
            authorName = itemView.findViewById(R.id.author_name);
        }
    }
    public interface OnItemClickListener{
        void onclick(int positon);
    }
    public  void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }


}
