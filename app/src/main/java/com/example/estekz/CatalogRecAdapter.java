package com.example.estekz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatalogRecAdapter extends RecyclerView.Adapter<CatalogRecAdapter.ViewHolder> {

    List<String> category_name;

    public CatalogRecAdapter(List<String> category_name) {
        this.category_name = category_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.catalog_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(category_name.get(position));
    }

    @Override
    public int getItemCount() {
        return category_name.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.next_button);
            textView = itemView.findViewById(R.id.catalog_name);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), category_name.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }

}















