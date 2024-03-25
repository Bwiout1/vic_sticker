package com.example.amusingstickerbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amusingstickerbox.R;
import com.example.amusingstickerbox.tools.Photo_Source;

import java.util.List;

public class Gallery_Adapter extends RecyclerView.Adapter<Gallery_Adapter.ContactHolder> {

    private Context context;

    public List<Photo_Source> data;

    private onItemViewClickListener listener;

    public Gallery_Adapter(Context context) {
        this.context = context;
    }

    public void setData(List<Photo_Source> data) {
        this.data = data;
    }


    public onItemViewClickListener getListener() {
        return listener;
    }

    public void setListener(onItemViewClickListener listener) {
        this.listener = listener;
    }



    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(context).inflate(R.layout.gallery_photo_list, null));
    }


    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {

        Glide.with(context)
                .load(data.get(position).getPath())
                .into(holder.Image);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {//绑定列表项点击事件
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);//点击跳转
            }
        });

    }
    public class ContactHolder extends RecyclerView.ViewHolder {

        ImageView Image;//Recyclerview列表项图片
        ConstraintLayout constraintLayout;//Recyclerview列表项布局

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.my_gallery_photo);
            constraintLayout = itemView.findViewById(R.id.my_gallery_layout);

        }
    }



    @Override
    public int getItemCount() {
        return null != data ? data.size() : 0;//不为空返回数量，为空返回0
    }

    public interface onItemViewClickListener {
        void onItemClick(int position);
    }
}
