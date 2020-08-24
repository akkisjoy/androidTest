package com.example.androidtest.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.R;
import com.example.androidtest.adapter.DataPageListAdapter;
import com.example.androidtest.listener.RecyclerViewClickListener;
import com.example.androidtest.listener.ResponseListener;
import com.example.androidtest.model.Data;
import com.example.androidtest.viewmodel.DataListViewModel;

public class MainActivity2 extends AppCompatActivity implements RecyclerViewClickListener {

    private DataListViewModel dataViewModel;
    private RecyclerView recyclerView;
    private DataPageListAdapter dataListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        initViewModel();

    }

    private void initView() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        dataListAdapter = new DataPageListAdapter(this);
        recyclerView.setAdapter(dataListAdapter);
    }

    private void initViewModel() {
        dataViewModel = new ViewModelProvider(this).get(DataListViewModel.class);
        dataViewModel.getListData().observe(this, dataListAdapter::submitList);
        dataViewModel.getNetworkState().observe(this, networkState -> {
            dataListAdapter.setNetworkState(networkState);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.searchItem);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                dataListAdapter.filter(newText, false, new ResponseListener() {
//                    @Override
//                    public void onResponse(Object object) {
//
//                    }
//                });
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void recyclerViewListClicked(Object object) {
        DataDialog.show(MainActivity2.this, (Data) object);
    }
}