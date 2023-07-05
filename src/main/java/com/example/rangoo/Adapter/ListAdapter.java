package com.example.rangoo.Adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoo.Interfaces.AdapterListener;
import com.example.rangoo.Model.Food;
import com.example.rangoo.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private final int[] colors = {R.color.baron, R.color.pumpkin, R.color.vegan};
    private final ArrayList<Food> list;

    private AdapterListener adapterListener;

    public ListAdapter(ArrayList<Food> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Food food = list.get(position);
        holder.bind(food);
        holder.itemView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), colors[position % colors.length])));

        // Realiza esculta de cliques nos cards da lista. E delega tratamento para activity.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.onCardClick(list.get(holder.getLayoutPosition()));
            }
        });

        // Realiza esculta de cliques em botão add presente em cards. E delega tratamento para activity.
        holder.itemView.findViewById(R.id.cardList_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.onAddClick(list.get(holder.getLayoutPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setAdapterListener(AdapterListener listener){
        this.adapterListener = listener;
    }
}
