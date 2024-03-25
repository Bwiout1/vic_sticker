package com.example.amusingstickerbox.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amusingstickerbox.R;
import com.example.amusingstickerbox.tools.MyPhotoBean;

import java.util.ArrayList;
import java.util.List;

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.My_Work_holder> {

    List<MyPhotoBean> data = new ArrayList<>();
    private Context context;

    public MyPhotoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MyPhotoBean> data) {
        this.data = data;
    }

    private onItemViewClickListener listener;

    public void setOnItemClickListener(onItemViewClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public My_Work_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new My_Work_holder(LayoutInflater.from(context).inflate(R.layout.mywork_list, null));
    }


    @Override
    public void onBindViewHolder(@NonNull My_Work_holder holder, int position) {
        holder.pack_name.setText(data.get(position).getPack());
        holder.img_my_work.setImageURI(Uri.parse(data.get(position).getSource()));


    }
    public class My_Work_holder extends RecyclerView.ViewHolder{
        TextView pack_name;
        ImageView img_my_work;
        ConstraintLayout my_work_list_layout;

        public My_Work_holder(@NonNull View itemView) {
            super(itemView);

            pack_name = itemView.findViewById(R.id.pack_name);
            img_my_work = itemView.findViewById(R.id.my_work_view);
            my_work_list_layout = itemView.findViewById(R.id.my_work_list_layout);
            my_work_list_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return null != data ? data.size() : 0;
    }
    public interface onItemViewClickListener {
        void onItemClick(int position);
    }

}
