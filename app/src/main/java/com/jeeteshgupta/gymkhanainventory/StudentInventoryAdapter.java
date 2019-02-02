package com.jeeteshgupta.gymkhanainventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class StudentInventoryAdapter extends RecyclerView.Adapter<StudentInventoryAdapter.StudentInventoryViewHolder> {

    private ArrayList<StudentInventoryRecord> list;
    private Context mctx;

    public StudentInventoryAdapter(Context mctx, ArrayList<StudentInventoryRecord> list) {
        this.list = list;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public StudentInventoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.studentinventory_card, viewGroup,false);
        return new StudentInventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentInventoryViewHolder studentInventoryViewHolder, int i) {
        StudentInventoryRecord studentInventoryRecord = list.get(i);

        studentInventoryViewHolder.from.setText(studentInventoryRecord.getFrom());
        studentInventoryViewHolder.item.setText(studentInventoryRecord.getItem());
        studentInventoryViewHolder.value.setText(Integer.toString(studentInventoryRecord.getValue()));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class StudentInventoryViewHolder extends RecyclerView.ViewHolder {
        TextView from , item , value;
        public StudentInventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            item = itemView.findViewById(R.id.item);
            value = itemView.findViewById(R.id.value);
        }
    }
}
