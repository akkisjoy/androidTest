package com.example.androidtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.androidtest.R;
import com.example.androidtest.listener.OnLoadMoreListener;
import com.example.androidtest.listener.RecyclerViewClickListener;
import com.example.androidtest.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataAdapter extends Adapter<RecyclerView.ViewHolder> {

    private List<Data> dataList;
    private List<Data> filterDataList;
    //duplicate list for filtering
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    RecyclerViewClickListener itemListener;

    public DataAdapter(RecyclerView recyclerView, RecyclerViewClickListener itemListener) {
        this.dataList = new ArrayList<>();
        this.filterDataList = new ArrayList<>();//initiate filter list

        this.itemListener = itemListener;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
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
            Data data = dataList.get(safePosition);
            holder.itemView.setOnClickListener(view -> itemListener.recyclerViewListClicked(data));
            ((DataHolder) holder).titleText.setText(data.getTitle());
            ((DataHolder) holder).descriptionText.setText(data.getBlurb());
            ((DataHolder) holder).pleageText.setText(String.format("Pledge - %s %s", data.getAmtPledged(), data.getCurrency()));
            ((DataHolder) holder).backersText.setText(String.format("Backers - %s", data.getNumBackers()));

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

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
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
