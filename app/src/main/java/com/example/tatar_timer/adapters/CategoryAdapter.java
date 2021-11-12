package com.example.tatar_timer.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatar_timer.CategoryChooseActivity;
import com.example.tatar_timer.R;
import com.example.tatar_timer.stopwatch;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private LayoutInflater mInflater;
    private static int ViewHolderCount;
    private ArrayList<String> categoryList;
    private static final String TAG = "myLogs";
    private static final String DIVIDER = "|||||||||||||||||||||||";
    int positionIndex;


    //Осталось возврат добавить и усе
    private Boolean flag = true;
    Context mContext;
    private int mPosition;

    final String SAVED_TEXT = "saved_text";

    public CategoryAdapter(Context context, int numberItems, ArrayList<String> array, String[] newArray) {
//        this.numberItems = numberItems;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        ViewHolderCount = 0;
        categoryList = array;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.category_number;

        View view = mInflater.inflate(R.layout.category_number, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(view);

        ViewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        mPosition = position;
        Log.d(DIVIDER, "Divide i say");
        Log.d(TAG, "Current position is equal" + String.valueOf(mPosition));
        holder.bind(mPosition);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
//        return numberItems;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tv_holder_for_names);

            itemView.setOnClickListener(v -> {
                int positionIndex = getAdapterPosition() + 1;
                Toast toast;
                Intent intent = new Intent(v.getContext(), stopwatch.class);

                toast = Toast.makeText(mContext, "Выбрана категория " + categoryList.get(getAdapterPosition()), Toast.LENGTH_SHORT);
                toast.show();
                Log.d(TAG, categoryList.get(getAdapterPosition()));
                intent.putExtra("ChosenActivity", categoryList.get(getAdapterPosition()));
                String[] sentArray2 = categoryList.toArray(new String[0]);
                intent.putExtra("Array", sentArray2);
                v.getContext().startActivity(intent); //Стартуем активити.

                CategoryChooseActivity.closeChooser();
//                    stopwatch.setCurrentCategoryName(String.valueOf(categoryList.get(positionIndex)));


            });

            itemView.setOnLongClickListener(v -> {

                Log.d(DIVIDER, "");
                Log.d(TAG, "Зажалось");

                positionIndex = getAdapterPosition();
                Log.d(TAG, "Размер массива " + String.valueOf(categoryList.size()));
                Log.d(TAG, "Выбранный элемент " + String.valueOf(positionIndex));


                for (int i = 0; i < categoryList.size(); i++) {
                    Log.d(TAG, "Выбранный элемент: \n" + categoryList.toArray()[i]);
                }
                categoryList.remove(positionIndex);
                notifyItemRemoved(positionIndex);
                notifyItemRangeChanged(positionIndex, categoryList.size());

                for (int i = 0; i < categoryList.size(); i++) {
                    Log.d(TAG, "Остался элемент:  \n" + categoryList.toArray()[i]);
                }
                flag = false;
                return true;
            });
        }

        void bind(int listIndex) {
            itemName.setText(categoryList.get(listIndex));
        }

    }
}