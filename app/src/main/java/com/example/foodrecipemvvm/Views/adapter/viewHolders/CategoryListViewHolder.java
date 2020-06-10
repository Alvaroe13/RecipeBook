package com.example.foodrecipemvvm.Views.adapter.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.Views.adapter.OnClickListeners;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //make them public since ViewHolders are in a different packages than the Adapter
    public CircleImageView categoryImage;
    public TextView categoryText;
    OnClickListeners categoryClickListener;

    public CategoryListViewHolder(@NonNull View cardViewLayout, OnClickListeners categoryClickListener ) {
        super(cardViewLayout);
        this.categoryClickListener = categoryClickListener;
        //ui
        categoryImage = cardViewLayout.findViewById(R.id.cardViewImage);
        categoryText = cardViewLayout.findViewById(R.id.cardViewText);

        //init click listener
        cardViewLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //we access the method created in the interface created by us
        categoryClickListener.openCategoryOnClick(categoryText.getText().toString());
    }

}
