package com.example.customer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    Context context;
    int layout;
    List<MongoData> data;

    public DataAdapter(List<MongoData> data, int item1, Context context) {
        this.context = context;
        this.layout = item1;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.view_layout,null);
        ViewHolder holder = new ViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productname.setText(data.get(position).getProductname());
        holder.issue.setText(data.get(position).getIssue());
        holder.desc.setText(data.get(position).getDescription());
        holder.cname.setText(data.get(position).getCustomername());
        holder.phno.setText(data.get(position).getPhno());
        holder.add.setText(data.get(position).getAddress());
        holder.as.setText(data.get(position).getAssigned_Engineer());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname,cname,issue,desc,phno,add,as;
        public ViewHolder(View itemView) {
            super(itemView);
            productname=itemView.findViewById(R.id.product_id);
            cname=itemView.findViewById(R.id.customer_id);
            issue=itemView.findViewById(R.id.issue_id);
            desc=itemView.findViewById(R.id.description_id);
            phno=itemView.findViewById(R.id.customer_phno);
            add=itemView.findViewById(R.id.address_id);
            as=itemView.findViewById(R.id.assigned_id);

        }
    }
}
