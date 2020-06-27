package com.mmit.uc_assignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private long _id;
    private DBManager dbManager;
    ArrayList<String> personNames;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> personNames) {
        this.context = context;
        this.personNames = personNames;
        dbManager = new DBManager(context);
        dbManager.open();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//    }
    // Defining the behaviour for each item of recycler view maintaing ToDoList
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // set the data in items


        holder.name.setText(personNames.get(position));

       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dbManager.delete(_id);
               personNames.remove(holder.name.getText().toString());
         //      dbManager.update(_id, holder.name.getText().toString());
//               notifyItemRemoved(position);
//               notifyItemRangeChanged(position, personNames.size());
            //((MainActivity)context).deletePersonName(holder.name.getText().toString());
               notifyDataSetChanged();
           }
       });

        holder.undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                String title = titleText.getText().toString();
//                dbManager.update(_id, title);
//                this.returnHome();
                holder.delete.setVisibility(View.GONE);
                holder.undo.setVisibility(View.GONE);
                holder.checkItem.setVisibility(View.VISIBLE);
            }
        });


        holder.checkItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.checkItem.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.undo.setVisibility(View.VISIBLE);
            }
        });




        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, personNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return personNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the item view's
        ImageButton delete;
        ImageButton undo;
        ImageButton checkItem;


        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            undo = (ImageButton) itemView.findViewById(R.id.undo);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            checkItem = (ImageButton) itemView.findViewById(R.id.checkItem);
        }
    }
}