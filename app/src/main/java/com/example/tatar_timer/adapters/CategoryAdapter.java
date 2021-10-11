package com.example.tatar_timer.adapters;

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

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    //////////////////////////////////////////////////////////
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