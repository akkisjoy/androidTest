package com.example.androidtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.R;
import com.example.androidtest.listener.RecyclerViewClickListener;
import com.example.androidtest.listener.ResponseListener;
import com.example.androidtest.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataListAdapter extends PagedListAdapter<Data, RecyclerView.ViewHolder> {

    private List<Data> dataList;
    private List<Data> filterDataList;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private RecyclerViewClickListener itemListener;

    public DataListAdapter() {
        super(Data.DIFF_CALLBACK);
    }

    public DataListAdapter(RecyclerViewClickListener itemListener) {
        super(Data.DIFF_CALLBACK);
        this.dataList = new ArrayList<>();
        this.filterDataList = new ArrayList<>();
        this.itemListener = itemListener;
    }

    public void addDataList(List<Data> dataListing) {
        dataList.addAll(dataListing);
        filterDataList.addAll(dataListing);//add all items of array list to filter list
    }

    // Filter Method to filter data
    public void filter(String charText, boolean isSearchWithPrefix, ResponseListener responseListener) {

        //If Filter type is NAME and EMAIL then only do lowercase, else in case of NUMBER no need to do lowercase because of number format
        charText = charText.toLowerCase(Locale.getDefault());

        dataList.clear();//Clear the main ArrayList

        //If search query is null or length is 0 then add all filterList items back to arrayList
        if (charText.length() == 0) {
            dataList.addAll(filterDataList);
        } else {

            //Else if search query is not null do a loop to all filterList items
            for (Data model : filterDataList) {

                //Now check the type of search filter
                if (isSearchWithPrefix) {
                    //if STARTS WITH radio button is selected then it will match the exact NAME which match with search query
                    if (model.getTitle().toLowerCase(Locale.getDefault()).startsWith(charText))
                        dataList.add(model);
                } else {
                    //if CONTAINS radio button is selected then it will match the NAME wherever it contains search query
                    if (model.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                        dataList.add(model);
                }
            }

            responseListener.onResponse((dataList.size() > 0));

        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
            holder = new DataHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_progress_bar, parent, false);

            holder = new ProgressHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int safePosition = holder.getAdapterPosition();

        if (holder instanceof DataHolder) {
            Data data = getItem(safePosition);
            if (data != null) {
                holder.itemView.setOnClickListener(view -> itemListener.recyclerViewListClicked(data));
                ((DataHolder) holder).titleText.setText(data.getTitle());
                ((DataHolder) holder).descriptionText.setText(data.getBlurb());
                ((DataHolder) holder).pleageText.setText(String.format("Pledge - %s %s", data.getAmtPledged(), data.getCurrency()));
                ((DataHolder) holder).backersText.setText(String.format("Backers - %s", data.getNumBackers()));
            }
        } else {
            ((ProgressHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    protected class DataHolder extends RecyclerView.ViewHolder {

        public TextView titleText, descriptionText, pleageText, backersText;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            pleageText = itemView.findViewById(R.id.pleageText);
            backersText = itemView.findViewById(R.id.backersText);

        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }
}
