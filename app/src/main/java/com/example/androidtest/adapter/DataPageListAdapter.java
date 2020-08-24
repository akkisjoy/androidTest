package com.example.androidtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.R;
import com.example.androidtest.listener.RecyclerViewClickListener;
import com.example.androidtest.listener.ResponseListener;
import com.example.androidtest.model.Data;
import com.example.androidtest.model.NetworkState;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class DataPageListAdapter extends PagedListAdapter<Data, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private RecyclerViewClickListener itemClickListener;

    public DataPageListAdapter(RecyclerViewClickListener itemClickListener) {
        super(Data.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.row_data) {
            View view = layoutInflater.inflate(R.layout.row_data, parent, false);
            DataViewHolder viewHolder = new DataViewHolder(view, itemClickListener);;
            return viewHolder;
        } else if (viewType == R.layout.row_progress) {
            View view = layoutInflater.inflate(R.layout.row_progress, parent, false);
            return new ProgressViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.row_data:
                ((DataViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.row_progress:
                ((ProgressViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        return hasExtraRow() && position == getItemCount() - 1 ? R.layout.row_progress : R.layout.row_data;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    // Filter Method to filter data
//    public void filter(String charText, boolean isSearchWithPrefix, ResponseListener responseListener) {
//
//        //If Filter type is NAME and EMAIL then only do lowercase, else in case of NUMBER no need to do lowercase because of number format
//        charText = charText.toLowerCase(Locale.getDefault());
//
//        getCurrentList().clear();//Clear the main ArrayList
//
//        //If search query is null or length is 0 then add all filterList items back to arrayList
//        if (charText.length() == 0) {
//            getCurrentList().addAll(filterDataList);
//        } else {
//
//            //Else if search query is not null do a loop to all filterList items
//            for (Data model : filterDataList) {
//
//                //Now check the type of search filter
//                if (isSearchWithPrefix) {
//                    //if STARTS WITH radio button is selected then it will match the exact NAME which match with search query
//                    if (model.getTitle().toLowerCase(Locale.getDefault()).startsWith(charText))
//                        getCurrentList().add(model);
//                } else {
//                    //if CONTAINS radio button is selected then it will match the NAME wherever it contains search query
//                    if (model.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
//                        getCurrentList().add(model);
//                }
//            }
//
//            responseListener.onResponse((getCurrentList().size() > 0));
//
//        }
//        notifyDataSetChanged();
//    }
}
