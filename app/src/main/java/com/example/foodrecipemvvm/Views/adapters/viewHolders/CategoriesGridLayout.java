package com.example.foodrecipemvvm.Views.adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.Views.adapters.OnClickListeners;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesGridLayout extends RecyclerView.ViewHolder  implements View.OnClickListener  {

    //ui
    public CircleImageView categoryImage;
    public TextView categoryText;
    // on click event listener
    public OnClickListeners categoryClickListener;

    public CategoriesGridLayout(@NonNull View itemView, OnClickListeners categoryClickListener) {
        super(itemView);

        categoryImage = itemView.findViewById(R.id.cardViewImage);
        categoryText = itemView.findViewById(R.id.cardViewText);

        this.categoryClickListener = categoryClickListener;
        //clickable the entire view
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //we access the method created in the interface created by us
        categoryClickListener.openCategoryOnClick(categoryText.getText().toString());
    }
}

