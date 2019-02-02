package com.jeeteshgupta.gymkhanainventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class CoordinatorListAdapter extends RecyclerView.Adapter<CoordinatorListAdapter.ViewHolder>{

    private ArrayList<CoordinatorObject> coordinatorObjects;
    private Context mctx;

    public CoordinatorListAdapter(Context mctx, ArrayList<CoordinatorObject> myCoordinators) {
        this.coordinatorObjects = myCoordinators;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.coordinator_object_card, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinatorListAdapter.ViewHolder viewHolder, int i) {
        CoordinatorObject coordinatorObject = coordinatorObjects.get(i);

        viewHolder.name.setText(coordinatorObject.getName());
        viewHolder.phoneNo.setText(coordinatorObject.getPhoneNo());
        viewHolder.hostel.setText(coordinatorObject.getHostel());

        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mctx, LinearLayoutManager.VERTICAL, false));
        viewHolder.recyclerView.setHasFixedSize(true);

        int position = viewHolder.getAdapterPosition();

        ArrayList<Inventory> list = coordinatorObjects.get(position).getInventory();
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();

        for(Inventory x : list){
            if(!x.getName().isEmpty()){
                inventories.add(x);
            }
        }

        if(inventories.size() == 0){
            inventories.add(new Inventory("No Inventory " , 0));
        }

        viewHolder.adapter = new InventoryAdapter(mctx,inventories);
        viewHolder.recyclerView.setAdapter(viewHolder.adapter);
    }

    @Override
    public int getItemCount() {
        return this.coordinatorObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name , hostel , phoneNo;
        RecyclerView recyclerView;
        InventoryAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name   = itemView.findViewById(R.id.name);
            hostel = itemView.findViewById(R.id.hostel);
            phoneNo = itemView.findViewById(R.id.phoneNo);

            recyclerView = itemView.findViewById(R.id.recyclerView);

        }
    }
}
