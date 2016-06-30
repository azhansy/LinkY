//package com.azhansy.linky.blog;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.azhansy.linky.R;
//import com.azhansy.linky.base.SimpleOneViewHolderBaseAdapter;
//import com.azhansy.linky.blog.model.BlogModel;
//
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by SHU on 2016/6/17.
// */
//public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder>{
//    private List<BlogModel> mBlogModelList;
//    private Context mContext;
//
//    public BlogAdapter(Context context,List<BlogModel> list){
//        this.mBlogModelList = list;
//        this.mContext = context;
//    }
//
//    @Override
//    public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new BlogViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image_title_content,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(BlogViewHolder holder, int position) {
//
//        BlogModel blogModel = mBlogModelList.get(position);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mBlogModelList.size();
//    }
//
//    class BlogViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.iv_logo)
//        ImageView logo;
//        @Bind(R.id.tv_title)
//        TextView title;
//        @Bind(R.id.tv_description)
//        TextView escription;
//        @Bind(R.id.tv_time)
//        TextView time;
//        @Bind(R.id.btn_more)
//        Button more;
//
//        public BlogViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//}