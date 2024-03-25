package com.example.amusingstickerbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amusingstickerbox.R;
import com.example.amusingstickerbox.tools.Photo_Source;

import java.util.ArrayList;
import java.util.List;

public class Create_Adapter extends RecyclerView.Adapter<Create_Adapter.Myholder> {

    List<Photo_Source> data = new ArrayList<>();

    private Context context;

    public onItemViewClickListener listener;

    public void setData(List<Photo_Source> data) {
        this.data = data;
    }

    public Create_Adapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(onItemViewClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.create_sticker_list, null));
    }


    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        holder.txt_num.setText(String.valueOf(data.get(position).getId()));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);//点击跳转
            }
        });

    }


    public class Myholder extends RecyclerView.ViewHolder{

        ImageView add;
        TextView txt_num;
        ConstraintLayout constraintLayout;


        public Myholder(@NonNull View itemView) {

            super(itemView);

            add = itemView.findViewById(R.id.create_add);
            txt_num = itemView.findViewById(R.id.number);
            constraintLayout = itemView.findViewById(R.id.create_list);

            itemView.setOnClickListener(new View.OnClickListener() {
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
