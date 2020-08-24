package com.example.androidtest.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.R;
import com.example.androidtest.listener.RecyclerViewClickListener;
import com.example.androidtest.model.Data;

public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private Data data;
    public TextView titleText, descriptionText, pleageText, backersText;
    private RecyclerViewClickListener itemClickListener;

    public DataViewHolder(View view, RecyclerViewClickListener itemClickListener) {
        super(view);
        titleText = itemView.findViewById(R.id.titleText);
        descriptionText = itemView.findViewById(R.id.descriptionText);
        pleageText = itemView.findViewById(R.id.pleageText);
        backersText = itemView.findViewById(R.id.backersText);
        this.itemClickListener = itemClickListener;
        view.setOnClickListener(this);

    }

    public void bindTo(Data data) {
        this.data = data;
        titleText.setText(data.getTitle());
        descriptionText.setText(data.getBlurb());
        pleageText.setText(String.format("Pledge - %s %s", data.getAmtPledged(), data.getCurrency()));
        backersText.setText(String.format("Backers - %s", data.getNumBackers()));
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.recyclerViewListClicked(data);
        }
    }

}
