package com.example.tatar_timer.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatar_timer.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private int numberItems;
    private LayoutInflater mInflater;
    private static int ViewHolderCount;
    private String[] categories;
    private ArrayList<String> categoryList;
    private static final String TAG = "myLogs";
    private static final String DIVIDER = "|||||||||||||||||||||||";

    private Boolean flag = true;
    Context mContext;
    SharedPreferences sPref;
    private int mPosition;

    final String SAVED_TEXT = "saved_text";

    public CategoryAdapter(Context context, int numberItems, ArrayList<String> array) {
//        this.numberItems = numberItems;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        ViewHolderCount = 0;
        categories = array.toArray(new String[0]);
        categoryList = array;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.category_number;

        View view = mInflater.inflate(R.layout.category_number, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(view);

//        viewHolder.itemName.setText("view holder index" + ViewHolderCount);
        ViewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        mPosition = position;
        Log.d(DIVIDER, "Divide i say");
        Log.d(TAG, "Current position is equal" +  String.valueOf(mPosition));

        holder.bind(mPosition);
    }

    @Override
    public int getItemCount() {
        return categories.length;
//        return numberItems;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tv_holder_for_names);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positionIndex = getAdapterPosition() + 1;
                    Toast toast;

                    toast = Toast.makeText(mContext, "Айди этого элемента равно " + positionIndex, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Log.d(DIVIDER, "");
                    Log.d(TAG, "Зажалось");

                    int positionIndex = getAdapterPosition() ;
                    Log.d(TAG, "Размер массива " + String.valueOf(categoryList.size()));
                    Log.d(TAG, "Выбранный элемент " + String.valueOf(positionIndex));


                    for (int i = 0; i < categoryList.size(); i ++){
                        Log.d(TAG, "Выбранный элемент " + categoryList.toArray()[i]);
                    }
                    categoryList.remove(positionIndex);
                    notifyItemRemoved(positionIndex);
                    notifyItemRangeChanged(positionIndex, categoryList.size());

                    for (int i = 0; i < categoryList.size(); i ++){
                        Log.d(TAG, "Выбранный элемент " + categoryList.toArray()[i]);
                    }
                    flag = false;
                    return true;
                }
            });

        }

        void bind(int listIndex) {
            itemName.setText(categoryList.get(listIndex));
        }

    }
}