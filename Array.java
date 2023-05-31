package com.example.lostandfoundapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
    //declare variables
public class Array extends RecyclerView.Adapter<Array.arrayholder> {
    Context context;
    ArrayList<String> nameitem = new ArrayList<String>();
    private RecyclerViewClickListener listener;
    //declare parts of the array
    public Array(Context context, ArrayList<String> item, RecyclerViewClickListener listener) {
        this.context = context;
        this.nameitem = item;
        this.listener = listener;
    }


    @NonNull
    @Override
    public arrayholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.advert, null);
                arrayholder myViewHolder = new arrayholder(view);
                     return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull arrayholder holder, int position) {
        String name1 = nameitem.get(position);
         holder.name.setText(name1);
    }

    @Override
    public int getItemCount() {
        return nameitem.size();
    }
        public interface RecyclerViewClickListener{
            void  onClick(View view, int position);
    }
    public class arrayholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        public arrayholder(@NonNull View itemView) {
             super(itemView);

                 name = itemView.findViewById(R.id.itemname);
                    itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }
}
