package com.jeeteshgupta.gymkhanainventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class CoordinatorRecordAdpter extends RecyclerView.Adapter<CoordinatorRecordAdpter.CoordinatorViewHolder> {

    private Context mctx;
    private ArrayList<StudentInventoryRecord> studentInventoryRecords;
    private CoordinatorObject coordinatorObject;

    public CoordinatorRecordAdpter(Context context, ArrayList<StudentInventoryRecord> list, CoordinatorObject coordinatorObject) {
        this.mctx = context;
        this.studentInventoryRecords = list;
        this.coordinatorObject = coordinatorObject;
    }

    @NonNull
    @Override
    public CoordinatorRecordAdpter.CoordinatorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.coordinator_card, viewGroup,false);
        return new CoordinatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinatorRecordAdpter.CoordinatorViewHolder coordinatorViewHolder, int i) {
        StudentInventoryRecord studentInventoryRecord = studentInventoryRecords.get(i);

        coordinatorViewHolder.name.setText(studentInventoryRecord.getTo());
        coordinatorViewHolder.rollno.setText(studentInventoryRecord.getStudentRollno());
        coordinatorViewHolder.item.setText(studentInventoryRecord.getItem());
        coordinatorViewHolder.count.setText( Integer.toString( studentInventoryRecord.getValue() ) );
    }

    @Override
    public int getItemCount() {
        return studentInventoryRecords.size();
    }

    public class CoordinatorViewHolder extends RecyclerView.ViewHolder {
        TextView name , rollno , item , count;
        public CoordinatorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rollno = itemView.findViewById(R.id.rollno);
            item = itemView.findViewById(R.id.item);
            count = itemView.findViewById(R.id.count);
        }
    }
}
