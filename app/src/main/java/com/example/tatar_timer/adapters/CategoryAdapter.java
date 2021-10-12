package com.example.tatar_timer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatar_timer.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private int numberItems;
    private static int ViewHolderCount;

    public CategoryAdapter(int numberItems) {
        this.numberItems = numberItems;
        ViewHolderCount = 0;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.category_number;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(view);

        viewHolder.itemName.setText("view holder index" + ViewHolderCount);
        ViewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tv_holder_for_names);

        }

        void bind(int listIndex) {
//            itemName.setText(S);

        }
    }


}