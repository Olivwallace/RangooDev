package com.example.rangoo.Adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rangoo.Model.Food;
import com.example.rangoo.R;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

public class ListViewHolder extends RecyclerView.ViewHolder {

    MaterialTextView name, resume;
    ImageView image;

    public ListViewHolder(@NotNull View view){
        super(view);
    }

    public void bind(Food food){
        image = itemView.findViewById(R.id.cardList_image);

        Glide.with(itemView)
                .load(food.getImageUrl())
                .override(150, 150)
                .centerCrop()
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(image);

        name = itemView.findViewById(R.id.cardList_name);
        resume = itemView.findViewById(R.id.cardList_resume);

        name.setText(food.getName());
        resume.setText(food.getResume());
    }

}
