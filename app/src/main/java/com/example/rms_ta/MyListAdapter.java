package com.example.rms_ta;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<MyListData> listData;

    public MyListAdapter(ArrayList<MyListData> listData)
    {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.custom_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyListData myListData = listData.get(position);
        holder.name.setText(myListData.getUsername());
        holder.age.setText(myListData.getAge());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,age;


        public ViewHolder(View itemView) {
            super(itemView);
            this.name =  itemView.findViewById(R.id.custom_list_item_name);
            this.age =  itemView.findViewById(R.id.custom_list_item_age);

        }
    }
}

