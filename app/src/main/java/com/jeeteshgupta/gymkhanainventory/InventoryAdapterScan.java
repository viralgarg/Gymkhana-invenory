package com.jeeteshgupta.gymkhanainventory;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InventoryAdapterScan extends RecyclerView.Adapter<InventoryAdapterScan.InventoryViewHolder> {

    Context mctx;
    ArrayList<Inventory> list;
    CoordinatorObject coordinatorObject;

    public InventoryAdapterScan(Context mctx, ArrayList<Inventory> list , CoordinatorObject coordinatorObject)
    {
        this.mctx = mctx;
        this.list = list;
        this.coordinatorObject = coordinatorObject;
    }

    @NonNull
    @Override
    public InventoryAdapterScan.InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.inventory_card, viewGroup,false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InventoryAdapterScan.InventoryViewHolder holder, int i) {

        Inventory p = list.get(i);

        holder.name.setText(p.getName());
        holder.count.setText(String.valueOf(p.getValue()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class InventoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name,count;
        CardView cardView;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name  = itemView.findViewById(R.id.name);
            count = itemView.findViewById(R.id.count);
            cardView = itemView.findViewById(R.id.cardView);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition()+1;

            Intent intent = new Intent( mctx , GiveInventory.class);
            intent.putExtra("position" , (Serializable) position);
            mctx.startActivity(intent);

        }
    }

}
