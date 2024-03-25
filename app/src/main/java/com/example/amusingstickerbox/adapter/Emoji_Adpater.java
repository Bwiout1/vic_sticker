package com.example.amusingstickerbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amusingstickerbox.R;
import com.example.amusingstickerbox.tools.Emoji_Source;
import com.example.amusingstickerbox.tools.Photo_Source;

import java.util.ArrayList;
import java.util.List;

public class Emoji_Adpater extends RecyclerView.Adapter<Emoji_Adpater.Myholder> {

    Emoji_Source emoji_source;

    List<Photo_Source> data = new ArrayList<>();


    private Context context;


    public onItemViewClickListener listener;


    public Emoji_Adpater(Context context) {
        this.context = context;
    }

    public void SetData(List<Photo_Source> data){this.data=data;}

    public void setOnItemClickListener(onItemViewClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.emoji_list, null));
    }




    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        holder.img_show.setImageResource(data.get(position).getId());


        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);//点击跳转

            }
        });


    }

    public class Myholder extends RecyclerView.ViewHolder{

        ImageView img_show;

        ConstraintLayout constraintLayout;


        public Myholder(@NonNull View itemView) {

            super(itemView);

            img_show = itemView.findViewById(R.id.emoji);

            constraintLayout = itemView.findViewById(R.id.emoji_list);

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
        return 30;
    }


    public interface onItemViewClickListener {
        void onItemClick(int position);
    }
}
